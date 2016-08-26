package com.onemeter.omm.onemm.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.onemeter.omm.onemm.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tacademy on 2016-08-26.
 */
public class RankCategoryViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.radiogroup)
    RadioGroup radioGroup;
    @BindView(R.id.btn_listen)
    RadioButton listenButton;
    @BindView(R.id.btn_cost)
    RadioButton costButton;

    public RankCategoryViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.btn_listen:
                        if (listener != null) {
                            listener.onCategoryItemClick(true);
                        }
                        break;
                    case R.id.btn_cost:
                        if (listener != null) {
                            listener.onCategoryItemClick(false);
                        }
                        break;
                }
            }
        });
    }

    public interface OnRankCategoryItemClickListener {
        public void onCategoryItemClick(Boolean flag);
    }

    OnRankCategoryItemClickListener listener;

    public void setOnRankCategoryItemClickListener(OnRankCategoryItemClickListener listener) {
        this.listener = listener;
    }

}
