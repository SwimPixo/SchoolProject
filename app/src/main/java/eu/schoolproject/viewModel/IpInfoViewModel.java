package eu.schoolproject.viewModel;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;

import eu.inloop.viewmodel.AbstractViewModel;
import eu.schoolproject.R;
import eu.schoolproject.api.RestServiceProvider;
import eu.schoolproject.api.model.IpInfo;
import eu.schoolproject.event.RefreshList;
import eu.schoolproject.service.DatabaseService;
import eu.schoolproject.util.sl.SL;
import eu.schoolproject.util.view.LoadingView;
import eu.schoolproject.viewModel.view.IIpInfoView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by BP on 29/12/2017.
 */

public class IpInfoViewModel extends AbstractViewModel<IIpInfoView> {

    private final static String ARG_IP_INFO = "IpInfoViewModel.ARG_IP_INFO";
    private final static String ARG_IP_INFO_ADDRESS = "IpInfoViewModel.ARG_IP_INFO_ADDRESS";

    private String mIpInfoAddress;
    private IpInfo mIpInfo = null;

    public static Bundle createBundle(final @Nullable String ipInfoAddress) {
        final Bundle bundle = new Bundle();
        bundle.putString(ARG_IP_INFO_ADDRESS, ipInfoAddress);
        return bundle;
    }

    public static Bundle createBundle(final @NonNull IpInfo ipInfo) {
        final Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_IP_INFO, ipInfo);
        return bundle;
    }

    @Override
    public void onCreate(@Nullable Bundle arguments, @Nullable Bundle savedInstanceState) {
        super.onCreate(arguments, savedInstanceState);
        parseBundle(arguments);
    }

    @Override
    public void onBindView(@NonNull IIpInfoView view) {
        super.onBindView(view);

        if (null != mIpInfo) {
            view.showIpInfo(mIpInfo);
        } else if (null != mIpInfoAddress) {
            getIpInfo(view.getActivity());
        } else {
            view.showError(view.getActivity().getResources().getString(R.string.ip_message_show_no_content));
        }
    }

    private void parseBundle(final @Nullable Bundle savedInstanceState) {
        if (null != savedInstanceState) {
            mIpInfoAddress = savedInstanceState.getString(ARG_IP_INFO_ADDRESS);
            mIpInfo = (IpInfo) savedInstanceState.getSerializable(ARG_IP_INFO);
        }
    }

    private void getIpInfo(final @NonNull Context context) {
        if (null != mIpInfoAddress) {
            getViewOptional().showProgress();

            SL.get(RestServiceProvider.class).provide().
                    requestIpInfo(mIpInfoAddress).enqueue(new Callback<IpInfo>() {
                @Override
                public void onResponse(Call<IpInfo> call, Response<IpInfo> response) {
                    getViewOptional().dismissProgress();

                    if (response.isSuccessful()) {
                        final IpInfo ipInfo = response.body();
                        if (null != ipInfo) {
                            mIpInfo = ipInfo;
                            mIpInfo.setDateTime(new Date());

                            SL.get(DatabaseService.class).getIpInfoDao().createOrUpdate(mIpInfo);
                            getViewOptional().showIpInfo(mIpInfo);

                            EventBus.getDefault().post(new RefreshList());
                        } else {
                            getViewOptional().showError(context.getResources().getString(R.string.ip_message_show_no_content_again),
                                    new LoadingView.OnAgainClickListener() {

                                        @Override
                                        public void clicked() {
                                            getIpInfo(context);
                                        }
                                    });
                        }
                    } else {
                        getViewOptional().showError(context.getResources().getString(R.string.ip_message_show_no_content_again),
                                new LoadingView.OnAgainClickListener() {

                                    @Override
                                    public void clicked() {
                                        getIpInfo(context);
                                    }
                                });
                    }
                }

                @Override
                public void onFailure(Call<IpInfo> call, Throwable t) {
                    Timber.e("Problem with obtain of ipInfo", t);

                    getViewOptional().dismissProgress();
                    getViewOptional().showError(context.getResources().getString(R.string.ip_message_show_no_content));
                }
            });
        } else {
            getViewOptional().showError(context.getResources().getString(R.string.ip_message_show_no_content));
        }
    }

    public IpInfo getIpInfo() {
        return mIpInfo;
    }
}
