package com.onemeter.omm.onemm.viewholder;

import android.support.v4.content.ContextCompat;
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
 * Created by Kwak on 2016-08-28.
 */
public class PostViewHolder extends RecyclerView.ViewHolder {
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
    @BindView(R.id.btn_listen)
    TextView listenbtnView;

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
                .bitmapTransform(new CropCircleTransformation(MyApplication.getContext()))
                .error(R.drawable.ic_profile_image_default)
                .into(questionerImgView);
        Glide.with(answernerImgView.getContext())
                .load(post.getAnswernerPhoto())
                .bitmapTransform(new CropCircleTransformation(MyApplication.getContext()))
                .error(R.drawable.ic_profile_image_default)
                .into(answernerImgView);
        if(post.getPayInfo().equals("1")){
            listenbtnView.setBackgroundResource(R.drawable.ic_answer_on);
            listenbtnView.setTextColor(ContextCompat.getColor(MyApplication.getContext(),R.color.colorWhite));
        }else{
            listenbtnView.setBackgroundResource(R.drawable.ic_answer_off);
            listenbtnView.setTextColor(ContextCompat.getColor(MyApplication.getContext(),R.color.colorBlack));
        }
        setTime("답변 듣기");
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
    public void setTime(String time){
        listenbtnView.setText(time);
    }
}
