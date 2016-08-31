package com.onemeter.omm.onemm.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
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
    @BindView(R.id.text_questioner_name)
    TextView nameView;
    @BindView(R.id.text_message)
    TextView messageView;
    @BindView(R.id.text_donate)
    TextView donateView;
    @BindView(R.id.text_follower)
    TextView followerView;
    @BindView(R.id.text_following)
    TextView followingView;
    @BindView(R.id.image_profile)
    ImageView profileView;

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

    @OnClick(R.id.btn_sound)
    public void onSoundClick(View view){
        if (listener != null) {
            listener.onSoundItemClick(view, mydata, getAdapterPosition());
        }
    }

    @OnClick(R.id.image_profile)
    public void onPhotoClick(View view){
        if (listener != null) {
            listener.onPhotoClick(view, mydata, getAdapterPosition());
        }
    }

    @OnClick(R.id.btn_modify)
    public void modifyClick(View view){
        if(listener != null){
            listener.onModifyClick(view, mydata, getAdapterPosition());
        }
    }


    MyData mydata;
    public void setUserInof(MyData myData){
        this.mydata = myData;
        nameView.setText(myData.getName());
        followerView.setText(myData.getFollower());
        followingView.setText(myData.getFollowing());
        messageView.setText(myData.getStateMessage());
        donateView.setText(myData.getDonationName());
//        Glide.with(profileView.getContext())
//                .load(myData.getPhoto())
//                .into(profileView);
    }

    public interface OnMyDataItemClickListener {
        public void onDonateItemClick(View view, MyData myData, int position);
        public void onFollowingItemClick(View view, MyData myData, int position);
        public void onFollowerItemClick(View view, MyData myData, int position);
        public void onSoundItemClick(View view, MyData myData, int position);
        public void onModifyClick(View view, MyData myData, int position);
        public void onPhotoClick(View view, MyData myData, int position);
    }

    OnMyDataItemClickListener listener;

    public void setOnMyDataItemClickListener(OnMyDataItemClickListener listener) {
        this.listener = listener;
    }

}
