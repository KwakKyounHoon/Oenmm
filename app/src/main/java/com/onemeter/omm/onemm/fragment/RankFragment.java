package com.onemeter.omm.onemm.fragment;


import android.os.Bundle;
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

    public static String TAG_RANK_DOATE = "donate";
    public static String TAB_RANK_POPUL = "popul";

    public RankFragment() {
        // Required empty public constructor
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

                if (tag.equals(TAG_RANK_DOATE)){
                    Fragment f = new RankDonationFragment();
                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.container, f , (String)tab.getTag())
                            .commit();

                }else if(tag.equals(TAB_RANK_POPUL)){
                    Fragment f = new RankPopularFragment();
                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.container, f , (String)tab.getTag())
                            .commit();
                }
            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabs.addTab(tabs.newTab().setText("기부랭킹").setTag(TAG_RANK_DOATE));
        tabs.addTab(tabs.newTab().setText("인기질문").setTag(TAB_RANK_POPUL));
        return view;
    }

}
