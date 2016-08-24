package com.onemeter.omm.onemm.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.MyData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Tacademy on 2016-08-23.
 */
public class MyHeaderViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.text_donate)
    TextView donateView;

    @BindView(R.id.text_follower)
    TextView followerView;

    @BindView(R.id.text_following)
    TextView followingView;

    public MyHeaderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        donateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onDonateItemClick(view, mydata, getAdapterPosition());
                }
            }
        });

        followerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onFollowerItemClick(view, mydata, getAdapterPosition());
                }
            }
        });

        followingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onFollowingItemClick(view, mydata, getAdapterPosition());
                }
            }
        });
    }

    @OnClick(R.id.btn_photo)
    public void onPhotoClick(View view){
        if (listener != null) {
            listener.onPhotoItemClick(view, mydata, getAdapterPosition());
        }
    }

    @OnClick(R.id.btn_sound)
    public void onSoundClick(View view){
        if (listener != null) {
            listener.onSoundItemClick(view, mydata, getAdapterPosition());
        }
    }

    @OnClick(R.id.btn_profile)
    public void onProfileClick(View view){
        if (listener != null) {
            listener.onProfileItemClick(view, mydata, getAdapterPosition());
        }
    }


    MyData mydata;
    public void setUserInof(MyData myData){
        this.mydata = myData;
    }

//    public interface OnDonateItemClickListener {
//        public void onDonateItemClick(View view, MyData mydata, int position);
//    }
//
//    OnDonateItemClickListener listener;
//
//    public void setOnDonateItemClickListener(OnDonateItemClickListener listener) {
//        this.listener = listener;
//    }

    public interface OnMyDataItemClickListener {
        public void onDonateItemClick(View view, MyData myData, int position);
        public void onFollowingItemClick(View view, MyData myData, int position);
        public void onFollowerItemClick(View view, MyData myData, int position);
        public void onSoundItemClick(View view, MyData myData, int position);
        public void onPhotoItemClick(View view, MyData myData, int position);
        public void onProfileItemClick(View view, MyData myData, int position);
    }

    OnMyDataItemClickListener listener;

    public void setOnMyDataItemClickListener(OnMyDataItemClickListener listener) {
        this.listener = listener;
    }


}
