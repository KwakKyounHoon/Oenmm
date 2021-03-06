package com.onemeter.omm.onemm.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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
    @BindView(R.id.text_category)
    TextView categroyView;

    public boolean isForced = false;

    public MyCategoryViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (!isForced) {
                    switch (i) {
                        case R.id.btn_com:
                            if (listener != null) {
                                listener.onCategoryItemClick(true, getAdapterPosition());
                            }
                            break;
                        case R.id.btn_incom:
                            if (listener != null) {
                                listener.onCategoryItemClick(false, getAdapterPosition());
                            }
                            break;
                    }
                }
            }
        });
    }

    public interface OnMyCategoryItemClickListener {
        public void onCategoryItemClick(Boolean flag, int position);
    }

    OnMyCategoryItemClickListener listener;

    public void setOnMyCategoryItemClickListener(OnMyCategoryItemClickListener listener) {
        this.listener = listener;
    }

    public void setCategory(boolean isCom){
        isForced = true;
        if(isCom){
            comButton.setChecked(true);
        }else{
            inComButton.setChecked(true);
        }
        isForced = false;
    }

    public void setCategroyText(String str){
        categroyView.setText(str);
    }

    public void setCategoryView(boolean flag){
        if(flag){
            radioGroup.setVisibility(View.VISIBLE);
        }else{
            radioGroup.setVisibility(View.GONE);
        }
    }
}
