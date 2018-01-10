package eu.schoolproject.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import eu.schoolproject.R;
import eu.schoolproject.activity.IpInfoActivity;
import eu.schoolproject.adapter.IpInfoAdapter;
import eu.schoolproject.api.model.IpInfo;
import eu.schoolproject.fragment.base.ProjectBaseFragment;
import eu.schoolproject.service.ConnectionProvider;
import eu.schoolproject.util.Util;
import eu.schoolproject.util.sl.SL;
import eu.schoolproject.viewModel.MainViewModel;
import eu.schoolproject.viewModel.view.IMainView;

/**
 * Created by BP on 29/12/2017.
 */

public class MainFragment extends ProjectBaseFragment<IMainView, MainViewModel> implements IMainView {

    @BindView(R.id.ip_list_empty)
    protected TextView mEmptyTextView;
    @BindView(R.id.ip_list_view)
    protected RecyclerView mIpListView;
    @BindView(R.id.ip_text_field)
    protected EditText mIpTextField;
    
    private IpInfoAdapter mIpInfoAdapter;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initAdapter();
        initListView();

        setModelView(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (null != mIpInfoAdapter) {
            mIpInfoAdapter.setData(getViewModel().getIpInfos());
            showHistoryOfSearch(getViewModel().getIpInfos());
        }
    }

    private void initAdapter() {
        mIpInfoAdapter = new IpInfoAdapter(getActivity(), new ArrayList<IpInfo>());
        mIpInfoAdapter.setOnItemClickListener(new IpInfoAdapter.RecyclerItemClickListener() {
            @Override
            public void onItemClick(final @NonNull View view, final int position) {
                final IpInfo ipInfo = mIpInfoAdapter.getItem(position);
                if (null != ipInfo) {
                    IpInfoActivity.call(getActivity(), ipInfo);
                }
            }
        });
    }

    private void initListView() {
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mIpListView.setLayoutManager(mLayoutManager);
        mIpListView.setItemAnimator(new DefaultItemAnimator());
        mIpListView.setAdapter(mIpInfoAdapter);
    }
    
    @OnClick(R.id.ip_btn_search)
    protected void onSearchBtnClicked() {
        if (SL.get(ConnectionProvider.class).isConnectionAvailable()) {
            final String ipToAddress = mIpTextField.getText().toString().trim();
            if (!ipToAddress.isEmpty()) {
                if (validate(ipToAddress)    ) {
                    IpInfoActivity.call(getActivity(), ipToAddress);
                }
                else {
                    Util.showInfo(getActivity(), getActivity().getResources().getString(R.string.ip_detail_show_no_valid_ip));
                }
            } else {
                Util.showInfo(getActivity(), getActivity().getResources().getString(R.string.ip_message_show_empty_text));
            }
        } else {
            Util.showInfo(getActivity(), getActivity().getResources().getString(R.string.ip_message_show_no_internet));
        }
    }

    public boolean validate(final String ip) {
        String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";

        return ip.matches(PATTERN);
    }

    @Override
    public void showHistoryOfSearch(@Nullable ArrayList<IpInfo> ipInfos) {
        if (null == ipInfos) {
            mEmptyTextView.setVisibility(View.VISIBLE);
        } else {
            mEmptyTextView.setVisibility(View.GONE);
            mIpInfoAdapter.setData(ipInfos);
        }
    }
}