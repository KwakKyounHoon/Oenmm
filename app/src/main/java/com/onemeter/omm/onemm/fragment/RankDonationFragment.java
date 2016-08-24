package com.onemeter.omm.onemm.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onemeter.omm.onemm.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class RankDonationFragment extends Fragment {

    public RankDonationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rank_donation, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @OnClick(R.id.btn_user)
    public void onUserClick(){
        ((RankFragment) (getParentFragment())).showOther();
        Log.i("tag",getParentFragment()+"");
    }


}
