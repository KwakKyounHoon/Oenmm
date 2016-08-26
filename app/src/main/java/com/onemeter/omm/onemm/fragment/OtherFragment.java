package com.onemeter.omm.onemm.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.adapter.OtherAdapter;
import com.onemeter.omm.onemm.data.OtherData;
import com.onemeter.omm.onemm.data.OtherPageData;
import com.onemeter.omm.onemm.data.PostData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class OtherFragment extends Fragment {

    public static String OTHER_ID = "id";
    private String id;


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.list)
    RecyclerView list;
    FragmentManager manager;

    @BindView(R.id.btn_back)
    ImageView backView;

    public OtherFragment() {
        // Required empty public constructor
    }
    OtherAdapter mAdatper;

    public static OtherFragment newInstance(String message) {
        OtherFragment fragment = new OtherFragment();
        Bundle args = new Bundle();
        args.putString(OTHER_ID, message);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString(OTHER_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_other, container, false);
        ButterKnife.bind(this, view);

        setHasOptionsMenu(true);
//        changeHomeAsUp(true);
        manager = getChildFragmentManager();
        mAdatper = new OtherAdapter(manager);
        list.setAdapter(mAdatper);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(manager);
        mAdatper.setOnAdapterItemClickListener(new OtherAdapter.OnAdapterItemClickLIstener() {
            @Override
            public void onAdapterFollowingClick(View view, OtherData otherData, int position) {
                if(getParentFragment() instanceof TabMyFragment){
                    ((TabMyFragment) (getParentFragment())).showFollwing();
                }else if(getParentFragment() instanceof TabHomeFragment){
                    ((TabHomeFragment)getParentFragment()).showFollwing();
                }else if(getParentFragment() instanceof TabRankFragment){
                    ((TabRankFragment) (getParentFragment())).showFollwing();
                }else{
                    ((TabSearchFragment) (getParentFragment())).showFollwing();
                }
            }

            @Override
            public void onAdapterFollowerClick(View view, OtherData otherData, int position) {

                if(getParentFragment() instanceof TabMyFragment){
                    ((TabMyFragment) (getParentFragment())).showFollwer();
                }else if(getParentFragment() instanceof TabHomeFragment){
                    ((TabHomeFragment)getParentFragment()).showFollwer();
                }else if(getParentFragment() instanceof TabRankFragment){
                    ((TabRankFragment) (getParentFragment())).showFollwer();
                }else{
                    ((TabSearchFragment) (getParentFragment())).showFollwer();
                }
            }

            @Override
            public void onAdapterSoundClick(View view, OtherData otherData, int position) {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if(getParentFragment() instanceof TabMyFragment){
                ((TabMyFragment) (getParentFragment())).popFragment();
            }else if(getParentFragment() instanceof TabHomeFragment){
                ((TabHomeFragment) (getParentFragment())).popFragment();
            }else if(getParentFragment() instanceof TabRankFragment){
                ((TabRankFragment) (getParentFragment())).popFragment();
            }else{
                ((TabSearchFragment) (getParentFragment())).popFragment();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_back)
    public void backClick(View view){
        if(getParentFragment() instanceof TabMyFragment){
            ((TabMyFragment) (getParentFragment())).popFragment();
        }else if(getParentFragment() instanceof TabHomeFragment){
            ((TabHomeFragment) (getParentFragment())).popFragment();
        }else if(getParentFragment() instanceof TabRankFragment){
            ((TabRankFragment) (getParentFragment())).popFragment();
        }else{
            ((TabSearchFragment) (getParentFragment())).popFragment();
        }
    }

}
