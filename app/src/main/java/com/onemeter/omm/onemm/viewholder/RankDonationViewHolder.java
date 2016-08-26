package com.onemeter.omm.onemm.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.DonationRank;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        userNameView.setText(donationRank.getName());
        placeNameView.setText(donationRank.getDonationName());
//        Glide.with(plcaeView.getContext())
//                .load(donationRank.getDonationPhoto())
//                .into(plcaeView);
//        Glide.with(profileView.getContext())
//                .load(donationRank.getPhoto())
//                .into(profileView);
    }

    public interface OnRankDonationItemClickListener {
        public void onPostItemClick(View view, DonationRank donationRank, int position);
    }

    OnRankDonationItemClickListener listener;

    public void setOnRankDonationItemClickListener(OnRankDonationItemClickListener listener) {
        this.listener = listener;
    }
}
