package eu.schoolproject.service;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;

import eu.schoolproject.util.sl.IService;

/**
 * Created by BP on 29/12/2017.
 */

public class ConnectionProvider implements IService {

    private final Context mContext;

    public ConnectionProvider(final @NonNull Context context) {
        mContext = context;
    }

    public boolean isConnectionAvailable() {
        final ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return null != connectivityManager.getActiveNetworkInfo();
    }
}
