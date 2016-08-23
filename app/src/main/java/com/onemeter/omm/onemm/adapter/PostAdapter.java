package com.onemeter.omm.onemm.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.PostData;
import com.onemeter.omm.onemm.viewholder.PostViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kwak on 2016-08-23.
 */
public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {

    List<PostData> postDatas = new ArrayList<>();

    public void addPostDats(List<PostData> postDatas){
       this.postDatas = postDatas;
        notifyDataSetChanged();
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_post_item, parent, false);
        PostViewHolder holder = new PostViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        PostViewHolder pvh = (PostViewHolder) holder;
        pvh.setPost(postDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return postDatas.size();
    }
}
