package com.onemeter.omm.onemm.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.adapter.OtherAdapter;
import com.onemeter.omm.onemm.data.OtherData;
import com.onemeter.omm.onemm.data.OtherPageData;
import com.onemeter.omm.onemm.data.PostData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class OtherFragment extends Fragment {


    @BindView(R.id.list)
    RecyclerView list;
    FragmentManager manager;

    public OtherFragment() {
        // Required empty public constructor
    }
    OtherAdapter mAdatper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_other, container, false);
        ButterKnife.bind(this, view);
        manager = getChildFragmentManager();
        mAdatper = new OtherAdapter(manager);
        list.setAdapter(mAdatper);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(manager);
        mAdatper.setOnAdapterItemClickListener(new OtherAdapter.OnAdapterItemClickLIstener() {

            @Override
            public void onAdapterDonateClick(View view, OtherData otherData, int position) {

            }

            @Override
            public void onAdapterFollowingClick(View view, OtherData otherData, int position) {

            }

            @Override
            public void onAdapterFollowerClick(View view, OtherData otherData, int position) {

            }

            @Override
            public void onAdapterSoundClick(View view, OtherData otherData, int position) {

            }

            @Override
            public void onAdapterPhotoClick(View view, OtherData otherData, int position) {

            }

            @Override
            public void onAdapterProfileClick(View view, OtherData otherData, int position) {

            }
        });

        init();
        return view;
    }

    void init(){
        OtherPageData otherPageData = new OtherPageData();
        OtherData otherData = new OtherData();
        otherPageData.setOtherData(otherData);

        List<PostData> postDatas = new ArrayList<>();
        PostData postData = new PostData();
        postDatas.add(postData);

        otherPageData.setPostDatas(postDatas);

        mAdatper.addOtherData(otherPageData);
    }

}
