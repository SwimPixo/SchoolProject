package eu.schoolproject;

import android.app.Application;

import eu.schoolproject.util.sl.SL;
import timber.log.Timber;

/**
 * Created by BP on 29/12/2017.
 */

public class AppClass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SL.init(getApplicationContext());

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
