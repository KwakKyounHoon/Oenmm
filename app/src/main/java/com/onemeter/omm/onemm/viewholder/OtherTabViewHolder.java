package com.onemeter.omm.onemm.viewholder;

import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.onemeter.omm.onemm.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class OtherTabViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.tabs)
    TabLayout tabs;
    TabLayout.Tab receiveTab;
    TabLayout.Tab sendTab;

    Boolean isForced = false;

    public static String TAB_TAG_MY_RECEIVE = "receive";
    public static String TAB_TAG_MY_SEND = "send";


    public OtherTabViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (!isForced) {
                    String tag = (String) tab.getTag();

                    if (tag.equals(TAB_TAG_MY_RECEIVE)) {
                        if (listener != null) {
                            listener.onTabType(itemView, 1);

                        }
                    } else if (tag.equals(TAB_TAG_MY_SEND)) {
                        if (listener != null) {
                            listener.onTabType(itemView, 2);
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
        tabs.addTab(receiveTab.setTag(TAB_TAG_MY_RECEIVE));
        tabs.addTab(sendTab.setTag(TAB_TAG_MY_SEND));
    }

    public void setTabPosition(int position) {
        isForced = true;
        if(position == 1){
            receiveTab.select();
        }else{
            sendTab.select();
        }
        isForced = false;
    }

    public interface OnTabItemClickListener {
        public void onTabType(View view, int num);
    }

    OnTabItemClickListener listener;

    public void setOnTabClickListener(OnTabItemClickListener listener) {
        this.listener = listener;
    }


}
