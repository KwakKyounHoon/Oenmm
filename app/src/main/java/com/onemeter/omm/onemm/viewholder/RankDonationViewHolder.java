package com.onemeter.omm.onemm.viewholder;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.onemeter.omm.onemm.MyApplication;
import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.DonationRank;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Tacademy on 2016-08-26.
 */
public class RankDonationViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.text_user_name)
    TextView userNameView;
    @BindView(R.id.text_plcae_name)
    TextView placeNameView;
    @BindView(R.id.image_profile)
    ImageView profileView;
    @BindView(R.id.image_place)
    ImageView plcaeView;
    @BindView(R.id.image_rank)
    ImageView rankView;

    public RankDonationViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.onPostItemClick(view, donationRank, getAdapterPosition());
                }
            }
        });
    }

    DonationRank donationRank;
    public void setDonationRank(DonationRank donationRank){
        this.donationRank = donationRank;
        userNameView.setText(donationRank.getUserName());
        placeNameView.setText(donationRank.getDonationName());
        Glide.with(plcaeView.getContext())
                .load(donationRank.getDonationPhoto())
                .bitmapTransform(new CropCircleTransformation(MyApplication.getContext()))
                .error(R.drawable.ic_profile_image_default)
                .into(plcaeView);
        Glide.with(profileView.getContext())
                .load(donationRank.getUserPhoto())
                .bitmapTransform(new CropCircleTransformation(MyApplication.getContext()))
                .error(R.drawable.ic_profile_image_default)
                .into(profileView);
        switch (getAdapterPosition()){
            case 0:
                rankView.setImageDrawable(ContextCompat.getDrawable(MyApplication.getContext(),R.drawable.ic_one));
                break;
            case 1:
                rankView.setImageDrawable(ContextCompat.getDrawable(MyApplication.getContext(),R.drawable.ic_two));
                break;
            case 2:
                rankView.setImageDrawable(ContextCompat.getDrawable(MyApplication.getContext(),R.drawable.ic_three));
                break;
            case 3:
                rankView.setImageDrawable(ContextCompat.getDrawable(MyApplication.getContext(),R.drawable.ic_four));
                break;
            case 4:
                rankView.setImageDrawable(ContextCompat.getDrawable(MyApplication.getContext(),R.drawable.ic_five));
                break;
        }
    }

    public interface OnRankDonationItemClickListener {
        public void onPostItemClick(View view, DonationRank donationRank, int position);
    }

    OnRankDonationItemClickListener listener;

    public void setOnRankDonationItemClickListener(OnRankDonationItemClickListener listener) {
        this.listener = listener;
    }
}
