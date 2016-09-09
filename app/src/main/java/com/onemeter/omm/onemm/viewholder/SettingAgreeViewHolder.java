package com.onemeter.omm.onemm.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.onemeter.omm.onemm.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tacademy on 2016-08-30.
 */

public class SettingAgreeViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.text_agree_text)
    TextView agreeView;
    public SettingAgreeViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.onSettingAgreeClick(view);
                }
            }
        });
    }
    public void setText(String text){
        agreeView.setText(text);
    }

    public interface OnSettingAgreeClickListener {
        public void onSettingAgreeClick(View view);
    }

    OnSettingAgreeClickListener listener;

    public void setOnRankCategoryItemClickListener(OnSettingAgreeClickListener listener) {
        this.listener = listener;
    }
}
