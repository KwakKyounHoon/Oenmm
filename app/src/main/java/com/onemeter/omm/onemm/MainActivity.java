package com.onemeter.omm.onemm;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.onemeter.omm.onemm.fragment.TabHomeFragment;
import com.onemeter.omm.onemm.fragment.TabMyFragment;
import com.onemeter.omm.onemm.fragment.TabRankFragment;
import com.onemeter.omm.onemm.fragment.TabSearchFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tabs)
    TabLayout tabs;

    @BindView(R.id.toolbar_name)
    TextView toolbarView;

    public static String TAB_TAG_HOME = "home";
    public static String TAB_TAG_SEARCH = "search";
    public static String TAB_TAG_RANK = "rank";
    public static String TAB_TAG_MY = "my";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                String tag = (String)tab.getTag();

                if (tag.equals(TAB_TAG_HOME)){
                    Fragment f = new TabHomeFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, f , (String)tab.getTag())
                            .commit();

                }else if(tag.equals(TAB_TAG_MY)){
                    Fragment f = new TabMyFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, f , (String)tab.getTag())
                            .commit();

                }else if(tag.equals(TAB_TAG_SEARCH)){
                    Fragment f = new TabSearchFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, f , (String)tab.getTag())
                            .commit();

                }else if(tag.equals(TAB_TAG_RANK)){
                    Fragment f = new TabRankFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, f , (String)tab.getTag())
                            .commit();
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
        tabs.addTab(tabs.newTab().setText("HOME").setTag(TAB_TAG_HOME));
        tabs.addTab(tabs.newTab().setText("SEARCH").setTag(TAB_TAG_SEARCH));
        tabs.addTab(tabs.newTab().setText("RANK").setTag(TAB_TAG_RANK));
        tabs.addTab(tabs.newTab().setText("MY").setTag(TAB_TAG_MY));
    }

    public void changeHomeAsUp(boolean isBack) {
        if (isBack) {
            getSupportActionBar().show();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        } else {
            getSupportActionBar().show();
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    public void actionBarHide(){
//        getSupportActionBar().setShowHideAnimationEnabled(false);
        getSupportActionBar().hide();
    }

    public void setToolbarText(String name){
        toolbarView.setText(name);
    }
}
