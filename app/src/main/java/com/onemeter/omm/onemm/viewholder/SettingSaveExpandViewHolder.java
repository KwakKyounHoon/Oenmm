package com.onemeter.omm.onemm.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.SettingSave;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tacademy on 2016-08-30.
 */
public class SettingSaveExpandViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.text_pay_total)
    TextView payTotlaView;
    @BindView(R.id.text_listening_profit)
    TextView listeningProfitView;
    @BindView(R.id.text_listening_cost)
    TextView listeningCostView;
    @BindView(R.id.text_question_cost)
    TextView questionCostView;
    @BindView(R.id.text_withdraw_total)
    TextView withdrawTotalView;
    public SettingSaveExpandViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    SettingSave settingSave;
    public void setSettingSave(SettingSave settingSave){
        this.settingSave = settingSave;
        payTotlaView.setText(settingSave.getPayTotal());
        listeningCostView.setText(settingSave.getListeningCost());
        listeningProfitView.setText(settingSave.getListeningProfit());
        questionCostView.setText(settingSave.getQuestionCost());
        withdrawTotalView.setText(settingSave.getWithdrawTotal());
    }
}
