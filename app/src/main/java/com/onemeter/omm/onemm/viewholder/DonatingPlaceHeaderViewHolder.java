package com.onemeter.omm.onemm.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.DonatingPlace;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tacademy on 2016-09-01.
 */
public class DonatingPlaceHeaderViewHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.image_header)
    ImageView headerView;
    @BindView(R.id.text_header_title)
    TextView headerTitleView;
    @BindView(R.id.text_header_discription)
    TextView headerDiscriptionView;
    public DonatingPlaceHeaderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    DonatingPlace donatingPlace;
    public void setDPHeader(DonatingPlace donatingPlace) {
        this.donatingPlace = donatingPlace;
        headerTitleView.setText(donatingPlace.getName());
        headerDiscriptionView.setText(donatingPlace.getDescription());
//        Glide.with(headerView.getContext())
//                .load(donatingPlace.getPhoto())
//                .into(headerView);
    }
}
