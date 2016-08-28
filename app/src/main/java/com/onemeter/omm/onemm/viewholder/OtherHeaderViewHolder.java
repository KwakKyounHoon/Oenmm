package com.onemeter.omm.onemm.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.OtherInfo;

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
                    listener.onFollowerItemClick(view, otherInfo, getAdapterPosition());
                }
            }
        });

        followingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onFollowingItemClick(view, otherInfo, getAdapterPosition());
                }
            }
        });
    }

    @OnClick(R.id.btn_like)
    public void onSoundClick(View view){
        if (listener != null) {
            listener.onSoundItemClick(view, otherInfo, getAdapterPosition());
        }
    }

    OtherInfo otherInfo;
    public void setOtherInof(OtherInfo otherInfo){
        this.otherInfo = otherInfo;
        nameView.setText(otherInfo.getName());
        followerView.setText(otherInfo.getFollower());
        followingView.setText(otherInfo.getFollowing());
        messageView.setText(otherInfo.getStateMessage());
        donateView.setText(otherInfo.getDonationName());
    }

    public interface OnOtherDataItemClickListener {
        public void onFollowingItemClick(View view, OtherInfo otherInfo, int position);
        public void onFollowerItemClick(View view, OtherInfo otherInfo, int position);
        public void onSoundItemClick(View view, OtherInfo otherInfo, int position);
    }

    OnOtherDataItemClickListener listener;

    public void setOnOtherDataItemClickListener(OnOtherDataItemClickListener listener) {
        this.listener = listener;
    }
}
