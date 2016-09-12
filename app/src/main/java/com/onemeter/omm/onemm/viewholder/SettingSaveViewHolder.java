package com.onemeter.omm.onemm.viewholder;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.onemeter.omm.onemm.MyApplication;
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
    @BindView(R.id.layout_save_child)
    RelativeLayout childView;

    boolean expandFlag = false;

    public SettingSaveViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.onExpandClick(view, getAdapterPosition(), expandFlag);
                }
                if(expandFlag){
                    childView.setVisibility(View.VISIBLE);
                    expandView.setImageDrawable(ContextCompat.getDrawable(MyApplication.getContext(), R.drawable.ic_setting_fold));
                    expandFlag = !expandFlag;
                }else{
                    childView.setVisibility(View.GONE);
                    expandView.setImageDrawable(ContextCompat.getDrawable(MyApplication.getContext(), R.drawable.ic_setting_spread));
                    expandFlag = !expandFlag;
                }
            }
        });
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

    @OnClick(R.id.btn_withdraw)
    public void withdrawClick(View view){
        if(listener != null){
            listener.onWithdrawClick(view, settingSave, getAdapterPosition());
        }
    }

    public interface OnSettingSaveItemClickListener {
        public void onExpandClick(View view, int position, boolean expandFlag);
        public void onWithdrawClick(View view,SettingSave settingSave, int position);
    }

    OnSettingSaveItemClickListener listener;

    public void setSettingSaveItemClickListener(OnSettingSaveItemClickListener listener) {
        this.listener = listener;
    }
}
