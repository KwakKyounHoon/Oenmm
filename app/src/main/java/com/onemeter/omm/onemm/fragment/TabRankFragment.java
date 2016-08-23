package com.onemeter.omm.onemm.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onemeter.omm.onemm.R;

public class TabRankFragment extends Fragment {

    public static String TAG_RANK = "rank";

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

        return view;
    }
}
