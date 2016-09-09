package com.onemeter.omm.onemm.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.onemeter.omm.onemm.MainActivity;
import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.adapter.FollowerAdatper;
import com.onemeter.omm.onemm.data.Follower;
import com.onemeter.omm.onemm.data.NetWorkResultType;
import com.onemeter.omm.onemm.manager.NetworkManager;
import com.onemeter.omm.onemm.manager.NetworkRequest;
import com.onemeter.omm.onemm.request.MyFollowerRequest;
import com.onemeter.omm.onemm.request.OtherFollowerRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowerFragment extends Fragment {

    @BindView(R.id.list)
    RecyclerView list;
    FollowerAdatper mAdapter;
    String id;

    public FollowerFragment() {
        // Required empty public constructor
    }

    public static String USRE_ID = "id";

    public static FollowerFragment newInstance(String id) {
        FollowerFragment fragment = new FollowerFragment();
        Bundle args = new Bundle();
        args.putString(USRE_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString(USRE_ID);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_follower, container, false);
        ButterKnife.bind(this, view);
        mAdapter = new FollowerAdatper();
        list.setAdapter(mAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(manager);
        setHasOptionsMenu(true);
        ((MainActivity) (getActivity())).changeHomeAsUp(true);

        mAdapter.setOnAdapterItemClickListener(new FollowerAdatper.OnAdapterItemClickLIstener() {
            @Override
            public void onAdapterItemClick(View view, Follower follower, int position) {
                if(getParentFragment() instanceof TabMyFragment){
                    ((TabMyFragment) (getParentFragment())).showOther(follower.getUserId());
                }else if(getParentFragment() instanceof TabHomeFragment){
                    ((TabHomeFragment) (getParentFragment())).showOther(follower.getUserId());
                }else if(getParentFragment() instanceof TabRankFragment){
                    ((TabRankFragment) (getParentFragment())).showOther(follower.getUserId());
                }else{
                    ((TabSearchFragment) (getParentFragment())).showOther(follower.getUserId());
                }
            }

            @Override
            public void onAdapterLikeClick(View view, Follower follower, int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("정말 팔로우하시겠습니까?")
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getContext(), "follow dial cancel", Toast.LENGTH_SHORT).show();
                            }
                        }).setPositiveButton("예, 확실합니다.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(), "follow dial ok", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
//        init();
        if(id != "-1"){
            OtherFollowerRequest request = new OtherFollowerRequest(getContext(), id, 1, 20);
            NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP, request, new NetworkManager.OnResultListener<NetWorkResultType<Follower[]>>() {
                @Override
                public void onSuccess(NetworkRequest<NetWorkResultType<Follower[]>> request, NetWorkResultType<Follower[]> result) {
                    mAdapter.addAll(result.getResult());
                }

                @Override
                public void onFail(NetworkRequest<NetWorkResultType<Follower[]>> request, int errorCode, String errorMessage, Throwable e) {

                }
            });
        }else{
            MyFollowerRequest request = new MyFollowerRequest(getContext(), 1, 20);
            NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP, request, new NetworkManager.OnResultListener<NetWorkResultType<Follower[]>>() {
                @Override
                public void onSuccess(NetworkRequest<NetWorkResultType<Follower[]>> request, NetWorkResultType<Follower[]> result) {
                    mAdapter.addAll(result.getResult());
                }

                @Override
                public void onFail(NetworkRequest<NetWorkResultType<Follower[]>> request, int errorCode, String errorMessage, Throwable e) {

                }
            });
        }
        return view;
    }

    void init(){
        for(int i = 0; i < 10; i++){
            Follower follower = new Follower();
            follower.setName(i+"1");
            follower.setNickname(i+"2");
            follower.setPhoto(i+"#");
            follower.setUserId(i+"4");
            mAdapter.addFollwers(follower);
        }
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

//    @OnClick(R.id.btn_user)
//    public void onUserClick(){
//        if(getParentFragment() instanceof TabMyFragment){
//            ((TabMyFragment) (getParentFragment())).showOther("test");
//        }else if(getParentFragment() instanceof TabHomeFragment){
//            ((TabHomeFragment) (getParentFragment())).showOther("test");
//        }else if(getParentFragment() instanceof TabRankFragment){
//            ((TabRankFragment) (getParentFragment())).showOther("test");
//        }else{
//            ((TabSearchFragment) (getParentFragment())).showOther("test");
//        }
//    }

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

}
