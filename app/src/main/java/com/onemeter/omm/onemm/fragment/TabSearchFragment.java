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
import com.onemeter.omm.onemm.data.MyData;
import com.onemeter.omm.onemm.data.OtherData;
import com.onemeter.omm.onemm.data.Post;

public class TabSearchFragment extends BackKeyFragment {

    public static String TAG_SEARCH = "search";
    public static String TAG_FOLLOWER = "follower";
    public static String TAG_FOLLOWING = "following";
    public static String TAG_SEARCH_RESULT = "searchresult";
    public static String TAG_OTHER = "other";
    public static String TAG_LISTEN_ON ="listenon";
    public static String TAG_LISTEN_OFF ="listenoff";
    public static String TAG_QUESTION ="question";
    public static String TAG_SETTING = "setting";
    public static String TAG_MY = "my";
    public static String TAG_REPLY = "reply";
    public static String TAG_PROFILE = "profile";
    public static String TAG_DONATION = "donation";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fragment old = getChildFragmentManager()
                .findFragmentByTag(TAG_SEARCH);
        if(old == null){
            FragmentTransaction ft = getChildFragmentManager()
                    .beginTransaction();
            SearchFragment f = new SearchFragment();
            ft.replace(R.id.container,f , TAG_SEARCH);
            ft.commit();
        }
    }

    public TabSearchFragment() {
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

    public void showFollwer(String id){
        FragmentTransaction ft = getChildFragmentManager()
                .beginTransaction();
        FollowerFragment f = FollowerFragment.newInstance(id);
        ft.replace(R.id.container,f , TAG_FOLLOWER).addToBackStack(TAG_FOLLOWER);
        ft.commit();
    }

    public void showFollwing(String id){
        FragmentTransaction ft = getChildFragmentManager()
                .beginTransaction();
        FollowingFragment f = FollowingFragment.newInstance(id);
        ft.replace(R.id.container,f , TAG_FOLLOWING).addToBackStack(TAG_FOLLOWING);
        ft.commit();
    }

    public void showSearchResult(){
        FragmentTransaction ft = getChildFragmentManager()
                .beginTransaction();
        SearchResultFragment f = new SearchResultFragment();
        ft.replace(R.id.container,f , TAG_SEARCH_RESULT).addToBackStack(TAG_SEARCH_RESULT);
        ft.commit();
    }

    public void showOther(String id){
        FragmentTransaction ft = getChildFragmentManager()
                .beginTransaction();
        OtherFragment f = OtherFragment.newInstance(id);
        ft.replace(R.id.container,f , TAG_OTHER).addToBackStack(TAG_OTHER);
        ft.commit();
    }

    public void showListenToOff(Post post, int position){
        FragmentTransaction ft = getChildFragmentManager()
                .beginTransaction();
        ListenToOffFragment f = ListenToOffFragment.newInstance(post, position);
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

    public void showMy() {
        FragmentTransaction ft = getChildFragmentManager()
                .beginTransaction();
        MyPageFragment f = new MyPageFragment();
        ft.replace(R.id.container, f, TAG_MY).addToBackStack(TAG_MY);
        ft.commit();
    }

    public void showSetting() {
        FragmentTransaction ft = getChildFragmentManager()
                .beginTransaction();
        SettingFragment f = new SettingFragment();
        ft.replace(R.id.container, f, TAG_SETTING).addToBackStack(TAG_SETTING);
        ft.commit();
    }

    public void showReply(Post post) {
        FragmentTransaction ft = getChildFragmentManager()
                .beginTransaction();
        ReplyFragment f = ReplyFragment.newInstance(post);
        ft.replace(R.id.container, f, TAG_REPLY).addToBackStack(TAG_REPLY);
        ft.commit();
    }

    public void showProfile(MyData myData) {
        FragmentTransaction ft = getChildFragmentManager()
                .beginTransaction();
        ProfileModifyFragment f = ProfileModifyFragment.newInstance(myData);
        ft.replace(R.id.container, f, TAG_PROFILE).addToBackStack(TAG_PROFILE);
        ft.commit();
    }

    public void showDonate(String id) {
        FragmentTransaction ft = getChildFragmentManager()
                .beginTransaction();
        DonatingPlaceFragment f = DonatingPlaceFragment.newInstance(id);
        ft.replace(R.id.container, f, TAG_DONATION);
        ft.addToBackStack(TAG_DONATION);
        ft.commit();
    }

    int payPosition = 0;

    public void setPayPosition(int payPosition){
        this.payPosition = payPosition;
    }

    public int getPayPosition(){
        return  this.payPosition;
    }

}
