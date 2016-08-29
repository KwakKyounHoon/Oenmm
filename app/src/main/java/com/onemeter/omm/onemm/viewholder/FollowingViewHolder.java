package com.onemeter.omm.onemm.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.Following;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Tacademy on 2016-08-29.
 */
public class FollowingViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.text_name)
    TextView nameView;
    @BindView(R.id.text_nickname)
    TextView nicknameView;
    @BindView(R.id.image_profile)
    ImageView profileView;
    @BindView(R.id.text_distance)
    TextView distanceView;

    public FollowingViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.onItemClick(view, following, getAdapterPosition());
                }
            }
        });
    }


    Following following;
    public void setFollowing(Following following) {
        this.following = following;
        nameView.setText(following.getName());
        nicknameView.setText(following.getName());
        distanceView.setText(following.getDistance());
        Glide.with(profileView.getContext())
                .load(following.getPhoto())
                .into(profileView);
    }

    @OnClick(R.id.image_arrow)
    public void arrowClick(View view){
        if(listener != null){
            listener.onArrowClick(view, following, getAdapterPosition());
        }
    }

    public interface OnFollowingItemClickListener {
        public void onItemClick(View view, Following following, int position);
        public void onArrowClick(View view, Following following, int position);
    }

    OnFollowingItemClickListener listener;

    public void setOnFollowingClickListener(OnFollowingItemClickListener listener) {
        this.listener = listener;
    }
}
