package com.onemeter.omm.onemm.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
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

    @BindView(R.id.text_name)
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

    public OtherHeaderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

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

    @OnClick(R.id.btn_like)
    public void onSoundClick(View view){
        if (listener != null) {
            listener.onSoundItemClick(view, otherData, getAdapterPosition());
        }
    }

    OtherData otherData;
    public void setOtherInof(OtherData otherData){
        this.otherData = otherData;
        nameView.setText(otherData.getName());
        followerView.setText(otherData.getFollower());
        followingView.setText(otherData.getFollowing());
        messageView.setText(otherData.getStateMessage());
        donateView.setText(otherData.getDonationName());
    }

    public interface OnOtherDataItemClickListener {
        public void onFollowingItemClick(View view, OtherData otherData, int position);
        public void onFollowerItemClick(View view, OtherData otherData, int position);
        public void onSoundItemClick(View view, OtherData otherData, int position);
    }

    OnOtherDataItemClickListener listener;

    public void setOnOtherDataItemClickListener(OnOtherDataItemClickListener listener) {
        this.listener = listener;
    }
}
