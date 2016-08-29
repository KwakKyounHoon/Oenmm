package com.onemeter.omm.onemm.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.Follower;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tacademy on 2016-08-29.
 */
public class FollowerViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.text_name)
    TextView nameView;
    @BindView(R.id.text_nickname)
    TextView nicknameView;
    @BindView(R.id.image_profile)
    ImageView profileView;
    @BindView(R.id.check_follow)
    CheckBox followView;

    public FollowerViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.onItemClick(view, follower, getAdapterPosition());
                }
            }
        });

        followView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(listener != null){
                    listener.onlikeClick(itemView, follower, getAdapterPosition());
                }
            }
        });
    }


    Follower follower;
    public void setFollower(Follower follower) {
        this.follower = follower;
        nameView.setText(follower.getName());
        nicknameView.setText(follower.getNickname());
        Glide.with(profileView.getContext())
                .load(follower.getPhoto())
                .into(profileView);
    }

    public interface OnFollowerItemClickListener {
        public void onItemClick(View view, Follower follower, int position);
        public void onlikeClick(View view, Follower follower, int position);
    }

    OnFollowerItemClickListener listener;

    public void setOnFollowerClickListener(OnFollowerItemClickListener listener) {
        this.listener = listener;
    }
}
