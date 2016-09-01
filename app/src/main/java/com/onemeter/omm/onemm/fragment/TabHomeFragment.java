package com.onemeter.omm.onemm.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.OtherData;
import com.onemeter.omm.onemm.data.Post;

public class TabHomeFragment extends BackKeyFragment {

    public static String TAG_OTHER = "other";
    public static String TAG_FOLLOWER = "follower";
    public static String TAG_FOLLOWING = "following";
    public static String TAG_POST = "post";
    public static String TAG_LISTEN_ON ="listenon";
    public static String TAG_LISTEN_OFF ="listenoff";
    public static String TAG_QUESTION ="question";

    boolean isFirst = true;


    public TabHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isFirst) {
            Fragment old = getChildFragmentManager()
                    .findFragmentByTag(TAG_POST);
            if (old == null) {
                FragmentTransaction ft = getChildFragmentManager()
                        .beginTransaction();
                PostFragment f = new PostFragment();
                ft.replace(R.id.container, f, TAG_POST);
                ft.commit();
            }
        }
        isFirst = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_item, container, false);
        setHasOptionsMenu(true);
//        ((MainActivity) (getActivity())).changeHomeAsUp(false);
        return view;
    }

    public void showOther(String id){
        FragmentTransaction ft = getChildFragmentManager()
                .beginTransaction();
        OtherFragment f = OtherFragment.newInstance(id);
        ft.replace(R.id.container,f , TAG_OTHER).addToBackStack(null);
        ft.commit();
    }

    public void showFollwer(String id){
        FragmentTransaction ft = getChildFragmentManager()
                .beginTransaction();
        FollowerFragment f = FollowerFragment.newInstance(id);
        ft.replace(R.id.container,f , TAG_FOLLOWER).addToBackStack(null);
        ft.commit();
    }

    public void showFollwing(String id){
        FragmentTransaction ft = getChildFragmentManager()
                .beginTransaction();
        FollowingFragment f = FollowingFragment.newInstance(id);
        ft.replace(R.id.container,f , TAG_FOLLOWING).addToBackStack(null);
        ft.commit();
    }

    public void showListenToOff(Post post){
        FragmentTransaction ft = getChildFragmentManager()
                .beginTransaction();
        ListenToOffFragment f = ListenToOffFragment.newInstance(post);
        ft.replace(R.id.container,f , TAG_LISTEN_OFF).addToBackStack(null);
        ft.commit();
    }

    public void showListenToOn(Post post){
        FragmentTransaction ft = getChildFragmentManager()
                .beginTransaction();
        ListenToOnFragment f = ListenToOnFragment.newInstance(post);
        ft.replace(R.id.container,f , TAG_LISTEN_ON).addToBackStack(null);
        ft.commit();
    }

    public void popFragment(){
        getChildFragmentManager().popBackStack();
    }

    public void showQuestion(OtherData otherData) {
        FragmentTransaction ft = getChildFragmentManager()
                .beginTransaction();
        QuestionFragment f = QuestionFragment.newInstance(otherData);
        ft.replace(R.id.container,f , TAG_QUESTION).addToBackStack(null);
        ft.commit();
    }
}
