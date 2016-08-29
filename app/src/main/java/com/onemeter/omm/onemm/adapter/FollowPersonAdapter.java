package com.onemeter.omm.onemm.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.FollowPerson;
import com.onemeter.omm.onemm.viewholder.FollowPersonViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-08-29.
 */
public class FollowPersonAdapter extends RecyclerView.Adapter<FollowPersonViewHolder> {

    List<FollowPerson> items = new ArrayList<>();

    public void add(FollowPerson p) {
        items.add(p);
        notifyDataSetChanged();
    }

    @Override
    public FollowPersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_follow_person, parent, false);
        FollowPersonViewHolder holder = new FollowPersonViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(FollowPersonViewHolder holder, int position) {
        holder.setFollowPerson(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
