package eu.schoolproject.viewModel.view;

import android.support.annotation.Nullable;

import java.util.ArrayList;

import eu.inloop.viewmodel.IView;
import eu.schoolproject.api.model.IpInfo;

/**
 * Created by BP on 29/12/2017.
 */

public interface IMainView extends IView {

    void showHistoryOfSearch(final @Nullable ArrayList<IpInfo> ipInfos);
}
