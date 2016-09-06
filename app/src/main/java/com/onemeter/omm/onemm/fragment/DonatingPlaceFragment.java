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

import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.adapter.DonatingPlaceAdapter;
import com.onemeter.omm.onemm.data.DonatingPlace;
import com.onemeter.omm.onemm.data.NetWorkResultType;
import com.onemeter.omm.onemm.manager.NetworkManager;
import com.onemeter.omm.onemm.manager.NetworkRequest;
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
        // Inflate the layout for this fragment
        return view;
    }

    private void init() {
//        DonatingPlace[] d = {new DonatingPlace(), new DonatingPlace(), new DonatingPlace(), new DonatingPlace(), new DonatingPlace()};
//        mAdapter.addPlaces(d);
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

        DonatingPlaceRequest donatingPlaceRequest = new DonatingPlaceRequest(getContext(), id);
        NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP, donatingPlaceRequest, new NetworkManager.OnResultListener<NetWorkResultType<DonatingPlace[]>>() {
            @Override
            public void onSuccess(NetworkRequest<NetWorkResultType<DonatingPlace[]>> request, NetWorkResultType<DonatingPlace[]> result) {
                mAdapter.addHeadPlace(result.getResult());
            }

            @Override
            public void onFail(NetworkRequest<NetWorkResultType<DonatingPlace[]>> request, int errorCode, String errorMessage, Throwable e) {

            }
        });
//        DonatingPlace header = new DonatingPlace();
//        header.setDescription("Good");
//        header.setName("Hi");
//        mAdapter.addHeadPlace(header);
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
}
