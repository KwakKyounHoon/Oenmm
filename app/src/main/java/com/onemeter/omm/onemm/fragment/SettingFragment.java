package com.onemeter.omm.onemm.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.login.LoginManager;
import com.onemeter.omm.onemm.LoginActivity;
import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.SettingDialog;
import com.onemeter.omm.onemm.SettingWithdrawDial;
import com.onemeter.omm.onemm.adapter.SettingAdapter;
import com.onemeter.omm.onemm.data.NetWorkResultType;
import com.onemeter.omm.onemm.data.SettingDonate;
import com.onemeter.omm.onemm.data.SettingSave;
import com.onemeter.omm.onemm.manager.NetworkManager;
import com.onemeter.omm.onemm.manager.NetworkRequest;
import com.onemeter.omm.onemm.manager.PropertyManager;
import com.onemeter.omm.onemm.request.LogOutRequest;
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
        NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP, settingDonateRequest, new NetworkManager.OnResultListener<NetWorkResultType<SettingDonate>>() {
            @Override
            public void onSuccess(NetworkRequest<NetWorkResultType<SettingDonate>> request, NetWorkResultType<SettingDonate> result) {
                mAdatper.addDonate(result.getResult());
            }

            @Override
            public void onFail(NetworkRequest<NetWorkResultType<SettingDonate>> request, int errorCode, String errorMessage, Throwable e) {

            }
        });
        SettingSaveRequest settingSaveRequest = new SettingSaveRequest(getContext());
        NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP, settingSaveRequest, new NetworkManager.OnResultListener<NetWorkResultType<SettingSave>>() {
            @Override
            public void onSuccess(NetworkRequest<NetWorkResultType<SettingSave>> request, NetWorkResultType<SettingSave> result) {
                mAdatper.addSave(result.getResult());
            }

            @Override
            public void onFail(NetworkRequest<NetWorkResultType<SettingSave>> request, int errorCode, String errorMessage, Throwable e) {

            }
        });
        mAdatper.setOnMyDataItemClickListener(new SettingAdapter.OnSettingAdapterItemClickListener() {


            @Override
            public void onExpandClick(View view, int position, boolean expandFlag) {
            }

            @Override
            public void onAdatperAgreeClick(View view) {
                ((TabMyFragment) (getParentFragment())).settIngAgree();
            }

            @Override
            public void onAdapterChargeClick(View view) {
                SettingDialog settingDialog = new SettingDialog();
                settingDialog.show(getFragmentManager(), "CHARGE");
            }

            @Override
            public void onAdapterLogoutClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("정말 로그아웃 하시겠습니까?")
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        LoginManager.getInstance().logOut();
                        LogOutRequest request = new LogOutRequest(getContext());
                        NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP, request, new NetworkManager.OnResultListener<NetWorkResultType>() {
                            @Override
                            public void onSuccess(NetworkRequest<NetWorkResultType> request, NetWorkResultType result) {
                                PropertyManager.getInstance().setFacebookId("");
                                Intent intent = new Intent(getContext(), LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                getActivity().finish();
                            }

                            @Override
                            public void onFail(NetworkRequest<NetWorkResultType> request, int errorCode, String errorMessage, Throwable e) {

                            }
                        });
                    }
                });
                builder.show();

            }

            @Override
            public void onAdapterWithdrawClick(View view, SettingSave settingSave, int position) {
                SettingWithdrawDial settingWithdrawDial = SettingWithdrawDial.newInstance(settingSave.getCurrentPoint());
                settingWithdrawDial.show(getFragmentManager(), "WIthDRWAL");
            }

            @Override
            public void onAdapterAnswerSwitchClick(Boolean flag, int position) {
                if(flag) {
                    PropertyManager.getInstance().setAnswerSwitch("1");
                }else {
                    PropertyManager.getInstance().setAnswerSwitch("0");
                }
            }

            @Override
            public void onAdapterQuestionSwitchClick(Boolean flag, int position) {
                if(flag) {
                    PropertyManager.getInstance().setQuestionSwitch("1");
                }else {
                    PropertyManager.getInstance().setQuestionSwitch("0");
                }
            }

            @Override
            public void onAdapterLikeSwitchClick(Boolean flag, int position) {
                if(flag) {
                    PropertyManager.getInstance().setLikeSwitch("1");
                }else {
                    PropertyManager.getInstance().setLikeSwitch("0");
                }
            }
        });
        return view;
    }

    @OnClick(R.id.btn_back)
    public void backClick(View view) {
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
