package eu.schoolproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import eu.schoolproject.R;
import eu.schoolproject.activity.base.ProjectBaseActivity;
import eu.schoolproject.api.model.IpInfo;
import eu.schoolproject.fragment.MapFragment;

/**
 * Created by BP on 29/12/2017.
 */

public class MapActivity extends ProjectBaseActivity {

    private final static String EXTRA_IP_INFO = "MapActivity.EXTRA_IP_INFO";

    public static void call(final @NonNull Activity activity, final @NonNull IpInfo ipInfo) {
        final Intent intent = new Intent(activity, MapActivity.class);
        intent.putExtra(EXTRA_IP_INFO, ipInfo);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        showToolbar(true);

        if (null == savedInstanceState) {
            final Bundle bundle = getIntent().getExtras();
            if (null != bundle) {
                final IpInfo ipInfo = (IpInfo) bundle.getSerializable(EXTRA_IP_INFO);
                replaceFragment(MapFragment.newInstance(ipInfo), R.id.map_container);
            }
        }
    }
}
