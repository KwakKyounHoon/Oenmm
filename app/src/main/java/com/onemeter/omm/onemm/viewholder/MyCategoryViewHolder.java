package com.onemeter.omm.onemm.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.onemeter.omm.onemm.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class MyCategoryViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.radiogroup)
    RadioGroup radioGroup;
    @BindView(R.id.btn_com)
    RadioButton comButton;
    @BindView(R.id.btn_incom)
    RadioButton inComButton;

    public MyCategoryViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.btn_com:
                        if (listener != null) {
                            listener.onCategoryItemClick(true);
                        }
                        break;
                    case R.id.btn_incom:
                        if (listener != null) {
                            listener.onCategoryItemClick(false);
                        }
                        break;
                }
            }
        });
    }

    public interface OnMyCategoryItemClickListener {
        public void onCategoryItemClick(Boolean flag);
    }

    OnMyCategoryItemClickListener listener;

    public void setOnMyCategoryItemClickListener(OnMyCategoryItemClickListener listener) {
        this.listener = listener;
    }

    public void setCategory(boolean isCom){
        if(isCom){
            comButton.setChecked(true);
        }else{
            inComButton.setChecked(true);
        }
    }
}
