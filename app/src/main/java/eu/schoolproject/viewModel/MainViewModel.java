package eu.schoolproject.viewModel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import eu.inloop.viewmodel.AbstractViewModel;
import eu.schoolproject.api.model.IpInfo;
import eu.schoolproject.event.RefreshList;
import eu.schoolproject.service.DatabaseService;
import eu.schoolproject.util.sl.SL;
import eu.schoolproject.viewModel.view.IMainView;

/**
 * Created by BP on 29/12/2017.
 */

public class MainViewModel extends AbstractViewModel<IMainView> {

    private ArrayList<IpInfo> mIpInfos;

    @Override
    public void onCreate(@Nullable Bundle arguments, @Nullable Bundle savedInstanceState) {
        super.onCreate(arguments, savedInstanceState);
        EventBus.getDefault().register(this);

        mIpInfos = (ArrayList<IpInfo>) SL.get(DatabaseService.class).getIpInfoDao().queryForAll();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onBindView(@NonNull IMainView view) {
        super.onBindView(view);

        if (null == mIpInfos || mIpInfos.isEmpty()) {
            view.showHistoryOfSearch(null);
        } else {
            view.showHistoryOfSearch(mIpInfos);
        }
    }

    public ArrayList<IpInfo> getIpInfos() {
        return mIpInfos;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(RefreshList event) {
        mIpInfos = (ArrayList<IpInfo>) SL.get(DatabaseService.class).getIpInfoDao().queryForAll();
    }
}
