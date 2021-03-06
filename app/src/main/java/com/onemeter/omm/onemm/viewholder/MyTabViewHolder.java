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

    Boolean isForced = false;

    public static String TAB_TAG_MY_RECEIVE = "receive";
    public static String TAB_TAG_MY_SEND = "send";
    public static String TAB_TAG_LISTEN_RANK = "listen";

    public MyTabViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (!isForced) {
                    String tag = (String) tab.getTag();

                    if (tag.equals(TAB_TAG_MY_RECEIVE)) {
                        if (listener != null) {
                            listener.onTabType(itemView, 1, getAdapterPosition());
                        }
                    } else if (tag.equals(TAB_TAG_MY_SEND)) {
                        if (listener != null) {
                            listener.onTabType(itemView, 2, getAdapterPosition());
                        }
                    } else if (tag.equals(TAB_TAG_LISTEN_RANK)) {
                        if (listener != null) {
                            listener.onTabType(itemView, 3, getAdapterPosition());
                        }
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
        receiveTab = tabs.newTab().setIcon(R.drawable.mypage_receive_tab).setTag(TAB_TAG_MY_RECEIVE);
        sendTab = tabs.newTab().setIcon(R.drawable.mypage_send_tab).setTag(TAB_TAG_MY_SEND);
        listenTab = tabs.newTab().setIcon(R.drawable.mypage_hearing_tab).setTag(TAB_TAG_LISTEN_RANK);
        tabs.addTab(receiveTab);
        tabs.addTab(sendTab);
        tabs.addTab(listenTab);

    }


    public interface OnTabItemClickListener {
        public void onTabType(View view, int num, int position);
    }

    MyPageData myPageData;
    OnTabItemClickListener listener;

    public void setOnTabClickListener(OnTabItemClickListener listener) {
        this.listener = listener;
    }

    public void setTabPosition(int position){
        isForced = true;
        if(position == 1){
            receiveTab.select();
        }else if(position == 2){
            sendTab.select();
        }else {
            listenTab.select();
        }
        isForced = false;
    }
}
