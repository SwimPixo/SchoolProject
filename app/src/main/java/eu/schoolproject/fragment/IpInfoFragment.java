package eu.schoolproject.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import eu.schoolproject.R;
import eu.schoolproject.activity.IpInfoActivity;
import eu.schoolproject.activity.MapActivity;
import eu.schoolproject.api.model.IpInfo;
import eu.schoolproject.fragment.base.ProjectBaseFragment;
import eu.schoolproject.util.Util;
import eu.schoolproject.viewModel.IpInfoViewModel;
import eu.schoolproject.viewModel.view.IIpInfoView;

/**
 * Created by BP on 29/12/2017.
 */

public class IpInfoFragment extends ProjectBaseFragment<IIpInfoView, IpInfoViewModel> implements IIpInfoView {

    @BindView(R.id.ip_detail_ip)
    protected TextView mDetailIp;
    @BindView(R.id.ip_detail_city)
    protected TextView mDetailCity;
    @BindView(R.id.ip_detail_region)
    protected TextView mDetailRegion;
    @BindView(R.id.ip_detail_country)
    protected TextView mDetailCountry;
    @BindView(R.id.ip_detail_lat_long)
    protected TextView mDetailLatLong;
    @BindView(R.id.ip_detail_time_zone)
    protected TextView mDetailTimeZone;
    @BindView(R.id.ip_detail_postal_code)
    protected TextView mDetailPostalCode;
    @BindView(R.id.ip_detail_asn)
    protected TextView mDetailAsn;
    @BindView(R.id.ip_detail_org)
    protected TextView mDetailOrg;

    public static IpInfoFragment newInstance(final @NonNull String ipInfoAddress) {
        final IpInfoFragment fragment = new IpInfoFragment();
        fragment.setArguments(IpInfoViewModel.createBundle(ipInfoAddress));
        return fragment;
    }

    public static IpInfoFragment newInstance(final @NonNull IpInfo ipInfo) {
        final IpInfoFragment fragment = new IpInfoFragment();
        fragment.setArguments(IpInfoViewModel.createBundle(ipInfo));
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ip_info, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setModelView(this);
    }

    @Override
    public void showIpInfo(final @NonNull IpInfo ipInfos) {
        mDetailIp.setText(ipInfos.getIp());
        mDetailCity.setText(ipInfos.getCity());
        mDetailRegion.setText(ipInfos.getRegion());
        mDetailCountry.setText(ipInfos.getCountry());
        mDetailLatLong.setText(ipInfos.getLatitude() + " / " + ipInfos.getLongitude());
        mDetailTimeZone.setText(ipInfos.getTimezone());
        mDetailPostalCode.setText(ipInfos.getPostal());
        mDetailAsn.setText(ipInfos.getAsn() + "");
        mDetailOrg.setText(ipInfos.getOrg());
    }

    @OnClick(R.id.ip_btn_map)
    protected void onMapClicked() {
        final IpInfo ipInfo = getViewModel().getIpInfo();
        if (null != ipInfo && ipInfo.getLatitude() != 0d && ipInfo.getLongitude() != 0d) {
            MapActivity.call(getActivity(), ipInfo);
        } else {
            Util.showInfo(getActivity(), getActivity().getResources().getString(R.string.ip_message_show_no_gps));
        }
    }
}