package eu.schoolproject.viewModel.view;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import eu.inloop.viewmodel.IView;
import eu.schoolproject.api.model.IpInfo;
import eu.schoolproject.util.view.LoadingView;

/**
 * Created by BP on 29/12/2017.
 */

public interface IIpInfoView extends IView {

    Activity getActivity();

    void showProgress();
    void showProgress(final @NonNull String message);
    void dismissProgress();
    void showError(final @NonNull String message);
    void showError(final @NonNull String message, final @Nullable LoadingView.OnAgainClickListener onAgainClickListener);

    void showIpInfo(final @NonNull IpInfo ipInfo);
}

