package com.onemeter.omm.onemm.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.OtherData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class OtherHeaderViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.text_donate)
    TextView donateView;

    @BindView(R.id.text_follower)
    TextView followerView;

    @BindView(R.id.text_following)
    TextView followingView;

    public OtherHeaderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        donateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onDonateItemClick(view, otherData, getAdapterPosition());
                }
            }
        });

        followerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onFollowerItemClick(view, otherData, getAdapterPosition());
                }
            }
        });

        followingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onFollowingItemClick(view, otherData, getAdapterPosition());
                }
            }
        });
    }

    @OnClick(R.id.btn_photo)
    public void onPhotoClick(View view){
        if (listener != null) {
            listener.onPhotoItemClick(view, otherData, getAdapterPosition());
        }
    }

    @OnClick(R.id.btn_sound)
    public void onSoundClick(View view){
        if (listener != null) {
            listener.onSoundItemClick(view, otherData, getAdapterPosition());
        }
    }

    @OnClick(R.id.btn_profile)
    public void onProfileClick(View view){
        if (listener != null) {
            listener.onProfileItemClick(view, otherData, getAdapterPosition());
        }
    }


    OtherData otherData;
    public void setOtherInof(OtherData otherData){
        this.otherData = otherData;
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
        public void onDonateItemClick(View view, OtherData otherData, int position);
        public void onFollowingItemClick(View view, OtherData otherData, int position);
        public void onFollowerItemClick(View view, OtherData otherData, int position);
        public void onSoundItemClick(View view, OtherData otherData, int position);
        public void onPhotoItemClick(View view, OtherData otherData, int position);
        public void onProfileItemClick(View view, OtherData otherData, int position);
    }

    OnMyDataItemClickListener listener;

    public void setOnMyDataItemClickListener(OnMyDataItemClickListener listener) {
        this.listener = listener;
    }
}
