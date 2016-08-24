package com.onemeter.omm.onemm.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.OtherData;
import com.onemeter.omm.onemm.data.OtherPageData;
import com.onemeter.omm.onemm.viewholder.OtherCategoryViewHolder;
import com.onemeter.omm.onemm.viewholder.OtherHeaderViewHolder;
import com.onemeter.omm.onemm.viewholder.OtherPostViewHolder;
import com.onemeter.omm.onemm.viewholder.OtherTabViewHolder;


/**
 * Created by Tacademy on 2016-08-24.
 */
public class OtherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OtherHeaderViewHolder.OnMyDataItemClickListener {
    OtherPageData otherPageData;
    FragmentManager manager;


    public OtherAdapter(FragmentManager manager) {
        this.manager = manager;
    }

    public void addOtherData(OtherPageData otherPageData) {
        this.otherPageData = otherPageData;
        notifyDataSetChanged();
    }

    public static final int VIEW_TYPE_HEADER = 1;
    public static final int VIEW_TYPE_TAP = 2;
    public static final int VIEW_TYPE_CTEGORY = 3;
    public static final int VIEW_TYPE_POST = 4;

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return VIEW_TYPE_HEADER;
        position--;
        if (position == 0) return VIEW_TYPE_TAP;
        position--;

        if (position == 0) return VIEW_TYPE_CTEGORY;
        position--;

        if (otherPageData.getPostDatas().size() > 0) {
            if (position < otherPageData.getPostDatas().size()) {
                return VIEW_TYPE_POST;
            }
            position -= otherPageData.getPostDatas().size();
        }

        throw new IllegalArgumentException("invalid position");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_HEADER: {
                View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_my_header, parent, false);
                return new OtherHeaderViewHolder(headerView);
            }
            case VIEW_TYPE_TAP: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_my_tab, parent, false);
                return new OtherTabViewHolder(view);
            }
            case VIEW_TYPE_CTEGORY: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_my_category, parent, false);
                return new OtherCategoryViewHolder(view);
            }
            case VIEW_TYPE_POST: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_my_post, parent, false);
                return new OtherPostViewHolder(view);
            }
        }
        throw new IllegalArgumentException("invalid viewtype");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            OtherHeaderViewHolder othvh = (OtherHeaderViewHolder) holder;
            othvh.setOnMyDataItemClickListener(this);
            othvh.setOtherInof(otherPageData.getOtherData());
            return;
        }
        position--;
        if (position == 0) {
            OtherTabViewHolder othvh = (OtherTabViewHolder) holder;
            return;
        }
        position--;

        if (position == 0) {
            OtherCategoryViewHolder ocvh = (OtherCategoryViewHolder) holder;
            return;
        }

        position--;
        if (otherPageData.getPostDatas().size() > 0) {
            if (position < otherPageData.getPostDatas().size()) {
                OtherPostViewHolder otpvh = (OtherPostViewHolder) holder;
                return;
            }
            position -= otherPageData.getPostDatas().size();
        }
        throw new IllegalArgumentException("invalid position");
    }

    @Override
    public int getItemCount() {
        if (otherPageData == null) return 0;

        return otherPageData.getPostDatas().size() + 3;
    }

    @Override
    public void onDonateItemClick(View view, OtherData otherData, int position) {

    }

    @Override
    public void onFollowingItemClick(View view, OtherData otherData, int position) {

    }

    @Override
    public void onFollowerItemClick(View view, OtherData otherData, int position) {

    }

    @Override
    public void onSoundItemClick(View view, OtherData otherData, int position) {

    }

    @Override
    public void onPhotoItemClick(View view, OtherData otherData, int position) {

    }

    @Override
    public void onProfileItemClick(View view, OtherData otherData, int position) {

    }


    public interface OnAdapterItemClickLIstener {
        public void onAdapterDonateClick(View view, OtherData otherData, int position);

        public void onAdapterFollowingClick(View view, OtherData otherData, int position);

        public void onAdapterFollowerClick(View view, OtherData otherData, int position);

        public void onAdapterSoundClick(View view, OtherData otherData, int position);

        public void onAdapterPhotoClick(View view, OtherData otherData, int position);

        public void onAdapterProfileClick(View view, OtherData otherData, int position);

    }

    OnAdapterItemClickLIstener listener;

    public void setOnAdapterItemClickListener(OnAdapterItemClickLIstener listener) {
        this.listener = listener;
    }
}

