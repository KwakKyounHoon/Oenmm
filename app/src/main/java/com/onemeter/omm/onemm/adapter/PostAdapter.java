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

    String time = "";
    int timePosition = -1;

    public void setTime(String time, int timePosition){
        this.time = time;
        this.timePosition = timePosition;
        notifyDataSetChanged();
    }

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
        if(position == timePosition)  pvh.setTime(time);
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

    @Override
    public void onAnswerClick(View view, Post post, int position) {
        if(listener != null){
            listener.onAdapterAnswerClick(view, post, position);
        }
    }

    @Override
    public void onQuestionerClick(View view, Post post, int position) {
        if(listener != null){
            listener.onAdapterQuestionerClick(view, post, position);
        }
    }

    public interface OnAdapterItemClickLIstener {
        public void onAdapterPostItemClick(View view, Post post, int position);
        public void onAdpaterPlayClick(View view, Post post, int position);
        public void onAdapterAnswerClick(View view, Post post, int position);
        public void onAdapterQuestionerClick(View view, Post post, int position);
    }

    OnAdapterItemClickLIstener listener;
    public void setOnAdapterItemClickListener(OnAdapterItemClickLIstener listener) {
        this.listener = listener;
    }
}
