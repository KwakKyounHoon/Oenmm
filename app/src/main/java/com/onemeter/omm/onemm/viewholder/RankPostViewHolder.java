package com.onemeter.omm.onemm.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.RankPopular;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Tacademy on 2016-08-26.
 */
public class RankPostViewHolder extends RecyclerView.ViewHolder {
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
    public RankPostViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.onPostItemClick(view, rankPopular, getAdapterPosition());
                }
            }
        });
    }

    RankPopular rankPopular;
    public void setRankPopular(RankPopular rankPopular){
        this.rankPopular = rankPopular;
        textQuestionView.setText(rankPopular.getQuestionerContent());
        playTimeView.setText(rankPopular.getLength());
        listenView.setText(rankPopular.getListenCount());
        costView.setText(rankPopular.getPrice());
        Glide.with(questionerImgView.getContext())
                .load(rankPopular.getQuestionerPhoto())
                .into(questionerImgView);
        Glide.with(answernerImgView.getContext())
                .load(rankPopular.getAnswernerPhoto())
                .into(answernerImgView);
    }

    @OnClick(R.id.btn_listen)
    public void listenClick(View view){
        if(listener != null){
            listener.onPlayClick(view, rankPopular, getAdapterPosition());
        }
    }

    public interface OnRankPopularItemClickListener {
        public void onPostItemClick(View view, RankPopular rankPopular, int position);
        public void onPlayClick(View view, RankPopular rankPopular, int position);
    }

    OnRankPopularItemClickListener listener;

    public void setOnRankPopularItemClickListener(OnRankPopularItemClickListener listener) {
        this.listener = listener;
    }

}
