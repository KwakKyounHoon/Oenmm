package com.onemeter.omm.onemm.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.Follower;
import com.onemeter.omm.onemm.viewholder.FollowerViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Tacademy on 2016-08-29.
 */
public class FollowerAdatper extends RecyclerView.Adapter<FollowerViewHolder> implements FollowerViewHolder.OnFollowerItemClickListener{

    List<Follower> followers = new ArrayList<>();

    public void addAll(Follower[] followers){
        this.followers.addAll(Arrays.asList(followers));
        notifyDataSetChanged();
    }

    public void addFollwers(Follower follower){
        followers.add(follower);
        notifyDataSetChanged();
    }

    public void clearData(){
        this.followers.clear();
        notifyDataSetChanged();
    }



    @Override
    public FollowerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_follower_item, parent, false);
        FollowerViewHolder viewHolder = new FollowerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FollowerViewHolder holder, int position) {
        FollowerViewHolder fvh = (FollowerViewHolder)holder;
        fvh.setOnFollowerClickListener(this);
        fvh.setFollower(followers.get(position));
    }

    @Override
    public int getItemCount() {
        return followers.size();
    }

    @Override
    public void onItemClick(View view, Follower follower, int position) {
        if(listener != null){
            listener.onAdapterItemClick(view, follower, position);
        }
    }

    @Override
    public void onlikeClick(View view, Follower follower, int position, boolean followFlag) {
        if(listener != null){
            listener.onAdapterLikeClick(view, follower, position, followFlag);
        }
    }

    public interface OnAdapterItemClickLIstener {
        public void onAdapterItemClick(View view, Follower follower, int position);
        public void onAdapterLikeClick(View view, Follower follower, int position, boolean followFlag);
    }

    OnAdapterItemClickLIstener listener;
    public void setOnAdapterItemClickListener(OnAdapterItemClickLIstener listener) {
        this.listener = listener;
    }


}
