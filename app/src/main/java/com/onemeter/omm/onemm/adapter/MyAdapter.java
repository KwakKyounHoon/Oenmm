package com.onemeter.omm.onemm.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.MyData;
import com.onemeter.omm.onemm.data.MyPageData;
import com.onemeter.omm.onemm.viewholder.MyCategoryViewHolder;
import com.onemeter.omm.onemm.viewholder.MyHeaderViewHolder;
import com.onemeter.omm.onemm.viewholder.MyPostViewHolder;
import com.onemeter.omm.onemm.viewholder.MyTabViewHolder;

/**
 * Created by Tacademy on 2016-08-23.
 */
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements MyHeaderViewHolder.OnMyDataItemClickListener, MyTabViewHolder.OnTabItemClickListener{

    MyPageData myPageData;

    FragmentManager manager;
    boolean categoryFlag = true;

    public MyAdapter(FragmentManager manager){
        this.manager = manager;
    }

    public void addMyData(MyPageData myPageData){
        this.myPageData = myPageData;
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
        if(position == 0) return VIEW_TYPE_TAP;
        position--;
        if(categoryFlag){
            if(position == 0) return VIEW_TYPE_CTEGORY;
            position--;
        }
        if (myPageData.getPostDatas().size() > 0) {
            if (position < myPageData.getPostDatas().size()) {
                return VIEW_TYPE_POST;
            }
            position -= myPageData.getPostDatas().size();
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
                return new MyTabViewHolder(view);
            }
            case VIEW_TYPE_CTEGORY :{
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_my_category, parent, false);
                return new MyCategoryViewHolder(view);
            }
            case VIEW_TYPE_POST :{
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_my_post, parent, false);
                return new MyPostViewHolder(view);
            }
        }
        throw new IllegalArgumentException("invalid viewtype");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0){
            MyHeaderViewHolder mhvh = (MyHeaderViewHolder)holder;
            mhvh.setOnMyDataItemClickListener(this);
            mhvh.setUserInof(myPageData.getMyData());
            return;
        }
        position--;
        if(position == 0) {
            MyTabViewHolder mtvh = (MyTabViewHolder)holder;
            mtvh.setOnTabClickListener(this);
            return;
        }
        position--;
        if(categoryFlag) {
            if (position == 0) {
                MyCategoryViewHolder mcvh = (MyCategoryViewHolder) holder;
                return;
            }
        }
        position--;
        if (myPageData.getPostDatas().size() > 0) {
            if (position < myPageData.getPostDatas().size()) {
                MyPostViewHolder mpvh = (MyPostViewHolder)holder;
                return;
            }
            position -= myPageData.getPostDatas().size();
        }
        throw new IllegalArgumentException("invalid position");
    }

    @Override
    public int getItemCount() {
        if(myPageData == null) return 0;
        int ctn = 2;
        if(categoryFlag) {
            ctn++;
        }
        return myPageData.getPostDatas().size()+ctn;
    }

    @Override
    public void onDonateItemClick(View view, MyData myData, int position) {
        if(listener != null){
            listener.onAdapterDonateClick(view,myData,position);
        }
    }

    @Override
    public void onFollowingItemClick(View view, MyData myData, int position) {
        if(listener != null){
            listener.onAdapterFollowingClick(view,myData,position);
        }
    }

    @Override
    public void onFollowerItemClick(View view, MyData myData, int position) {
        if(listener != null){
            listener.onAdapterFollowerClick(view,myData,position);
        }
    }

    @Override
    public void onSoundItemClick(View view, MyData myData, int position) {
        if(listener != null){
            listener.onAdapterSoundClick(view,myData,position);
        }
    }

    @Override
    public void onPhotoItemClick(View view, MyData myData, int position) {
        if(listener != null){
            listener.onAdapterPhotoClick(view,myData,position);
        }
    }

    @Override
    public void onProfileItemClick(View view, MyData myData, int position) {
        if(listener != null){
            listener.onAdapterProfileClick(view,myData,position);
        }
    }

    @Override
    public void onCategory(View view, boolean flag) {
        categoryFlag = flag;
        notifyDataSetChanged();
    }


    public interface OnAdapterItemClickLIstener {
        public void onAdapterDonateClick(View view, MyData myData, int position);
        public void onAdapterFollowingClick(View view, MyData myData, int position);
        public void onAdapterFollowerClick(View view, MyData myData, int position);
        public void onAdapterSoundClick(View view, MyData myData, int position);
        public void onAdapterPhotoClick(View view, MyData myData, int position);
        public void onAdapterProfileClick(View view, MyData myData, int position);

    }

    OnAdapterItemClickLIstener listener;
    public void setOnAdapterItemClickListener(OnAdapterItemClickLIstener listener) {
        this.listener = listener;
    }
}
