package com.onemeter.omm.onemm.viewholder;

import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.MyPageData;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tacademy on 2016-08-23.
 */
public class MyTabViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tabs)
    TabLayout tabs;
    TabLayout.Tab receiveTab;
    TabLayout.Tab sendTab;
    TabLayout.Tab listenTab;

    public static String TAB_TAG_MY_RECEIVE = "receive";
    public static String TAB_TAG_MY_SEND = "send";
    public static String TAB_TAG_LISTEN_RANK = "listen";

    public MyTabViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                String tag = (String)tab.getTag();

                if (tag.equals(TAB_TAG_MY_RECEIVE)){
                    if(listener != null){
                        listener.onCategory(itemView, true);
                        listener.onTabType(itemView, 1);
                    }
                }else if(tag.equals(TAB_TAG_MY_SEND)){
                    if(listener != null){
                        listener.onCategory(itemView, true);
                        listener.onTabType(itemView, 2);
                    }
                }else if(tag.equals(TAB_TAG_LISTEN_RANK)){
                    if(listener != null){
                        listener.onCategory(itemView, false);
                        listener.onTabType(itemView, 3);
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        receiveTab = tabs.newTab().setText("RECEIVE").setTag(TAB_TAG_MY_RECEIVE);
        sendTab = tabs.newTab().setText("SEND").setTag(TAB_TAG_MY_SEND);
        listenTab = tabs.newTab().setText("LISTEN").setTag(TAB_TAG_LISTEN_RANK);
        tabs.addTab(receiveTab);
        tabs.addTab(sendTab);
        tabs.addTab(listenTab);

    }


    public interface OnTabItemClickListener {
        public void onCategory(View view, boolean flag);
        public void onTabType(View view, int num);
    }

    MyPageData myPageData;
    OnTabItemClickListener listener;

    public void setOnTabClickListener(OnTabItemClickListener listener) {
        this.listener = listener;
    }

    public void setTabPosition(int position){
        if(position == 1){
            receiveTab.select();
        }else if(position == 2){
            sendTab.select();
        }else {
            listenTab.select();
        }
    }
}
