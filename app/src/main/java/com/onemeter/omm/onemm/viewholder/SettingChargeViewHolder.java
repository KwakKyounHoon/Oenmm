package com.onemeter.omm.onemm.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.onemeter.omm.onemm.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
 * Created by Tacademy on 2016-09-09.
 */
public class SettingChargeViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.text_setting_charge)
    TextView chargeView;

    public SettingChargeViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null)
                    listener.onSettingChargeClick(view);
            }
        });
    }

    public void setTextCharge(String text){
        chargeView.setText(text);
    }

    public interface OnSettingChargeClickListener {
        public void onSettingChargeClick(View view);
    }

    OnSettingChargeClickListener listener;

    public void setOnRankCategoryItemClickListener(OnSettingChargeClickListener listener) {
        this.listener = listener;
    }
}

