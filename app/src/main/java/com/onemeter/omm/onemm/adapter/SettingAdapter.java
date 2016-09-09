package com.onemeter.omm.onemm.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onemeter.omm.onemm.MyApplication;
import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.SettingData;
import com.onemeter.omm.onemm.data.SettingDonate;
import com.onemeter.omm.onemm.data.SettingSave;
import com.onemeter.omm.onemm.viewholder.SettingAgreeViewHolder;
import com.onemeter.omm.onemm.viewholder.SettingCategoryViewHolder;
import com.onemeter.omm.onemm.viewholder.SettingChargeViewHolder;
import com.onemeter.omm.onemm.viewholder.SettingDonateViewHolder;
import com.onemeter.omm.onemm.viewholder.SettingLogoutViewHolder;
import com.onemeter.omm.onemm.viewholder.SettingNotifyViewHolder;
import com.onemeter.omm.onemm.viewholder.SettingSaveViewHolder;

/**
 * Created by Tacademy on 2016-08-30.
 */
public class SettingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SettingSaveViewHolder.OnSettingSaveItemClickListener {

    SettingData settingData = new SettingData();

    public void addSave(SettingSave settingSaves){
        settingData.setSettingSave(settingSaves);
        notifyDataSetChanged();
    }


    public void addDonate(SettingDonate[] settingDonates){
        settingData.setSettingDonate(settingDonates[0]);
        notifyDataSetChanged();
    }

    public void addDonate(SettingDonate settingDonates){
        settingData.setSettingDonate(settingDonates);
        notifyDataSetChanged();
    }

    public static final int VIEW_TYPE_CATEGORY = 1;
    public static final int VIEW_TYPE_NOTIFY = 2;
    public static final int VIEW_TYPE_SAVE_PARENT = 3;
//    public static final int VIEW_TYPE_SAVE_CHILD = 30;
    public static final int VIEW_TYPE_DONATE = 4;
    public static final int VIEW_TYPE_LOGOUT = 5;
    public static final int VIEW_TYPE_AGREE = 6;
    public static final int VIEW_TYPE_CHARGE = 7;

    @Override
    public int getItemViewType(int position) {
        if(position == 0) return VIEW_TYPE_CATEGORY;
        position--;
        if(position == 0) return VIEW_TYPE_NOTIFY;
        position--;
        if(settingData.getSettingSave() != null) {
            if (position == 0) return VIEW_TYPE_CATEGORY;
            position--;
            if (position == 0) return VIEW_TYPE_SAVE_PARENT;
            position--;
        }
        if(position == 0) return VIEW_TYPE_CHARGE;
        position--;
        if(settingData.getSettingDonate() != null) {
            if (position == 0) return VIEW_TYPE_CATEGORY;
            position--;
            if (position == 0) return VIEW_TYPE_DONATE;
            position--;
        }
        if(position == 0) return VIEW_TYPE_AGREE;
        position--;
        if(position == 0) return VIEW_TYPE_LOGOUT;
        throw new IllegalArgumentException("invalid position");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case VIEW_TYPE_CATEGORY :{
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_setting_category, parent, false);
                return new SettingCategoryViewHolder(view);
            }
            case VIEW_TYPE_NOTIFY :{
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_setting_notify, parent, false);
                return new SettingNotifyViewHolder(view);
            }
            case VIEW_TYPE_SAVE_PARENT :{
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_setting_savemoney, parent, false);
                return new SettingSaveViewHolder(view);
            }
            case VIEW_TYPE_DONATE :{
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_setting_donate, parent, false);
                return new SettingDonateViewHolder(view);
            }
            case VIEW_TYPE_AGREE :{
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_setting_agree, parent, false);
                return new SettingAgreeViewHolder(view);
            }case VIEW_TYPE_LOGOUT :{
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_setting_category, parent, false);
                return new SettingLogoutViewHolder(view);
            }case VIEW_TYPE_CHARGE : {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_setting_charge, parent, false);
                return new SettingChargeViewHolder(view);
            }
        }
        throw new IllegalArgumentException("invalid viewtype");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position == 0) {
            SettingCategoryViewHolder scvh = (SettingCategoryViewHolder) holder;
            scvh.setCategory(MyApplication.getContext().getString(R.string.setting_notify_text));
            return;
        }
        position--;
        if(position == 0) {
            SettingNotifyViewHolder snvh = (SettingNotifyViewHolder) holder;
            return;
        }
        position--;
        if(settingData.getSettingSave() != null) {
            if (position == 0){
                SettingCategoryViewHolder scvh = (SettingCategoryViewHolder) holder;
                scvh.setCategory(MyApplication.getContext().getString(R.string.setting_save_text));
                return;
            }
            position--;
            if (position == 0){
                SettingSaveViewHolder ssvh = (SettingSaveViewHolder)holder;
                ssvh.setPoin(settingData.getSettingSave());
                ssvh.setSettingSave(settingData.getSettingSave());
                ssvh.setSettingSaveItemClickListener(this);
                return;
            }
            position--;
        }
        if(position == 0) {
            SettingChargeViewHolder slvh = (SettingChargeViewHolder) holder;
            slvh.setTextCharge(MyApplication.getContext().getString(R.string.setting_charge_text));
            return;
        }
        position--;
        if(settingData.getSettingDonate() != null) {
            if (position == 0){
                SettingCategoryViewHolder sccvh = (SettingCategoryViewHolder) holder;
                sccvh.setCategory(MyApplication.getContext().getString(R.string.setting_donate_text));
                return;
            }
            position--;
            if (position == 0) {
                SettingDonateViewHolder sdvh = (SettingDonateViewHolder)holder;
                sdvh.setSettingDonate(settingData.getSettingDonate());
                return;
            }
            position--;
        }
        if(position == 0) {
            SettingAgreeViewHolder savh = (SettingAgreeViewHolder)holder;
            savh.setText(MyApplication.getContext().getString(R.string.setting_agree_text));
            return;
        }
        position--;
        if(position == 0){
            SettingLogoutViewHolder slvh = (SettingLogoutViewHolder)holder;
            slvh.setCategory(MyApplication.getContext().getString(R.string.setting_logout_text));
            return;
        }
        throw new IllegalArgumentException("invalid position");
    }

    @Override
    public int getItemCount() {
        int ctn = 0;
        if (settingData == null) return 0;
        if(settingData.getSettingDonate() == null) ctn += 2;
        if(settingData.getSettingSave() == null) ctn += 2;
        return 9-ctn;
    }

    @Override
    public void onExpandClick(View view, int position, boolean expandFlag) {
        if(listener != null){
            listener.onExpandClick(view, position, expandFlag);
        }
    }

    public interface OnSettingAdapterItemClickListener {
        public void onExpandClick(View view, int position, boolean expandFlag);
    }

    OnSettingAdapterItemClickListener listener;

    public void setOnMyDataItemClickListener(OnSettingAdapterItemClickListener listener) {
        this.listener = listener;
    }
}
