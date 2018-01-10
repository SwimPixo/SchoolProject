package eu.schoolproject.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import eu.schoolproject.R;
import eu.schoolproject.api.model.IpInfo;
import eu.schoolproject.fragment.base.ProjectBaseFragment;

/**
 * Created by BP on 29/12/2017.
 */

public class MapFragment extends ProjectBaseFragment implements OnMapReadyCallback {

    private final static String ARG_IP_INFO = "MapFragment.ARG_IP_INFO";

    private static final float CLOSURE_ZOOM_LEVEL = 15;

    @BindView(R.id.ip_detail_map_view)
    protected MapView mMapView;

    private GoogleMap mGoogleMap;
    private IpInfo mIpInfo;

    public static MapFragment newInstance(final @NonNull IpInfo ipInfo) {
        final MapFragment fragment = new MapFragment();
        fragment.setArguments(createBundle(ipInfo));
        return fragment;
    }

    public static Bundle createBundle(final @Nullable IpInfo ipInfo) {
        final Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_IP_INFO, ipInfo);
        return bundle;
    }

    private void parseBundle(final @Nullable Bundle savedInstanceState) {
        if (null != savedInstanceState) {
            mIpInfo = (IpInfo) savedInstanceState.getSerializable(ARG_IP_INFO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parseBundle(getArguments());
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);
    }
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroyView() {
        mMapView.onDestroy();
        super.onDestroyView();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setBuildingsEnabled(true);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);

        final LatLng pinLocation = new LatLng(mIpInfo.getLatitude(), mIpInfo.getLongitude());

        mGoogleMap.addMarker(new MarkerOptions()
                .position(pinLocation)
                .title(mIpInfo.getIp())
                .snippet(mIpInfo.getCity()));

        showLocation(pinLocation);
    }

    private void showLocation(final @NonNull LatLng latLng) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, CLOSURE_ZOOM_LEVEL);
        mGoogleMap.animateCamera(cameraUpdate);
    }
}
