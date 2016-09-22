package com.onemeter.omm.onemm.viewholder;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.onemeter.omm.onemm.MyApplication;
import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.OtherData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class OtherHeaderViewHolder extends RecyclerView.ViewHolder {

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
    @BindView(R.id.image_like)
    CheckBox likeView;
    @BindView(R.id.btn_sound)
    ImageView soundView;
    public OtherHeaderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        likeView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!isForced) {
                    if (listener != null) {
                        listener.onFollowClick(compoundButton, b);
                        Log.i("test","3");
                    }
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

    @OnClick(R.id.btn_sound)
    public void voiceClick(View view){
        if(listener != null){
            listener.onSoundItemClick(view, otherData, getAdapterPosition());
        }
    }

    @OnClick(R.id.btn_question)
    public void onQuestionClick(View view){
        if(listener != null){
            listener.onQuestionClick(view, otherData);
        }
    }
    boolean headerPlayInfo;
    OtherData otherData;
    public void setOtherInof(OtherData otherData, boolean headerPlayInfo){
        this.headerPlayInfo = headerPlayInfo;
        this.otherData = otherData;
        nameView.setText(otherData.getName());
        followerView.setText(otherData.getFollower());
        followingView.setText(otherData.getFollowing());
        messageView.setText(otherData.getStateMessage());
        if(otherData.getDonationName() != null || otherData.getDonationName() != ""){
            donateView.setText(otherData.getDonationName());
        }else{
            donateView.setText("없 음");
        }

        Glide.with(profileView.getContext())
                .load(otherData.getPhoto())
                .bitmapTransform(new CropCircleTransformation(MyApplication.getContext()))
                .error(R.drawable.ic_profile_image_default)
                .into(profileView);
        if(otherData.getFollowInfo().equals("1")){
            isForced = true;
            likeView.setChecked(true);
            isForced = false;
        }else{
            isForced = true;
            likeView.setChecked(false);
            isForced = false;
        }
        if(headerPlayInfo){
            soundView.setImageDrawable(ContextCompat.getDrawable(MyApplication.getContext(), R.drawable.ic_speaker_on));
        }else{
            soundView.setImageDrawable(ContextCompat.getDrawable(MyApplication.getContext(), R.drawable.ic_speaker));
        }
    }

    public boolean isForced = false;

    public interface OnOtherDataItemClickListener {
        public void onFollowingItemClick(View view, OtherData otherData, int position);
        public void onFollowerItemClick(View view, OtherData otherData, int position);
        public void onSoundItemClick(View view, OtherData otherData, int position);
        public void onFollowClick(View view, boolean flag);
        public void onQuestionClick(View view, OtherData otherData);
    }

    OnOtherDataItemClickListener listener;

    public void setOnOtherDataItemClickListener(OnOtherDataItemClickListener listener) {
        this.listener = listener;
    }
}
