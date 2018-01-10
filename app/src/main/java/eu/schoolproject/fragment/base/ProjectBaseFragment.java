package eu.schoolproject.fragment.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import eu.inloop.viewmodel.AbstractViewModel;
import eu.inloop.viewmodel.IView;
import eu.inloop.viewmodel.base.ViewModelBaseFragment;
import eu.schoolproject.R;
import eu.schoolproject.util.view.LoadingView;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by BP on 29/12/2017.
 */

public class ProjectBaseFragment<T extends IView, M extends AbstractViewModel<T>> extends ViewModelBaseFragment<T, M> {

    private @NonNull Unbinder mUnbinder;
    private @Nullable LayoutInflater mLayoutInflater;

    @Nullable
    @BindView(R.id.loading_view)
    public LoadingView mLoadingView;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mUnbinder = ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    protected void hideSoftKeyBoard() {
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);

        if (imm.isAcceptingText() && null != getActivity().getCurrentFocus()) {
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void showProgress() {
        showProgress(null);
    }

    public void showProgress(final @Nullable String message) {
        if (null == mLoadingView) {
            throw new IllegalStateException("Loading layout is not included in this fragment");
        }

        mLoadingView.showLoading();
        if (null != message) {
            mLoadingView.setText(message);
        }
    }

    public void dismissProgress() {
        if (null == mLoadingView) {
            throw new IllegalStateException("Loading layout is not included in this fragment");
        }

        mLoadingView.dismiss();
    }

    public void showError(final @NonNull String message) {
        showError(message, null);
    }

    public void showError(final @NonNull String message, final @Nullable LoadingView.OnAgainClickListener onAgainClickListener) {
        if (null == mLoadingView) {
            throw new IllegalStateException("Loading layout is not included in this fragment");
        }

        if (null != onAgainClickListener) {
            mLoadingView.showError(message, onAgainClickListener);
        } else {
            mLoadingView.showError(message);
        }
    }
}

