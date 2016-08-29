package com.onemeter.omm.onemm.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.Post;
import com.onemeter.omm.onemm.viewholder.PostViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Kwak on 2016-08-28.
 */
public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> implements PostViewHolder.OnOtherItemClickListener {
    List<Post> posts = new ArrayList<>();

    public void addPost(Post post){
        posts.add(post);
        notifyDataSetChanged();
    }

    public void addAll(Post[] items) {
        this.posts.addAll(Arrays.asList(items));
        notifyDataSetChanged();
    }

//    public void addAll(PostData2[] items) {
//        this.posts.addAll(Arrays.asList(items));
//        notifyDataSetChanged();
//    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        PostViewHolder pvh = (PostViewHolder)holder;
        pvh.setPost(posts.get(position));
        pvh.setOnOtherItemClickListener(this);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    @Override
    public void onPostItemClick(View view, Post post, int position) {
        if(listener != null){
            listener.onAdapterPostItemClick(view, post, position);
        }
    }

    @Override
    public void onPlayClick(View view, Post post, int position) {
        if(listener != null){
            listener.onAdpaterPlayClick(view, post, position);
        }
    }

    public interface OnAdapterItemClickLIstener {
        public void onAdapterPostItemClick(View view, Post post, int position);
        public void onAdpaterPlayClick(View view, Post post, int position);
    }

    OnAdapterItemClickLIstener listener;
    public void setOnAdapterItemClickListener(OnAdapterItemClickLIstener listener) {
        this.listener = listener;
    }
}
