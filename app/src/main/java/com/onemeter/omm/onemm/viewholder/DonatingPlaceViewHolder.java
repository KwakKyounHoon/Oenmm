package com.onemeter.omm.onemm.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.onemeter.omm.onemm.MyApplication;
import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.DonatingPlace;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Tacademy on 2016-09-01.
 */
public class DonatingPlaceViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.image_item)
    ImageView itemView;
    @BindView(R.id.text_name)
    TextView nameView;
    public DonatingPlaceViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    DonatingPlace donatingPlace;
    public void setDPItem(DonatingPlace donatingPlace) {
        this.donatingPlace = donatingPlace;
        nameView.setText(donatingPlace.getName());
        Glide.with(itemView.getContext())
                .load(donatingPlace.getPhoto())
                .bitmapTransform(new CropCircleTransformation(MyApplication.getContext()))
                .into(itemView);
    }
}
