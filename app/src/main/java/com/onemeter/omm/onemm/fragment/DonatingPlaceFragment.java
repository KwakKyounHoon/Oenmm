package com.onemeter.omm.onemm.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.adapter.DonatingPlaceAdapter;
import com.onemeter.omm.onemm.data.DonatingPlace;
import com.onemeter.omm.onemm.data.NetWorkResultType;
import com.onemeter.omm.onemm.manager.NetworkManager;
import com.onemeter.omm.onemm.manager.NetworkRequest;
import com.onemeter.omm.onemm.request.ChangePlaceRequest;
import com.onemeter.omm.onemm.request.DonatingPlaceRequest;
import com.onemeter.omm.onemm.request.DonatingPlacesRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class DonatingPlaceFragment extends Fragment {

    public DonatingPlaceFragment() {
        // Required empty public constructor
    }

    public static String DONATINGPLACE = "donatingplace";
    String id;

    public static DonatingPlaceFragment newInstance(String id) {
        DonatingPlaceFragment fragment = new DonatingPlaceFragment();
        Bundle args = new Bundle();
        args.putString(DONATINGPLACE, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id =  getArguments().getString(DONATINGPLACE);
        }
    }

    @BindView(R.id.list)
    RecyclerView list;
    DonatingPlaceAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donating_place, container, false);
        ButterKnife.bind(this,view);
        setHasOptionsMenu(true);
        mAdapter = new DonatingPlaceAdapter();
        list.setAdapter(mAdapter);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 3);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(position == 0 && mAdapter.isHead()) return 3;
                return 1;
            }
        });
        list.setLayoutManager(manager);
        init();
        mAdapter.setOnAdapterItemClickListener(new DonatingPlaceAdapter.OnAdapterItemClickLIstener() {
            @Override
            public void onAdapterPlaceClick(View view, DonatingPlace donatingPlace, int position) {
                mAdapter.addHeadPlace(donatingPlace);
                id = donatingPlace.getDonationId();
            }
        });
        return view;
    }

    private void init() {
        DonatingPlacesRequest request = new DonatingPlacesRequest(getContext(), 1, 20);
        NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<DonatingPlace[]>>() {
            @Override
            public void onSuccess(NetworkRequest<NetWorkResultType<DonatingPlace[]>> request, NetWorkResultType<DonatingPlace[]> result) {
                mAdapter.addPlaces(result.getResult());
            }

            @Override
            public void onFail(NetworkRequest<NetWorkResultType<DonatingPlace[]>> request, int errorCode, String errorMessage, Throwable e) {

            }
        });

        if(id != null) {
            DonatingPlaceRequest donatingPlaceRequest = new DonatingPlaceRequest(getContext(), id);
            NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP, donatingPlaceRequest, new NetworkManager.OnResultListener<NetWorkResultType<DonatingPlace>>() {
                @Override
                public void onSuccess(NetworkRequest<NetWorkResultType<DonatingPlace>> request, NetWorkResultType<DonatingPlace> result) {
                    mAdapter.addHeadPlace(result.getResult());

                }

                @Override
                public void onFail(NetworkRequest<NetWorkResultType<DonatingPlace>> request, int errorCode, String errorMessage, Throwable e) {

                }
            });
        }
    }

    @OnClick(R.id.btn_check)
    public void checkClick(View view){
        ChangePlaceRequest request = new ChangePlaceRequest(getContext(), id);
        NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP, request, new NetworkManager.OnResultListener<NetWorkResultType>() {
            @Override
            public void onSuccess(NetworkRequest<NetWorkResultType> request, NetWorkResultType result) {
                Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                popFagment();
            }

            @Override
            public void onFail(NetworkRequest<NetWorkResultType> request, int errorCode, String errorMessage, Throwable e) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if(getParentFragment() instanceof TabMyFragment){
                ((TabMyFragment) (getParentFragment())).popFragment();
            }else if(getParentFragment() instanceof TabHomeFragment){
                ((TabHomeFragment) (getParentFragment())).popFragment();
            }else if(getParentFragment() instanceof TabRankFragment){
                ((TabRankFragment) (getParentFragment())).popFragment();
            }else{
                ((TabSearchFragment) (getParentFragment())).popFragment();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_cancel)
    public void backClick(View view){
        if(getParentFragment() instanceof TabMyFragment){
            ((TabMyFragment) (getParentFragment())).popFragment();
        }else if(getParentFragment() instanceof TabHomeFragment){
            ((TabHomeFragment) (getParentFragment())).popFragment();
        }else if(getParentFragment() instanceof TabRankFragment){
            ((TabRankFragment) (getParentFragment())).popFragment();
        }else{
            ((TabSearchFragment) (getParentFragment())).popFragment();
        }
    }

    public void popFagment(){
        if(getParentFragment() instanceof TabMyFragment){
            ((TabMyFragment) (getParentFragment())).popFragment();
        }else if(getParentFragment() instanceof TabHomeFragment){
            ((TabHomeFragment) (getParentFragment())).popFragment();
        }else if(getParentFragment() instanceof TabRankFragment){
            ((TabRankFragment) (getParentFragment())).popFragment();
        }else{
            ((TabSearchFragment) (getParentFragment())).popFragment();
        }
    }
}
