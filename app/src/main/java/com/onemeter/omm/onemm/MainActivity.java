package com.onemeter.omm.onemm;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.onemeter.omm.onemm.fragment.BackKeyFragment;
import com.onemeter.omm.onemm.fragment.TabHomeFragment;
import com.onemeter.omm.onemm.fragment.TabMyFragment;
import com.onemeter.omm.onemm.fragment.TabRankFragment;
import com.onemeter.omm.onemm.fragment.TabSearchFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tabs)
    TabLayout tabs;

    public static String TAB_TAG_HOME = "home";
    public static String TAB_TAG_SEARCH = "search";
    public static String TAB_TAG_RANK = "rank";
    public static String TAB_TAG_MY = "my";

    BackKeyFragment currentFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                String tag = (String)tab.getTag();

                if (tag.equals(TAB_TAG_HOME)){
                    Fragment f = getSupportFragmentManager().findFragmentByTag(tag);
                    if (f != null) {
                        getSupportFragmentManager().beginTransaction()
                                .attach(f)
                                .commit();
                        currentFragment = (TabHomeFragment) f;
                    }else{
                        f = new TabHomeFragment();
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.container, f , (String)tab.getTag())
                                .commit();
                        currentFragment = (TabHomeFragment) f;
                    }
                }else if(tag.equals(TAB_TAG_MY)){
                    Fragment f = getSupportFragmentManager().findFragmentByTag(tag);
                    if (f != null) {
                        getSupportFragmentManager().beginTransaction()
                                .attach(f)
                                .commit();
                        currentFragment = (TabMyFragment) f;
                    }else {
                        f = new TabMyFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, f, (String) tab.getTag())
                                .commit();
                        currentFragment = (TabMyFragment) f;
                    }
                }else if(tag.equals(TAB_TAG_SEARCH)){
                    Fragment f = getSupportFragmentManager().findFragmentByTag(tag);
                    if (f != null) {
                        getSupportFragmentManager().beginTransaction()
                                .attach(f)
                                .commit();
                        currentFragment = (TabSearchFragment) f;
                    }else {
                        f = new TabSearchFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, f, (String) tab.getTag())
                                .commit();
                        currentFragment = (TabSearchFragment) f;
                    }
                }else if(tag.equals(TAB_TAG_RANK)){
                    Fragment f = getSupportFragmentManager().findFragmentByTag(tag);
                    if (f != null) {
                        getSupportFragmentManager().beginTransaction()
                                .attach(f)
                                .commit();
                        currentFragment = (TabRankFragment) f;
                    }else {
                        f = new TabRankFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, f, (String) tab.getTag())
                                .commit();
                        currentFragment = (TabRankFragment) f;
                    }
                }
            }



            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab == null) return;
                String tag = (String)tab.getTag();
                Fragment f = getSupportFragmentManager().findFragmentByTag(tag);
                if (f != null) {
                    getSupportFragmentManager().beginTransaction()
                            .detach(f)
                            .commit();
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                String tag = (String)tab.getTag();
                Fragment f = getSupportFragmentManager().findFragmentByTag(tag);
                if (f != null) {
                    getSupportFragmentManager().beginTransaction()
                            .attach(f)
                            .commit();
                }
            }
        });

        TabLayout.Tab home = tabs.newTab().setIcon(R.drawable.main_home_tab).setTag(TAB_TAG_HOME);
        TabLayout.Tab search = tabs.newTab().setIcon(R.drawable.main_search_tab).setTag(TAB_TAG_SEARCH);
        TabLayout.Tab rank = tabs.newTab().setIcon(R.drawable.main_rank_tab).setTag(TAB_TAG_RANK);
        TabLayout.Tab my = tabs.newTab().setIcon(R.drawable.main_my_tab).setTag(TAB_TAG_MY);

        tabs.addTab(home);
        tabs.addTab(search);
        tabs.addTab(rank);
        tabs.addTab(my);
        tabs.setSelectedTabIndicatorColor(Color.WHITE);
    }

    public void changeHomeAsUp(boolean isBack) {
        if (isBack) {
//            getSupportActionBar().show();
//            setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
//            getSupportActionBar().setHomeAsUpIndicator(android.R.drawable.ic_input_add);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
//            getSupportActionBar().show();
//            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    public void onBackPressed() {
        if (currentFragment != null) {
            boolean isProcessed = currentFragment.onBackPressed();
            if (isProcessed) return;
        }
        super.onBackPressed();
    }

    public void actionBarHide(){
//        getSupportActionBar().setShowHideAnimationEnabled(false);
//        getSupportActionBar().hide();
    }
}
