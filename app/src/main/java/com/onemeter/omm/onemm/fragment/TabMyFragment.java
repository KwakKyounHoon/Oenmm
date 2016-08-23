package com.onemeter.omm.onemm.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onemeter.omm.onemm.R;


public class TabMyFragment extends Fragment {

    public static String TAG_MY;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fragment old = getChildFragmentManager()
                .findFragmentByTag(TAG_MY);
        if(old == null){
            FragmentTransaction ft = getChildFragmentManager()
                    .beginTransaction();
            MyPageFragment f = new MyPageFragment();
            ft.replace(R.id.container,f , TAG_MY);
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

        return view;
    }
}
