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
public class SettingSaveViewHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.text_current_point)
    TextView pointView;
    public SettingSaveViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setPoin(SettingSave settingSave){
        pointView.setText(settingSave.getCurrentPoint());
    }
}
