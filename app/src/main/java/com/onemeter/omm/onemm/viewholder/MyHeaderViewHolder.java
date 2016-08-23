package com.onemeter.omm.onemm.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.onemeter.omm.onemm.data.MyData;

import butterknife.ButterKnife;

/**
 * Created by Tacademy on 2016-08-23.
 */
public class MyHeaderViewHolder extends RecyclerView.ViewHolder {

    public MyHeaderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    MyData mydata;
    public void setUserInof(MyData myData){
        this.mydata = myData;
    }
}
