package com.onemeter.omm.onemm.viewholder;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.fragment.MyListenFragment;
import com.onemeter.omm.onemm.fragment.MyReceiveFragment;
import com.onemeter.omm.onemm.fragment.MySendFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tacademy on 2016-08-23.
 */
public class MyTabViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tabs)
    TabLayout tabs;
    FragmentManager fragmentManager;

    public static String TAB_TAG_MY_RECEIVE = "receive";
    public static String TAB_TAG_MY_SEND = "send";
    public static String TAB_TAG_LISTEN_RANK = "listen";

    public MyTabViewHolder(View itemView, FragmentManager manager) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        fragmentManager = manager;

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                String tag = (String)tab.getTag();

                if (tag.equals(TAB_TAG_MY_RECEIVE)){
                    Fragment f = new MyReceiveFragment();
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, f , (String)tab.getTag())
                            .commit();

                }else if(tag.equals(TAB_TAG_MY_SEND)){
                    Fragment f = new MySendFragment();
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, f , (String)tab.getTag())
                            .commit();

                }else if(tag.equals(TAB_TAG_LISTEN_RANK)){
                    Fragment f = new MyListenFragment();
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, f , (String)tab.getTag())
                            .commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab == null) return;
                String tag = (String)tab.getTag();
                Fragment f = fragmentManager.findFragmentByTag(tag);
                if (f != null) {
                    fragmentManager.beginTransaction()
                            .detach(f)
                            .commit();
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                String tag = (String)tab.getTag();
                Fragment f = fragmentManager.findFragmentByTag(tag);
                if (f != null) {
                    fragmentManager.beginTransaction()
                            .attach(f)
                            .commit();
                }
            }
        });

        tabs.addTab(tabs.newTab().setText("RECEIVE").setTag(TAB_TAG_MY_RECEIVE));
        tabs.addTab(tabs.newTab().setText("SEND").setTag(TAB_TAG_MY_SEND));
        tabs.addTab(tabs.newTab().setText("LISTEN").setTag(TAB_TAG_LISTEN_RANK));
    }
}
