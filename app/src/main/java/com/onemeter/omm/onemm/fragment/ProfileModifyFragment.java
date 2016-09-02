package com.onemeter.omm.onemm.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.onemeter.omm.onemm.MainActivity;
import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.NetWorkResultType;
import com.onemeter.omm.onemm.manager.NetworkManager;
import com.onemeter.omm.onemm.manager.NetworkRequest;
import com.onemeter.omm.onemm.request.ModifyProfileRequest;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileModifyFragment extends Fragment {


    public ProfileModifyFragment() {
        // Required empty public constructor
    }
    @BindView(R.id.edit_name)
    EditText nameView;
    @BindView(R.id.edit_nickname)
    EditText nickNameView;
    @BindView(R.id.edit_message)
    EditText messageView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_modify, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        ((MainActivity) (getActivity())).changeHomeAsUp(true);
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        if (id == android.R.id.home) {
//            if(getParentFragment() instanceof TabMyFragment){
//                ((TabMyFragment) (getParentFragment())).popFragment();
//            }else if(getParentFragment() instanceof TabHomeFragment){
//                ((TabHomeFragment) (getParentFragment())).popFragment();
//            }else if(getParentFragment() instanceof TabRankFragment){
//                ((TabRankFragment) (getParentFragment())).popFragment();
//            }else{
//                ((TabSearchFragment) (getParentFragment())).popFragment();
//            }
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_back)
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

    @OnClick(R.id.btn_check)
    public void checkClick(View view){
        String name = nameView.getText().toString();
        String nickname = nickNameView.getText().toString();
        String message = messageView.getText().toString();
        ModifyProfileRequest request = new ModifyProfileRequest(getContext(), nickname, name, message, new File("tt"));
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetWorkResultType>() {
            @Override
            public void onSuccess(NetworkRequest<NetWorkResultType> request, NetWorkResultType result) {
                Toast.makeText(getContext(),result.getMessage(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(NetworkRequest<NetWorkResultType> request, int errorCode, String errorMessage, Throwable e) {

            }
        });
        if(TextUtils.isEmpty(name)) {
            Toast.makeText(getContext(),"이름을 입력하세요",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(nickname)){
            Toast.makeText(getContext(),"닉네임을 입력하세요",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(message)){
            Toast.makeText(getContext(),"상태 메세지를 입력하세요",Toast.LENGTH_SHORT).show();
        }
//        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(nickname) && !TextUtils.isEmpty(message)) {

//        }
    }




}
