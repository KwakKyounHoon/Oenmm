package com.onemeter.omm.onemm.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
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
 * Created by Tacademy on 2016-08-26.
 */
public class RankPostViewHolder extends RecyclerView.ViewHolder {
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
    public RankPostViewHolder(View itemView) {
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

    Post post;
    public void setPost(Post post){
        this.post = post;
        textQuestionView.setText(post.getQuestionerContent());
        playTimeView.setText(post.getLength());
        listenView.setText(post.getListenCount());
        costView.setText(post.getPrice());
        Glide.with(questionerImgView.getContext())
                .load(post.getQuestionerPhoto())
                .bitmapTransform(new CropCircleTransformation(MyApplication.getContext()))
                .into(questionerImgView);
        Glide.with(answernerImgView.getContext())
                .load(post.getAnswernerPhoto())
                .bitmapTransform(new CropCircleTransformation(MyApplication.getContext()))
                .into(answernerImgView);
    }

    @OnClick(R.id.btn_listen)
    public void listenClick(View view){
        if(listener != null){
            listener.onPlayClick(view, post, getAdapterPosition());
        }
    }

    public interface OnRankPopularItemClickListener {
        public void onPostItemClick(View view, Post post, int position);
        public void onPlayClick(View view, Post post, int position);
    }

    OnRankPopularItemClickListener listener;

    public void setOnRankPopularItemClickListener(OnRankPopularItemClickListener listener) {
        this.listener = listener;
    }

}
