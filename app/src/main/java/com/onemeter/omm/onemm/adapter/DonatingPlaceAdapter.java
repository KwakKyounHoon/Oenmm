package com.onemeter.omm.onemm.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.DonatingPlace;
import com.onemeter.omm.onemm.data.DonatingPlaces;
import com.onemeter.omm.onemm.viewholder.DonatingPlaceHeaderViewHolder;
import com.onemeter.omm.onemm.viewholder.DonatingPlaceViewHolder;

import java.util.Arrays;

/**
 * Created by Tacademy on 2016-09-01.
 */
public class DonatingPlaceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    DonatingPlaces donatingPlaces = new DonatingPlaces();

    public static final int VIEW_TYPE_HEADER = 1;
    public static final int VIEW_TYPE_CONTENT = 2;

    public boolean isHead() {
        if(donatingPlaces.getHeaderDonatingPlace() != null) return true;
        else return false;
    }

    public void addHeadPlace(DonatingPlace donatingPlace){
        this.donatingPlaces.setHeaderDonatingPlace(donatingPlace);
        notifyDataSetChanged();
    }

    public void addHeadPlace(DonatingPlace[] donatingPlace){
        this.donatingPlaces.setHeaderDonatingPlace(donatingPlace[0]);
        notifyDataSetChanged();
    }

    public void addPlaces(DonatingPlace[] donatingPlaces){
        this.donatingPlaces.setDonatingPlaceList(Arrays.asList(donatingPlaces));
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (donatingPlaces.getHeaderDonatingPlace() != null){
            if (position == 0) return VIEW_TYPE_HEADER;
            position--;
        }
        if (donatingPlaces.getDonatingPlaceList().size() > 0) {
            if (position < donatingPlaces.getDonatingPlaceList().size()) {
                return VIEW_TYPE_CONTENT;
            }
            position -= donatingPlaces.getDonatingPlaceList().size();
        }
        throw new IllegalArgumentException("invalid position");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case VIEW_TYPE_HEADER : {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_donating_place_header,parent, false);
                return new DonatingPlaceHeaderViewHolder(view);
            }
            case VIEW_TYPE_CONTENT : {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_donating_place,parent,false);
                return new DonatingPlaceViewHolder(view);
            }
        }
        throw new IllegalArgumentException("invalid position");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (donatingPlaces.getHeaderDonatingPlace() != null){
            if (position == 0) {
                DonatingPlaceHeaderViewHolder dphvh = (DonatingPlaceHeaderViewHolder)holder;
                dphvh.setDPHeader(donatingPlaces.getHeaderDonatingPlace());
                return;
            }
            position--;
        }
        if (donatingPlaces.getDonatingPlaceList().size() > 0) {
            if (position < donatingPlaces.getDonatingPlaceList().size()) {
                DonatingPlaceViewHolder dpvh = (DonatingPlaceViewHolder)holder;
                dpvh.setDPItem(donatingPlaces.getDonatingPlaceList().get(position));
                return;
            }
            position -= donatingPlaces.getDonatingPlaceList().size();
        }
        throw new IllegalArgumentException("invalid position");
    }

    @Override
    public int getItemCount() {
        int ctn = 1;
        if(donatingPlaces.getDonatingPlaceList().size() == 0 && donatingPlaces.getHeaderDonatingPlace() == null) return 0;
        if(donatingPlaces.getHeaderDonatingPlace() == null) ctn--;
        if(donatingPlaces.getDonatingPlaceList().size() == 0) return ctn;
        return ctn + donatingPlaces.getDonatingPlaceList().size();
    }

}
