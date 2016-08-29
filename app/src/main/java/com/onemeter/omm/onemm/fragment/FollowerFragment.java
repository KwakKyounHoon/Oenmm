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
import com.onemeter.omm.onemm.adapter.FollowerAdatper;
import com.onemeter.omm.onemm.data.Follower;

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

    public FollowerFragment() {
        // Required empty public constructor
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

            }
        });
        init();
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
