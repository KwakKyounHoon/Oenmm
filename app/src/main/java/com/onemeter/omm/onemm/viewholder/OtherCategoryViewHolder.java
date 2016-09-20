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
public class OtherCategoryViewHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.radiogroup)
    RadioGroup radioGroup;
    @BindView(R.id.btn_listen)
    RadioButton listenButton;
    @BindView(R.id.btn_new)
    RadioButton costButton;
    @BindView(R.id.text_title)
    TextView categoryView;

    public boolean isForced = false;

    public OtherCategoryViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (!isForced) {
                    switch (i) {
                        case R.id.btn_listen:
                            if (listener != null) {
                                listener.onCategoryItemClick(true);
                            }
                            break;
                        case R.id.btn_new:
                            if (listener != null) {
                                listener.onCategoryItemClick(false);
                            }
                            break;
                    }
                }
            }
        });
    }

    public void setCategory(boolean comFlag) {
        isForced = true;
        if(comFlag){
            listenButton.setChecked(true);
        }else{
            costButton.setChecked(true);
        }
        isForced = false;
    }

    public interface OnOtherCategoryItemClickListener {
        public void onCategoryItemClick(Boolean flag);
    }

    OnOtherCategoryItemClickListener listener;

    public void setOnOtherCategoryItemClickListener(OnOtherCategoryItemClickListener listener) {
        this.listener = listener;
    }

    public void setCategoryText(String str){
        categoryView.setText(str);
    }

}
