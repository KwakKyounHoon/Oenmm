package com.onemeter.omm.onemm.viewholder;

import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.onemeter.omm.onemm.MyApplication;
import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.MyData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

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
    @BindView(R.id.btn_sound)
    ImageView soundView;

    MediaPlayer player;

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

    @OnClick(R.id.btn_question)
    public void modifyClick(View view){
        if(listener != null){
            listener.onModifyClick(view, mydata, getAdapterPosition());
        }
    }


    MyData mydata;
    boolean headerPlayInfo = false;
    public void setUserInof(MyData myData, boolean headerPlayInfo){
        this.mydata = myData;
        this.headerPlayInfo = headerPlayInfo;
        nameView.setText(myData.getName());
        followerView.setText(myData.getFollower());
        followingView.setText(myData.getFollowing());
        messageView.setText(myData.getStateMessage());
        if(myData.getDonationName() != null || myData.getDonationName() != ""){
            donateView.setText(myData.getDonationName());
        }else{
            donateView.setText("없 음");
        }

        Glide.with(profileView.getContext())
                .load(myData.getPhoto())
                .bitmapTransform(new CropCircleTransformation(MyApplication.getContext()))
                .error(R.drawable.ic_profile_image_default)
                .into(profileView);
        if(headerPlayInfo){
            soundView.setImageDrawable(ContextCompat.getDrawable(MyApplication.getContext(), R.drawable.ic_speaker_on));
        }else{
            soundView.setImageDrawable(ContextCompat.getDrawable(MyApplication.getContext(), R.drawable.ic_speaker));
        }
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

    private void killMediaPlayer() {
        if(player != null){
            try {
                player.release();
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    private void playAudio(String url) throws Exception{
        killMediaPlayer();
        player = new MediaPlayer();
        player.setDataSource(url);
        player.prepare();
        player.start();
    }

}
