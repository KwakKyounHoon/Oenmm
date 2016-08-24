package com.onemeter.omm.onemm.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.onemeter.omm.onemm.MainActivity;
import com.onemeter.omm.onemm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DonatingPlaceFragment extends Fragment {


    public DonatingPlaceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        ((MainActivity) (getActivity())).changeHomeAsUp(true);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_donating_place, container, false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            ((TabMyFragment) (getParentFragment())).popFragment();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
