package com.onemeter.omm.onemm.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.DonationRank;
import com.onemeter.omm.onemm.viewholder.RankDonationViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Tacademy on 2016-08-26.
 */
public class RankDonationAdatper extends RecyclerView.Adapter<RankDonationViewHolder> implements RankDonationViewHolder.OnRankDonationItemClickListener {

    List<DonationRank> donationRanks = new ArrayList<>();

    public void addDonationRank(DonationRank donationRank){
        donationRanks.add(donationRank);
        notifyDataSetChanged();
    }

    public void addAllDonationRank(DonationRank[] donationRanks){
        this.donationRanks.addAll(Arrays.asList(donationRanks));
        notifyDataSetChanged();
    }
    @Override
    public RankDonationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_rank_donate, parent, false);
        return new RankDonationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RankDonationViewHolder holder, int position) {
        RankDonationViewHolder rdvh = (RankDonationViewHolder)holder;
        rdvh.setOnRankDonationItemClickListener(this);
        rdvh.setDonationRank(donationRanks.get(position));
    }


    @Override
    public int getItemCount() {
        return donationRanks.size();
    }

    @Override
    public void onPostItemClick(View view, DonationRank donationRank, int position) {
        if(listener != null){
            listener.onAdapterItemClick(view, donationRank, position);
        }
    }
    public interface OnAdapterItemClickLIstener {
        public void onAdapterItemClick(View view, DonationRank donationRank, int position);
    }

    OnAdapterItemClickLIstener listener;
    public void setOnAdapterItemClickListener(OnAdapterItemClickLIstener listener) {
        this.listener = listener;
    }

}
