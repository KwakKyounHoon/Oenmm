package com.onemeter.omm.onemm.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.onemeter.omm.onemm.MyApplication;
import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.DonatingPlace;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Tacademy on 2016-09-01.
 */
public class DonatingPlaceViewHolder extends RecyclerView.ViewHolder implements Checkable {

    @BindView(R.id.image_item)
    ImageView itemView;
    @BindView(R.id.text_name)
    TextView nameView;
    @BindView(R.id.image_blur)
    ImageView blurView;
    public DonatingPlaceViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @OnClick(R.id.image_item)
    public void itemClick(View view){
        if(listener != null){
            listener.onPlaceClick(view, donatingPlace, getAdapterPosition());
        }
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

    public interface OnPlaceItemClickListener {
        public void onPlaceClick(View view, DonatingPlace donatingPlace, int position);
    }

    OnPlaceItemClickListener listener;

    public void setPlaceClickListener(OnPlaceItemClickListener listener) {
        this.listener = listener;
    }

    boolean isChecked;

    @Override
    public void setChecked(boolean checked) {
        if (isChecked != checked) {
            isChecked = checked;
            drawCheck();
        }
    }

    private void drawCheck() {
        if (isChecked) {
            blurView.setVisibility(View.VISIBLE);
        } else {
            blurView.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {
        setChecked(!isChecked);
    }
}
