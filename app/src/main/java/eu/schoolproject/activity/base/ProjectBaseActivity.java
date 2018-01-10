package eu.schoolproject.activity.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import butterknife.ButterKnife;
import eu.inloop.viewmodel.AbstractViewModel;
import eu.inloop.viewmodel.IView;
import eu.inloop.viewmodel.base.ViewModelBaseActivity;

/**
 * Created by BP on 29/12/2017.
 */

public class ProjectBaseActivity<T extends IView, M extends AbstractViewModel<T>> extends ViewModelBaseActivity<T, M> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        ButterKnife.bind(this);
    }

    protected void showToolbar(final boolean withHomeEnabled) {
        final ActionBar toolbar = getSupportActionBar();
        if (null != toolbar) {
            if (withHomeEnabled && null != toolbar) {
                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    protected void setToolbarTitle(final @NonNull String title) {
        final ActionBar toolbar = getSupportActionBar();
        if (null != toolbar) {
            toolbar.setTitle(title);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void replaceFragmentWithBack(final @NonNull Fragment newFragment, final int fragmentContainerId) {
        replaceFragmentWithBack(newFragment, fragmentContainerId, false);
    }

    public void replaceFragmentWithBack(final @NonNull Fragment newFragment, final int fragmentContainerId, final boolean useAnimation) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(fragmentContainerId, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void replaceFragment(final @NonNull Fragment newFragment, final int fragmentContainerId) {
        replaceFragment(newFragment, fragmentContainerId, false);
    }

    public void replaceFragment(final @NonNull Fragment newFragment, final int fragmentContainerId, final boolean useAnimation) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(fragmentContainerId, newFragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}