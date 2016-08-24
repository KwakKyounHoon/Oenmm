package com.onemeter.omm.onemm.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onemeter.omm.onemm.MainActivity;
import com.onemeter.omm.onemm.R;

public class TabRankFragment extends Fragment {

    public static String TAG_RANK = "rank";
    public static String TAG_FOLLOWING = "following";
    public static String TAG_FOLLOWER = "follower";
    public static String TAG_OTHER = "other";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fragment old = getChildFragmentManager()
                .findFragmentByTag(TAG_RANK);
        if(old == null){
            FragmentTransaction ft = getChildFragmentManager()
                    .beginTransaction();
            RankFragment f = new RankFragment();
            ft.replace(R.id.container,f , TAG_RANK);
            ft.commit();
        }
    }

    public TabRankFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_item, container, false);
        setHasOptionsMenu(true);
        ((MainActivity) (getActivity())).changeHomeAsUp(false);
        return view;
    }

    public void showOther(){
        FragmentTransaction ft = getChildFragmentManager()
                .beginTransaction();
        OtherFragment f = new OtherFragment();
        ft.replace(R.id.container,f , TAG_OTHER).addToBackStack(TAG_OTHER);
        ft.commit();
    }

    public void showFollwer(){
        FragmentTransaction ft = getChildFragmentManager()
                .beginTransaction();
        FollowerFragment f = new FollowerFragment();
        ft.replace(R.id.container,f , TAG_FOLLOWER).addToBackStack(TAG_FOLLOWER);
        ft.commit();
    }

    public void showFollwing(){
        FragmentTransaction ft = getChildFragmentManager()
                .beginTransaction();
        FollowingFragment f = new FollowingFragment();
        ft.replace(R.id.container,f , TAG_FOLLOWING).addToBackStack(TAG_FOLLOWING);
        ft.commit();
    }

    public void popFragment(){
        getChildFragmentManager().popBackStack();
    }
}
