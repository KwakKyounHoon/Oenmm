package com.onemeter.omm.onemm.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.adapter.RankDonationAdatper;
import com.onemeter.omm.onemm.data.DonationRank;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RankDonationFragment extends Fragment {

    public RankDonationFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.list)
    RecyclerView list;

    RankDonationAdatper mAdatper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rank_donation, container, false);
        ButterKnife.bind(this,view);
        mAdatper = new RankDonationAdatper();
        list.setAdapter(mAdatper);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(manager);
        mAdatper.setOnAdapterItemClickListener(new RankDonationAdatper.OnAdapterItemClickLIstener() {
            @Override
            public void onAdapterItemClick(View view, DonationRank donationRank, int position) {
                ((RankFragment) (getParentFragment())).showOther(donationRank.getUserId());
                ((RankFragment)(getParentFragment())).setTab(true);
            }
        });

        init();
        return view;
    }

    public void init(){
        for(int i = 0; i < 5; i++) {
            DonationRank donationRank = new DonationRank();
            donationRank.setName("name"+i);
            donationRank.setDonationName("PlaceNmae"+i);
            mAdatper.addDonationRank(donationRank);
        }
    }
}
