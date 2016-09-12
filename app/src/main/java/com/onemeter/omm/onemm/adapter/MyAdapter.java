package com.onemeter.omm.onemm.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.MyData;
import com.onemeter.omm.onemm.data.MyPageData;
import com.onemeter.omm.onemm.data.Post;
import com.onemeter.omm.onemm.viewholder.MyCategoryViewHolder;
import com.onemeter.omm.onemm.viewholder.MyHeaderViewHolder;
import com.onemeter.omm.onemm.viewholder.MyPostViewHolder;
import com.onemeter.omm.onemm.viewholder.MyTabViewHolder;

import java.util.Arrays;

/**
 * Created by Tacademy on 2016-08-23.
 */
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements MyHeaderViewHolder.OnMyDataItemClickListener
        ,MyTabViewHolder.OnTabItemClickListener,MyCategoryViewHolder.OnMyCategoryItemClickListener ,MyPostViewHolder.OnMyItemClickListener{

    MyPageData myPageData = new MyPageData();
    boolean categoryFlag = true;
    String time = "";
    int timePosition = -1;

    int tabPosition;
    boolean comFlag;

    public void setFlag(boolean comFlag){
        this.comFlag = comFlag;
    }

    public void addMyData(MyData myData){
        myPageData.setMyData(myData);
        notifyDataSetChanged();
    }

    public void setTime(String time, int timePosition){
        this.time = time;
        this.timePosition = timePosition;
        notifyDataSetChanged();
    }

    public void addMyData(MyData[] myData){
        myPageData.setMyData(myData[0]);
        notifyDataSetChanged();
    }

    public void addAllPost(Post[] posts){
        myPageData.getPosts().addAll(Arrays.asList(posts));
        notifyDataSetChanged();
    }

    public void addPost(Post post){
        myPageData.getPosts().add(post);
        notifyDataSetChanged();
    }

    public void clearPost(){
        myPageData.getPosts().clear();
        notifyDataSetChanged();
    }

    public static final int VIEW_TYPE_HEADER = 1;
    public static final int VIEW_TYPE_TAP = 2;
    public static final int VIEW_TYPE_CTEGORY = 3;
    public static final int VIEW_TYPE_POST = 4;

    @Override
    public int getItemViewType(int position) {
        if (myPageData.getMyData() != null){
            if (position == 0) return VIEW_TYPE_HEADER;
            position--;
        }
        if(position == 0) return VIEW_TYPE_TAP;
        position--;
        if(categoryFlag){
            if(position == 0) return VIEW_TYPE_CTEGORY;
            position--;
        }
        if (myPageData.getPosts().size() > 0) {
            if (position < myPageData.getPosts().size()) {
                return VIEW_TYPE_POST;
            }
            position -= myPageData.getPosts().size();
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
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_tab, parent, false);
                return new MyTabViewHolder(view);
            }
            case VIEW_TYPE_CTEGORY :{
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_my_category, parent, false);
                return new MyCategoryViewHolder(view);
            }
            case VIEW_TYPE_POST :{
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_post, parent, false);
                return new MyPostViewHolder(view);
            }
        }
        throw new IllegalArgumentException("invalid viewtype");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int realPosition = position;
        if (myPageData.getMyData() != null) {
            if (position == 0) {
                MyHeaderViewHolder mhvh = (MyHeaderViewHolder) holder;
                mhvh.setOnMyDataItemClickListener(this);
                mhvh.setUserInof(myPageData.getMyData());
                return;
            }
            position--;
        }
        if(position == 0) {
            MyTabViewHolder mtvh = (MyTabViewHolder)holder;
            mtvh.setOnTabClickListener(this);
//            mtvh.setTabPosition(tabPosition);
            return;
        }
        position--;
        if(categoryFlag) {
            if (position == 0) {
                MyCategoryViewHolder mcvh = (MyCategoryViewHolder) holder;
                mcvh.setOnMyCategoryItemClickListener(this);
//                mcvh.setCategory(comFlag);
                return;
            }
            position--;
        }

        if (myPageData.getPosts().size() > 0) {
            if (position < myPageData.getPosts().size()) {
                MyPostViewHolder mpvh = (MyPostViewHolder)holder;
                mpvh.setOnMyItemClickListener(this);
                mpvh.setPost(myPageData.getPosts().get(position));
                mpvh.setComFlag(comFlag, tabPosition);
                if(realPosition == timePosition)  mpvh.setTime(time);
                return;
            }
            position -= myPageData.getPosts().size();
        }
        throw new IllegalArgumentException("invalid position");
    }

    @Override
    public int getItemCount() {
        int ctn = 2;
        if(myPageData == null) return 0;
        if(myPageData.getMyData() == null) ctn--;
        if(categoryFlag) {
            ctn++;
        }
        return myPageData.getPosts().size()+ctn;
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
    public void onModifyClick(View view, MyData myData, int position) {
        if(listener != null){
            listener.onAdatperModyfiyClick(view,myData,position);
        }
    }

    @Override
    public void onPhotoClick(View view, MyData myData, int position) {
        if(listener != null){
            listener.onAdapterPhotoClick(view, myData, position);
        }
    }


    @Override
    public void onCategory(View view, boolean flag) {
        categoryFlag = flag;
    }

    @Override
    public void onTabType(View view, int num) {
        if(listener != null){
            listener.onAdapterTabType(view, num);
            tabPosition = num;
        }
    }

    @Override
    public void onCategoryItemClick(Boolean flag) {
        if(listener != null){
            listener.onAdapterCategory(flag);
            comFlag = flag;
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
            listener.onAdapterPlayItemClick(view, post, position);
        }
    }

    @Override
    public void onQuestionerClick(View view, Post post, int position) {
        if(listener != null){
            listener.onAdapterQuestionerClick(view, post, position);
        }
    }

    @Override
    public void onAnswerClick(View view, Post post, int position) {
        if(listener != null){
            listener.onAdapterAnswerClick(view, post, position);
        }
    }


    public interface OnAdapterItemClickLIstener {
        public void onAdapterDonateClick(View view, MyData myData, int position);
        public void onAdapterFollowingClick(View view, MyData myData, int position);
        public void onAdapterFollowerClick(View view, MyData myData, int position);
        public void onAdapterSoundClick(View view, MyData myData, int position);
        public void onAdapterPhotoClick(View view, MyData myData, int position);
        public void onAdatperModyfiyClick(View view, MyData myData, int position);
        public void onAdapterCategory(boolean flag);
        public void onAdapterTabType(View view, int num);
        public void onAdapterItemClick(View view, Post post, int position);
        public void onAdapterPlayItemClick(View view, Post post, int position);
        public void onAdapterQuestionerClick(View view, Post post, int position);
        public void onAdapterAnswerClick(View view, Post post, int position);
    }

    OnAdapterItemClickLIstener listener;
    public void setOnAdapterItemClickListener(OnAdapterItemClickLIstener listener) {
        this.listener = listener;
    }

    public void setAdatperPosition(int tabPosition, boolean isCom){
        this.tabPosition = tabPosition;
        this.comFlag = isCom;
    }
}
