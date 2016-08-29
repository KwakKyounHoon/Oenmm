package com.onemeter.omm.onemm.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.onemeter.omm.onemm.MainActivity;
import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.adapter.FollowingAdapter;
import com.onemeter.omm.onemm.data.Following;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowingFragment extends Fragment {

    @BindView(R.id.list)
    RecyclerView list;
    FollowingAdapter mAdapter;

    public FollowingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_following, container, false);
        ButterKnife.bind(this, view);
        mAdapter = new FollowingAdapter();
        list.setAdapter(mAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(manager);
        setHasOptionsMenu(true);
        ((MainActivity) (getActivity())).changeHomeAsUp(true);
        mAdapter.setOnAdapterItemClickListener(new FollowingAdapter.OnAdapterItemClickLIstener() {
            @Override
            public void onAdapterItemClick(View view, Following following, int position) {
                if(getParentFragment() instanceof TabMyFragment){
                    ((TabMyFragment) (getParentFragment())).showOther(following.getUserId());
                }else if(getParentFragment() instanceof TabHomeFragment){
                    ((TabHomeFragment) (getParentFragment())).showOther(following.getUserId());
                }else if(getParentFragment() instanceof TabRankFragment){
                    ((TabRankFragment) (getParentFragment())).showOther(following.getUserId());
                }else{
                    ((TabSearchFragment) (getParentFragment())).showOther(following.getUserId());
                }
            }

            @Override
            public void onAdapterArrowClick(View view, Following following, int position) {

            }
        });
        init();
        return view;
    }

    void init(){
        for(int i = 0; i < 10; i++){
            Following following = new Following();
            following.setName(i+"1");
            following.setNickname(i+"2");
            following.setPhoto(i+"#");
            following.setUserId(i+"4");
            following.setDistance(i+"0");
            mAdapter.addFollowings(following);
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

    @OnClick(R.id.btn_back)
    public void backclick(View view){
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
