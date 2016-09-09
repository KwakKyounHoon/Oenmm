package com.onemeter.omm.onemm.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.SettingSave;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Tacademy on 2016-08-30.
 */
public class SettingSaveViewHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.text_current_point)
    TextView pointView;
    @BindView(R.id.image_expand)
    ImageView expandView;
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

    public SettingSaveViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setPoin(SettingSave settingSave){
        pointView.setText(settingSave.getCurrentPoint());
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

    @OnClick(R.id.image_expand)
    public void expandClick(View view){

    }
}
