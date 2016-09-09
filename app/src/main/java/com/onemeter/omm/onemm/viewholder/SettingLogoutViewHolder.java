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
public class SettingLogoutViewHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.text_setting_category)
    TextView categoryView;

    public SettingLogoutViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.onSettingLogoutClick(view);
                }
            }
        });
    }

    public void setCategory(String text){
        categoryView.setText(text);
    }

    public interface OnSettingLogoutClickListener {
        public void onSettingLogoutClick(View view);
    }

    OnSettingLogoutClickListener listener;

    public void setOnRankCategoryItemClickListener(OnSettingLogoutClickListener listener) {
        this.listener = listener;
    }
}
