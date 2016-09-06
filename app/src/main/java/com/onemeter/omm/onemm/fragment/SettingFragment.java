package com.onemeter.omm.onemm.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.adapter.SettingAdapter;
import com.onemeter.omm.onemm.data.NetWorkResultType;
import com.onemeter.omm.onemm.data.SettingDonate;
import com.onemeter.omm.onemm.data.SettingSave;
import com.onemeter.omm.onemm.manager.NetworkManager;
import com.onemeter.omm.onemm.manager.NetworkRequest;
import com.onemeter.omm.onemm.request.SettingDonateRequest;
import com.onemeter.omm.onemm.request.SettingSaveRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {


    public SettingFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.list)
    RecyclerView list;
    SettingAdapter mAdatper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, view);
        mAdatper = new SettingAdapter();
        list.setAdapter(mAdatper);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(manager);
        SettingDonateRequest settingDonateRequest = new SettingDonateRequest(getContext());
        NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,settingDonateRequest, new NetworkManager.OnResultListener<NetWorkResultType<SettingDonate[]>>() {
            @Override
            public void onSuccess(NetworkRequest<NetWorkResultType<SettingDonate[]>> request, NetWorkResultType<SettingDonate[]> result) {
                mAdatper.addDonate(result.getResult());
            }

            @Override
            public void onFail(NetworkRequest<NetWorkResultType<SettingDonate[]>> request, int errorCode, String errorMessage, Throwable e) {

            }
        });
        SettingSaveRequest settingSaveRequest = new SettingSaveRequest(getContext());
        NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,settingSaveRequest, new NetworkManager.OnResultListener<NetWorkResultType<SettingSave[]>>() {
            @Override
            public void onSuccess(NetworkRequest<NetWorkResultType<SettingSave[]>> request, NetWorkResultType<SettingSave[]> result) {
                mAdatper.addSave(result.getResult());
            }

            @Override
            public void onFail(NetworkRequest<NetWorkResultType<SettingSave[]>> request, int errorCode, String errorMessage, Throwable e) {

            }
        });
        return view;
    }

    @OnClick(R.id.btn_back)
    public void backClick(View view){
        ((TabMyFragment) (getParentFragment())).popFragment();
    }

}
