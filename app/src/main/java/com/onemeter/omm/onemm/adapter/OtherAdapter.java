package com.onemeter.omm.onemm.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.OtherData;
import com.onemeter.omm.onemm.data.OtherPageData;
import com.onemeter.omm.onemm.data.Post;
import com.onemeter.omm.onemm.viewholder.OtherCategoryViewHolder;
import com.onemeter.omm.onemm.viewholder.OtherHeaderViewHolder;
import com.onemeter.omm.onemm.viewholder.OtherPostViewHolder;
import com.onemeter.omm.onemm.viewholder.OtherTabViewHolder;

import java.util.Arrays;


/**
 * Created by Tacademy on 2016-08-24.
 */
public class OtherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        OtherHeaderViewHolder.OnOtherDataItemClickListener, OtherCategoryViewHolder.OnOtherCategoryItemClickListener,
        OtherPostViewHolder.OnOtherItemClickListener, OtherTabViewHolder.OnTabItemClickListener{
    OtherPageData otherPageData = new OtherPageData();



    public void addOtherData(OtherPageData otherPageData) {
        this.otherPageData = otherPageData;
        notifyDataSetChanged();
    }

    public void addOtherData(OtherData[] otherDatas) {
        otherPageData.setOtherData(otherDatas[0]);
        notifyDataSetChanged();
    }

    public void addPost(Post post){
        otherPageData.getPostDatas().add(post);
        notifyDataSetChanged();
    }

    public void addAllPost(Post[] post){
        otherPageData.getPostDatas().addAll(Arrays.asList(post));
        notifyDataSetChanged();
    }

    public void addOtherInfo(OtherData otherData){
        otherPageData.setOtherData(otherData);
        notifyDataSetChanged();
    }

    public void clearPost(){
        otherPageData.getPostDatas().clear();
        notifyDataSetChanged();
    }


    public static final int VIEW_TYPE_HEADER = 1;
    public static final int VIEW_TYPE_TAP = 2;
    public static final int VIEW_TYPE_CTEGORY = 3;
    public static final int VIEW_TYPE_POST = 4;

    @Override
    public int getItemViewType(int position) {
        if(otherPageData.getOtherData() != null) {
            if (position == 0) return VIEW_TYPE_HEADER;
            position--;
        }
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
                View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_other_header, parent, false);
                return new OtherHeaderViewHolder(headerView);
            }
            case VIEW_TYPE_TAP: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_tab, parent, false);
                return new OtherTabViewHolder(view);
            }
            case VIEW_TYPE_CTEGORY: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_other_category, parent, false);
                return new OtherCategoryViewHolder(view);
            }
            case VIEW_TYPE_POST: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_post, parent, false);
                return new OtherPostViewHolder(view);
            }
        }
        throw new IllegalArgumentException("invalid viewtype");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(otherPageData.getOtherData() != null) {
            if (position == 0) {
                OtherHeaderViewHolder othvh = (OtherHeaderViewHolder) holder;
                othvh.setOnOtherDataItemClickListener(this);
                othvh.setOtherInof(otherPageData.getOtherData());
                return;
            }
            position--;
        }
        if (position == 0) {
            OtherTabViewHolder othvh = (OtherTabViewHolder) holder;
            othvh.setOnTabClickListener(this);
            return;
        }
        position--;

        if (position == 0) {
            OtherCategoryViewHolder ocvh = (OtherCategoryViewHolder) holder;
            ocvh.setOnOtherCategoryItemClickListener(this);
            return;
        }

        position--;
        if (otherPageData.getPostDatas().size() > 0) {
            if (position < otherPageData.getPostDatas().size()) {
                OtherPostViewHolder otpvh = (OtherPostViewHolder) holder;
                otpvh.setPost(otherPageData.getPostDatas().get(position));
                otpvh.setOnOtherItemClickListener(this);
                return;
            }
            position -= otherPageData.getPostDatas().size();
        }
        throw new IllegalArgumentException("invalid position");
    }

    @Override
    public int getItemCount() {
        int ctn = 3;
        if (otherPageData == null) return 0;
        if(otherPageData.getOtherData() == null) ctn--;
        return otherPageData.getPostDatas().size() + ctn;
    }


    @Override
    public void onFollowingItemClick(View view, OtherData otherData, int position) {
        if(listener != null) {
            listener.onAdapterFollowingClick(view, otherData, position);
        }
    }

    @Override
    public void onFollowerItemClick(View view, OtherData otherData, int position) {
        if(listener != null) {
            listener.onAdapterFollowerClick(view, otherData, position);
        }
    }

    @Override
    public void onSoundItemClick(View view, OtherData otherData, int position) {
        if(listener != null) {
            listener.onAdapterSoundClick(view, otherData, position);
        }
    }

    @Override
    public void onCategoryItemClick(Boolean flag) {
        if(listener != null) {
            listener.onAdapterCategoryItemClick(flag);
        }
    }

    @Override
    public void onPostItemClick(View view, Post post, int position) {
        if(listener != null){
            listener.onAdapterItemClick(view, post, position);
        }
    }

    @Override
    public void onPlayClick(View view, Post post, int position) {
        if(listener != null){
            listener.onAdapterPlayClick(view,post,position);
        }
    }

    @Override
    public void onTabType(View view, int num) {
        if(listener != null){
            listener.onAdapterTabType(view, num);
        }
    }


    public interface OnAdapterItemClickLIstener {

        public void onAdapterFollowingClick(View view, OtherData otherData, int position);
        public void onAdapterFollowerClick(View view, OtherData otherData, int position);
        public void onAdapterSoundClick(View view, OtherData otherData, int position);
        public void onAdapterCategoryItemClick(boolean flag);

        public void onAdapterItemClick(View view, Post post, int position);
        public void onAdapterPlayClick(View view, Post post, int position);

        public void onAdapterTabType(View view, int type);

    }

    OnAdapterItemClickLIstener listener;

    public void setOnAdapterItemClickListener(OnAdapterItemClickLIstener listener) {
        this.listener = listener;
    }
}

