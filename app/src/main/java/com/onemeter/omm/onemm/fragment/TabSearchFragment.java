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

public class TabSearchFragment extends Fragment {

    public static String TAG_SEARCH = "search";
    public static String TAG_FOLLOWER = "follower";
    public static String TAG_FOLLOWING = "following";
    public static String TAG_DONATION = "donation";
    public static String TAG_SEARCH_RESULT = "searchresult";

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

    public void showSearchResult(){
        FragmentTransaction ft = getChildFragmentManager()
                .beginTransaction();
        SearchResultFragment f = new SearchResultFragment();
        ft.replace(R.id.container,f , TAG_SEARCH_RESULT).addToBackStack(TAG_SEARCH_RESULT);
        ft.addToBackStack(TAG_FOLLOWING);
        ft.commit();
    }

    public void popFragment(){
        getChildFragmentManager().popBackStack();
    }
}
