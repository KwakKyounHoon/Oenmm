package com.onemeter.omm.onemm.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.Post;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Kwak on 2016-08-28.
 */
public class PostViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.image_questioner)
    ImageView questionerImgView;
    @BindView(R.id.image_answer)
    ImageView answernerImgView;
    @BindView(R.id.text_question)
    TextView textQuestionView;
    @BindView(R.id.text_play_time)
    TextView playTimeView;
    @BindView(R.id.text_listen)
    TextView listenView;
    @BindView(R.id.text_cost)
    TextView costView;

    public PostViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
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
                .into(questionerImgView);
        Glide.with(answernerImgView.getContext())
                .load(post.getAnswernerPhoto())
                .into(answernerImgView);
    }

    @OnClick(R.id.btn_listen)
    public void listenClick(View view){
        if(listener != null){
            listener.onPlayClick(view, post, getAdapterPosition());
        }
    }


    public interface OnOtherItemClickListener {
        public void onPostItemClick(View view, Post post, int position);
        public void onPlayClick(View view, Post post, int position);
    }

    OnOtherItemClickListener listener;

    public void setOnOtherItemClickListener(OnOtherItemClickListener listener) {
        this.listener = listener;
    }
}
