package com.onemeter.omm.onemm.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.MyData;
import com.onemeter.omm.onemm.viewholder.MyHeaderViewHolder;
import com.onemeter.omm.onemm.viewholder.MyTabViewHolder;

/**
 * Created by Tacademy on 2016-08-23.
 */
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    MyData myData;
    FragmentManager manager;

    public MyAdapter(FragmentManager manager){
        this.manager = manager;
    }

    public void addMyData(MyData myData){
        this.myData = myData;
        notifyDataSetChanged();
    }

    public static final int VIEW_TYPE_HEADER = 1;
    public static final int VIEW_TYPE_TAP = 2;

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return VIEW_TYPE_HEADER;
        position--;
        if(position == 0){
            return VIEW_TYPE_TAP;
        }
        throw new IllegalArgumentException("invalid position");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_HEADER : {
                    View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_my_header, parent, false);
                return new MyHeaderViewHolder(headerView);
            }
            case VIEW_TYPE_TAP : {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_my_tab, parent, false);
                return new MyTabViewHolder(view, manager);
            }
        }
        throw new IllegalArgumentException("invalid viewtype");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0){
            MyHeaderViewHolder mhvh = (MyHeaderViewHolder)holder;
            mhvh.setUserInof(myData);
            return;
        }
        position--;
        if(position == 0){
            MyTabViewHolder myvh = (MyTabViewHolder)holder;
            return;
        }
        throw new IllegalArgumentException("invalid position");
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
