package com.onemeter.omm.onemm.viewholder;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;

import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.manager.PropertyManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tacademy on 2016-08-30.
 */
public class SettingNotifyViewHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.answer_switch)
    SwitchCompat answerSwitch;
    @BindView(R.id.question_switch)
    SwitchCompat questionSwitch;
    @BindView(R.id.like_switch)
    SwitchCompat likeSwitch;

    public boolean isForced = false;

    public SettingNotifyViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        answerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!isForced) {
                    if (listener != null) {
                        listener.onAnswerSwitchClick(b, getAdapterPosition());
                    }
                }
            }
        });
        questionSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!isForced) {
                    if (listener != null) {
                        listener.onQuestionSwitchClick(b, getAdapterPosition());
                    }
                }
            }
        });
        likeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!isForced) {
                    if (listener != null) {
                        listener.onLikeSwitchClick(b, getAdapterPosition());
                    }
                }
            }
        });
    }

    public interface OnSettingNotifyClickListener {
        public void onAnswerSwitchClick(Boolean flag, int position);
        public void onQuestionSwitchClick(Boolean flag, int position);
        public void onLikeSwitchClick(Boolean flag, int position);
    }

    OnSettingNotifyClickListener listener;

    public void setSettingNotifyListener(OnSettingNotifyClickListener listener) {
        this.listener = listener;
    }

    public void setCategory() {
        isForced = true;
        if(PropertyManager.getInstance().getAnswerSwitch().equals("0")){
            answerSwitch.setChecked(false);
        }else{
            answerSwitch.setChecked(true);
        }
        if(PropertyManager.getInstance().getQuestionSwitch().equals("0")){
            questionSwitch.setChecked(false);
        }else{
            questionSwitch.setChecked(true);
        }
        if(PropertyManager.getInstance().getLikeSwitch().equals("0")){
            likeSwitch.setChecked(false);
        }else{
            likeSwitch.setChecked(true);
        }
        isForced = false;
    }
}
