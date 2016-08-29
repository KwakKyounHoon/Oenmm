package com.onemeter.omm.onemm.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.Following;
import com.onemeter.omm.onemm.viewholder.FollowingViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Tacademy on 2016-08-29.
 */
public class FollowingAdapter extends RecyclerView.Adapter<FollowingViewHolder> implements FollowingViewHolder.OnFollowingItemClickListener {

    List<Following> followings = new ArrayList<>();

    public void addAll(Following following){
        this.followings.addAll(Arrays.asList(following));
        notifyDataSetChanged();
    }

    public void addFollowings(Following following){
        followings.add(following);
        notifyDataSetChanged();
    }

    public void clearData(){
        this.followings.clear();
        notifyDataSetChanged();
    }

    @Override
    public FollowingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_following_item, parent, false);
        return new FollowingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FollowingViewHolder holder, int position) {
        FollowingViewHolder fvh = (FollowingViewHolder)holder;
        fvh.setFollowing(followings.get(position));
        fvh.setOnFollowingClickListener(this);
    }

    @Override
    public int getItemCount() {
        return followings.size();
    }

    @Override
    public void onItemClick(View view, Following following, int position) {
        if(listener != null){
            listener.onAdapterItemClick(view, following, position);
        }
    }

    @Override
    public void onArrowClick(View view, Following following, int position) {
        if(listener != null){
            listener.onAdapterArrowClick(view, following, position);
        }
    }

    public interface OnAdapterItemClickLIstener {
        public void onAdapterItemClick(View view, Following following, int position);
        public void onAdapterArrowClick(View view, Following following, int position);
    }

    OnAdapterItemClickLIstener listener;
    public void setOnAdapterItemClickListener(OnAdapterItemClickLIstener listener) {
        this.listener = listener;
    }
}
