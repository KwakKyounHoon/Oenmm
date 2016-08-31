package com.onemeter.omm.onemm.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.Post;
import com.onemeter.omm.onemm.viewholder.RankCategoryViewHolder;
import com.onemeter.omm.onemm.viewholder.RankPostViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Tacademy on 2016-08-26.
 */
public class RankPopularAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        RankPostViewHolder.OnRankPopularItemClickListener, RankCategoryViewHolder.OnRankCategoryItemClickListener{

    List<Post> posts = new ArrayList<>();

    public static final int VIEW_TYPE_CATEGORY = 1;
    public static final int VIEW_TYPE_POST = 2;


    public void addRankPopular(Post rankPopular){
        this.posts.add(rankPopular);
        notifyDataSetChanged();
    }

    public void addAll(Post[] populars){
        this.posts.addAll(Arrays.asList(populars));
        notifyDataSetChanged();
    }

    public void clearRankPopular(){
        posts.clear();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return VIEW_TYPE_CATEGORY;
        position--;
        if (posts.size() > 0) {
            if (position < posts.size()) {
                return VIEW_TYPE_POST;
            }
            position -= posts.size();
        }
        throw new IllegalArgumentException("invalid position");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_CATEGORY : {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_rank_category, parent, false);
                return new RankCategoryViewHolder(view);
            }
            case VIEW_TYPE_POST : {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_post, parent, false);
                return new RankPostViewHolder(view);
            }
        }
        throw new IllegalArgumentException("invalid viewtype");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            RankCategoryViewHolder mhvh = (RankCategoryViewHolder)holder;
            mhvh.setOnRankCategoryItemClickListener(this);
            return;
        }
        position--;
        if (posts.size() > 0) {
            if (position < posts.size()) {
                RankPostViewHolder rpvh = (RankPostViewHolder)holder;
                rpvh.setPost(posts.get(position));
                rpvh.setOnRankPopularItemClickListener(this);
                return;
            }
            position -= posts.size();
        }
        throw new IllegalArgumentException("invalid position");
    }

    @Override
    public int getItemCount() {
        return posts.size()+1;
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
            listener.onAdapterPlayClick(view, post, position);
        }
    }

    @Override
    public void onCategoryItemClick(Boolean flag) {
        if(listener != null){
            listener.onAdapterCategoryItemClick(flag);
        }
    }

    public interface OnAdapterItemClickLIstener {
        public void onAdapterItemClick(View view, Post post, int position);
        public void onAdapterPlayClick(View view, Post post, int position);
        public void onAdapterCategoryItemClick(Boolean flag);
    }

    OnAdapterItemClickLIstener listener;
    public void setOnAdapterItemClickListener(OnAdapterItemClickLIstener listener) {
        this.listener = listener;
    }
}
