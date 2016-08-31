package com.onemeter.omm.onemm.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.SettingDonate;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tacademy on 2016-08-30.
 */
public class SettingDonateViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.text_donate_total)
    TextView donateTotalView;
    @BindView(R.id.text_donate_monthly)
    TextView donateMonthlyView;
    public SettingDonateViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    SettingDonate settingDonate;
    public void setSettingDonate(SettingDonate settingDonate){
        this.settingDonate = settingDonate;
        donateMonthlyView.setText(settingDonate.getMonthlyDonation());
        donateTotalView.setText(settingDonate.getTotalDonation());
    }
}
