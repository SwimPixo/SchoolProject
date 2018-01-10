package eu.schoolproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import eu.schoolproject.R;
import eu.schoolproject.activity.base.ProjectBaseActivity;
import eu.schoolproject.api.model.IpInfo;
import eu.schoolproject.fragment.IpInfoFragment;

public class IpInfoActivity extends ProjectBaseActivity {

    private final static String EXTRA_IP_INFO = "IpInfoActivity.EXTRA_IP_INFO";
    private final static String EXTRA_IP_INFO_ADDRESS = "IpInfoActivity.EXTRA_IP_INFO_ADDRESS";

    public static void call(final @NonNull Activity activity, final @NonNull IpInfo ipInfo) {
        final Intent intent = new Intent(activity, IpInfoActivity.class);
        intent.putExtra(EXTRA_IP_INFO, ipInfo);
        activity.startActivity(intent);
    }

    public static void call(final @NonNull Activity activity, final @NonNull String ipInfoAddress) {
        final Intent intent = new Intent(activity, IpInfoActivity.class);
        intent.putExtra(EXTRA_IP_INFO_ADDRESS, ipInfoAddress);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip_info);

        showToolbar(true);

        if (null == savedInstanceState) {
            final Bundle bundle = getIntent().getExtras();
            if (null != bundle) {
                final String ipInfoAddress = bundle.getString(EXTRA_IP_INFO_ADDRESS);
                final IpInfo ipInfo = (IpInfo) bundle.getSerializable(EXTRA_IP_INFO);

                if (null != ipInfoAddress) {
                    replaceFragment(IpInfoFragment.newInstance(ipInfoAddress), R.id.ip_info_container);
                } else if (null != ipInfo) {
                    replaceFragment(IpInfoFragment.newInstance(ipInfo), R.id.ip_info_container);
                }
            }
        }
    }
}
