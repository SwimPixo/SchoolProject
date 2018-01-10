package eu.schoolproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.schoolproject.R;
import eu.schoolproject.api.model.IpInfo;
import eu.schoolproject.util.Util;

/**
 * Created by BP on 07/04/2017.
 */

public class    IpInfoAdapter extends RecyclerView.Adapter<IpInfoAdapter.ViewHolder> {

    private @NonNull ArrayList<IpInfo> mIpInfos;
    private final @NonNull Context mContext;

    private RecyclerItemClickListener mRecyclerItemClickListener;

    public IpInfoAdapter(final @NonNull Context context, final @NonNull ArrayList<IpInfo> ipInfos) {
        mIpInfos = ipInfos;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        final View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_list_ip_info, parent, false);
        final IpInfoAdapter.ViewHolder viewHolder = new IpInfoAdapter.ViewHolder(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerItemClickListener.onItemClick(view, viewHolder.getAdapterPosition());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final IpInfo ipInfo = mIpInfos.get(position);
        if (null != ipInfo) {
            holder.mIpName.setText(ipInfo.getIp());
            holder.mIpDate.setText(Util.mFancyDateTimeFormat.get().format(ipInfo.getDateTime()));
        }
    }

    @Override
    public int getItemCount() {
        return mIpInfos.size();
    }

    public IpInfo getItem(final int position) {
        return mIpInfos.get(position);
    }

    public void setOnItemClickListener(final RecyclerItemClickListener recyclerItemClickListener) {
        mRecyclerItemClickListener = recyclerItemClickListener;
    }

    public void setData(final @NonNull ArrayList<IpInfo> ipInfos) {
        mIpInfos = ipInfos;
        notifyDataSetChanged();
    }

    public void addData(final IpInfo ipInfo) {
        mIpInfos.add(ipInfo);
        notifyDataSetChanged();
    }

    static final class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_ip_name)
        protected TextView mIpName;
        @BindView(R.id.item_ip_date)
        protected TextView mIpDate;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface RecyclerItemClickListener {
        void onItemClick(final @NonNull View view, final int position);
    }
}
