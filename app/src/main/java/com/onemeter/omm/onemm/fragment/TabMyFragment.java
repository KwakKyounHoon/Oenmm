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


public class TabMyFragment extends Fragment {

    public static String TAG_MY = "my";
    public static String TAG_DONATION ="donation";
    public static String TAG_FOLLOWING = "following";
    public static String TAG_FOLLOWER = "follower";
    public static String TAG_PROFILE = "profile";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fragment old = getChildFragmentManager()
                .findFragmentByTag(TAG_MY);
        if(old == null){
            FragmentTransaction ft = getChildFragmentManager()
                    .beginTransaction();
            MyPageFragment f = new MyPageFragment();
            ft.add(R.id.container,f , TAG_MY).addToBackStack(TAG_MY);
            ft.commit();
        }
    }


    public TabMyFragment() {
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

    public void showDonate(){
        FragmentTransaction ft = getChildFragmentManager()
                .beginTransaction();
        DonatingPlaceFragment f = new DonatingPlaceFragment();
        ft.replace(R.id.container,f , TAG_DONATION);
//        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void showFollwer(){
        FragmentTransaction ft = getChildFragmentManager()
                .beginTransaction();
        FollowerFragment f = new FollowerFragment();
        ft.replace(R.id.container,f , TAG_FOLLOWER).addToBackStack(TAG_FOLLOWER);
        ft.addToBackStack(TAG_FOLLOWER);
        ft.commit();
    }

    public void showFollwing(){
        FragmentTransaction ft = getChildFragmentManager()
                .beginTransaction();
        FollowingFragment f = new FollowingFragment();
        ft.replace(R.id.container,f , TAG_FOLLOWING).addToBackStack(TAG_FOLLOWING);
        ft.addToBackStack(TAG_FOLLOWING);
        ft.commit();
    }

    public void showProfile(){
        FragmentTransaction ft = getChildFragmentManager()
                .beginTransaction();
        ProfileModifyFragment f = new ProfileModifyFragment();
        ft.replace(R.id.container,f , TAG_PROFILE).addToBackStack(TAG_PROFILE);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void showMy(){
        FragmentTransaction ft = getChildFragmentManager()
                .beginTransaction();
        MyPageFragment f = new MyPageFragment();
        ft.replace(R.id.container,f , TAG_MY).addToBackStack(TAG_MY);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void popFragment(){
        getChildFragmentManager().popBackStack();
    }
}
