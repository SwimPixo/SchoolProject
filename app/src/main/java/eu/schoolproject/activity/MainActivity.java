package eu.schoolproject.activity;

import android.os.Bundle;

import eu.schoolproject.R;
import eu.schoolproject.activity.base.ProjectBaseActivity;
import eu.schoolproject.fragment.MainFragment;

public class MainActivity extends ProjectBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showToolbar(false);

        if (null == savedInstanceState) {
            replaceFragment(MainFragment.newInstance(), R.id.main_container);
        }
    }
}
