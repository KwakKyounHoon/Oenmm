package com.onemeter.omm.onemm.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onemeter.omm.onemm.MultiSwipeRefreshLayout;
import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.adapter.RankDonationAdatper;
import com.onemeter.omm.onemm.data.DonationRank;
import com.onemeter.omm.onemm.data.NetWorkResultType;
import com.onemeter.omm.onemm.manager.NetworkManager;
import com.onemeter.omm.onemm.manager.NetworkRequest;
import com.onemeter.omm.onemm.request.RankDonationRequest;

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

    @BindView(R.id.refresh_view)
    MultiSwipeRefreshLayout refreshLayout;

    RankDonationAdatper mAdatper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RankDonationRequest request = new RankDonationRequest(getContext());
        NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<DonationRank[]>>() {
            @Override
            public void onSuccess(NetworkRequest<NetWorkResultType<DonationRank[]>> request, NetWorkResultType<DonationRank[]> result) {
                mAdatper.addAllDonationRank(result.getResult());
            }

            @Override
            public void onFail(NetworkRequest<NetWorkResultType<DonationRank[]>> request, int errorCode, String errorMessage, Throwable e) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rank_donation, container, false);
        ButterKnife.bind(this,view);
        refreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE);
        refreshLayout.setScrollChild(R.id.list);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdatper.clear();
                RankDonationRequest request = new RankDonationRequest(getContext());
                NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<DonationRank[]>>() {
                    @Override
                    public void onSuccess(NetworkRequest<NetWorkResultType<DonationRank[]>> request, NetWorkResultType<DonationRank[]> result) {
                        mAdatper.addAllDonationRank(result.getResult());
                        refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFail(NetworkRequest<NetWorkResultType<DonationRank[]>> request, int errorCode, String errorMessage, Throwable e) {

                    }
                });
            }
        });
        mAdatper = new RankDonationAdatper();
        list.setAdapter(mAdatper);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(manager);
        mAdatper.setOnAdapterItemClickListener(new RankDonationAdatper.OnAdapterItemClickLIstener() {
            @Override
            public void onAdapterItemClick(View view, DonationRank donationRank, int position) {
                ((RankFragment) (getParentFragment())).showOther(donationRank.getUserId());

            }
        });

        return view;
    }

}
