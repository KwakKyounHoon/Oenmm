package com.onemeter.omm.onemm.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.onemeter.omm.onemm.MyApplication;
import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.Post;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Tacademy on 2016-08-24.
 */

public class MyPostViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.image_questioner)
    ImageView questionerImgView;
    @BindView(R.id.image_answerner)
    ImageView answernerImgView;
    @BindView(R.id.text_question)
    TextView textQuestionView;
    @BindView(R.id.text_play_time)
    TextView playTimeView;
    @BindView(R.id.text_listen)
    TextView listenView;
    @BindView(R.id.text_cost)
    TextView costView;
    @BindView(R.id.answer)
    RelativeLayout answerLayout;
    @BindView(R.id.layout_no_re)
    RelativeLayout noRipleLayout;
    @BindView(R.id.text_no_cost)
    TextView noCostView;

    public MyPostViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.onPostItemClick(view, post, getAdapterPosition());
                }
            }
        });
    }

    boolean comFlag;

    public void setComFlag(boolean flag, int tabPosition){
        if (flag){
            answerLayout.setVisibility(View.VISIBLE);
            noRipleLayout.setVisibility(View.GONE);
        }else{
            answerLayout.setVisibility(View.GONE);
            noRipleLayout.setVisibility(View.VISIBLE);
        }

        if(tabPosition == 3){
            answerLayout.setVisibility(View.VISIBLE);
            noRipleLayout.setVisibility(View.GONE);
        }
    }

    Post post;
    public void setPost(Post post){
        this.post = post;
        textQuestionView.setText(post.getQuestionerContent());
        playTimeView.setText(post.getLength());
        listenView.setText(post.getListenCount());
        costView.setText(post.getPrice());
        noCostView.setText(post.getPrice());
        Glide.with(questionerImgView.getContext())
                .load(post.getQuestionerPhoto())
                .bitmapTransform(new CropCircleTransformation(MyApplication.getContext()))
                .error(R.drawable.ic_profile_image_default)
                .into(questionerImgView);
        Glide.with(answernerImgView.getContext())
                .load(post.getAnswernerPhoto())
                .bitmapTransform(new CropCircleTransformation(MyApplication.getContext()))
                .error(R.drawable.ic_profile_image_default)
                .into(answernerImgView);
    }

    @OnClick(R.id.btn_listen)
    public void listenClick(View view){
        if(listener != null){
            listener.onPlayClick(view, post, getAdapterPosition());
        }
    }

    @OnClick(R.id.image_questioner)
    public void questionerClick(View view){
        if(listener != null){
            listener.onQuestionerClick(view, post, getAdapterPosition());
        }
    }

    @OnClick(R.id.image_answerner)
    public void answernerClick(View view){
        if(listener != null){
            listener.onAnswerClick(view, post, getAdapterPosition());
        }
    }

    public interface OnMyItemClickListener {
        public void onPostItemClick(View view, Post post, int position);
        public void onPlayClick(View view, Post post, int position);
        public void onQuestionerClick(View view, Post post, int position);
        public void onAnswerClick(View view, Post post, int position);
    }

    OnMyItemClickListener listener;

    public void setOnMyItemClickListener(OnMyItemClickListener listener) {
        this.listener = listener;
    }
}
