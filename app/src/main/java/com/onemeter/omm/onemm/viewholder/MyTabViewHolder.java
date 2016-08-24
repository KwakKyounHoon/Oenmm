package com.onemeter.omm.onemm.viewholder;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
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
    FragmentManager fragmentManager;

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
                    }
                }else if(tag.equals(TAB_TAG_MY_SEND)){
                    if(listener != null){
                        listener.onCategory(itemView, true);
                    }
                }else if(tag.equals(TAB_TAG_LISTEN_RANK)){
                    if(listener != null){
                        listener.onCategory(itemView, false);
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

        tabs.addTab(tabs.newTab().setText("RECEIVE").setTag(TAB_TAG_MY_RECEIVE));
        tabs.addTab(tabs.newTab().setText("SEND").setTag(TAB_TAG_MY_SEND));
        tabs.addTab(tabs.newTab().setText("LISTEN").setTag(TAB_TAG_LISTEN_RANK));
    }


    public interface OnTabItemClickListener {
        public void onCategory(View view, boolean flag);
//        public void onCreatePost(View view, MyPageData myPageData);
    }

    MyPageData myPageData;
    OnTabItemClickListener listener;

    public void setOnTabClickListener(OnTabItemClickListener listener) {
        this.listener = listener;
    }

}
