package com.onemeter.omm.onemm.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onemeter.omm.onemm.MainActivity;
import com.onemeter.omm.onemm.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RankFragment extends Fragment {

    @BindView(R.id.tabs)
    TabLayout tabs;
    TabLayout.Tab donateTab;
    TabLayout.Tab populTab;
    public static String TAG_RANK_DOATE = "donate";
    public static String TAG_RANK_POPUL = "popul";
    boolean tabFlag = true;


    public RankFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rank, container, false);
        ButterKnife.bind(this,view);
        setHasOptionsMenu(true);
        ((MainActivity) (getActivity())).actionBarHide();
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String tag = (String)tab.getTag();
                    if (tag.equals(TAG_RANK_DOATE)) {
                        Fragment f = new RankDonationFragment();
                        getChildFragmentManager().beginTransaction()
                                .replace(R.id.container, f, (String) tab.getTag())
                                .commit();
                    } else if (tag.equals(TAG_RANK_POPUL)) {
                        Fragment f = new RankPopularFragment();
                        getChildFragmentManager().beginTransaction()
                                .replace(R.id.container, f, (String) tab.getTag())
                                .commit();
                    }

//                if (tag.equals(TAG_RANK_DOATE)){
//                    Fragment f = getChildFragmentManager().findFragmentByTag(tag);
//                    if (f != null) {
//                        getChildFragmentManager().beginTransaction()
//                                .attach(f)
//                                .commit();
//                    }else{
//                        f = new RankDonationFragment();
//                        getChildFragmentManager().beginTransaction()
//                                .replace(R.id.container, f , (String)tab.getTag())
//                                .commit();
//                    }
//                    tabFlag = true;
//                }else if(tag.equals(TAG_RANK_POPUL)){
//                    Fragment f = getChildFragmentManager().findFragmentByTag(tag);
//                    if (f != null) {
//                        getChildFragmentManager().beginTransaction()
//                                .attach(f)
//                                .commit();
//                    }else {
//                        f = new RankPopularFragment();
//                        getChildFragmentManager().beginTransaction()
//                                .replace(R.id.container, f, (String) tab.getTag())
//                                .commit();
//                    }
//                    tabFlag = false;
//                }
            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab == null) return;
                String tag = (String)tab.getTag();
                Fragment f = getChildFragmentManager().findFragmentByTag(tag);
                if (f != null) {
                    getChildFragmentManager().beginTransaction()
                            .detach(f)
                            .commit();
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                String tag = (String)tab.getTag();
                Fragment f = getChildFragmentManager().findFragmentByTag(tag);
                if (f != null) {
                    getChildFragmentManager ().beginTransaction()
                            .attach(f)
                            .commit();
                }
            }
        });

        donateTab = tabs.newTab().setText("기부랭킹").setTag(TAG_RANK_DOATE);
        populTab = tabs.newTab().setText("인기질문").setTag(TAG_RANK_POPUL);
        tabs.addTab(donateTab);
        tabs.addTab(populTab);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(tabFlag){
            donateTab.select();
        }else populTab.select();
    }

    public void showOther(String userId){
        ((TabRankFragment)getParentFragment()).showOther(userId);
    }

    public void setTab(boolean flag){
        tabFlag =  flag;
    }
}
