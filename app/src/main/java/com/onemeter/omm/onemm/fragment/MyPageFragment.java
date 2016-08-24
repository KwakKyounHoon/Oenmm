package com.onemeter.omm.onemm.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onemeter.omm.onemm.MainActivity;
import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.adapter.MyAdapter;
import com.onemeter.omm.onemm.data.MyData;
import com.onemeter.omm.onemm.data.MyPageData;
import com.onemeter.omm.onemm.data.PostData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyPageFragment extends Fragment {

    @BindView(R.id.list)
    RecyclerView list;
    FragmentManager manager;

    public MyPageFragment() {
        // Required empty public constructor
    }

    MyAdapter mAdatper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_page, container, false);
        ButterKnife.bind(this, view);
        manager = getChildFragmentManager();
        mAdatper = new MyAdapter(manager);
        list.setAdapter(mAdatper);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(manager);
        mAdatper.setOnAdapterItemClickListener(new MyAdapter.OnAdapterItemClickLIstener() {
            @Override
            public void onAdapterDonateClick(View view, MyData myData, int position) {
                ((TabMyFragment)getParentFragment()).showDonate();
            }

            @Override
            public void onAdapterFollowingClick(View view, MyData myData, int position) {
                ((TabMyFragment)getParentFragment()).showFollwing();
            }

            @Override
            public void onAdapterFollowerClick(View view, MyData myData, int position) {
                ((TabMyFragment)getParentFragment()).showFollwer();
            }

            @Override
            public void onAdapterSoundClick(View view, MyData myData, int position) {
            }

            @Override
            public void onAdapterPhotoClick(View view, MyData myData, int position) {

            }

            @Override
            public void onAdapterProfileClick(View view, MyData myData, int position){
                ((TabMyFragment)getParentFragment()).showProfile();
            }
        });


        init();
        return view;
    }

    void init(){
        MyPageData myPageData = new MyPageData();
        MyData myData = new MyData();
        myPageData.setMyData(myData);

        List<PostData> postDatas = new ArrayList<>();
        PostData postData = new PostData();
        postDatas.add(postData);

        myPageData.setPostDatas(postDatas);

        mAdatper.addMyData(myPageData);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) (getActivity())).changeHomeAsUp(false);
    }
}
