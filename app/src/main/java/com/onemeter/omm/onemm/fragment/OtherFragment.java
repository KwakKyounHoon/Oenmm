package com.onemeter.omm.onemm.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.adapter.OtherAdapter;
import com.onemeter.omm.onemm.data.NetWorkResultType;
import com.onemeter.omm.onemm.data.OtherData;
import com.onemeter.omm.onemm.data.Post;
import com.onemeter.omm.onemm.manager.NetworkManager;
import com.onemeter.omm.onemm.manager.NetworkRequest;
import com.onemeter.omm.onemm.request.OtherDataRequest;
import com.onemeter.omm.onemm.request.OtherPostRequest;

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

    @BindView(R.id.btn_back)
    ImageView backView;

    public OtherFragment() {
        // Required empty public constructor
    }
    OtherAdapter mAdapter;

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
        mAdapter = new OtherAdapter();
        list.setAdapter(mAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(manager);
        mAdapter.setOnAdapterItemClickListener(new OtherAdapter.OnAdapterItemClickLIstener() {
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

            @Override
            public void onAdapterCategoryItemClick(boolean flag) {
                if(flag){
                    mAdapter.clearPost();
                    init();
                }else{
                    mAdapter.clearPost();
                    init2();
                }
            }

            @Override
            public void onAdapterItemClick(View view, Post post, int position) {
                Toast.makeText(getContext(),post.getAnswernerId(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterPlayClick(View view, Post post, int position) {
                Toast.makeText(getContext(),post.getVoiceContent(),Toast.LENGTH_SHORT).show();
            }
        });

//        initOtherInfo();
        OtherDataRequest request = new OtherDataRequest(getContext(), id);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetWorkResultType<OtherData[]>>() {
            @Override
            public void onSuccess(NetworkRequest<NetWorkResultType<OtherData[]>> request, NetWorkResultType<OtherData[]> result) {
                mAdapter.addOtherData(result.getResult());
            }

            @Override
            public void onFail(NetworkRequest<NetWorkResultType<OtherData[]>> request, int errorCode, String errorMessage, Throwable e) {

            }
        });

        OtherPostRequest otherPostRequest = new OtherPostRequest(getContext(),id,"from","0","20","1");
        NetworkManager.getInstance().getNetworkData(otherPostRequest, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
            @Override
            public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                mAdapter.addAllPost(result.getResult());
            }

            @Override
            public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

            }
        });
        return view;
    }

    void init(){
        for(int i = 0; i < 5; i++){
            Post post = new Post();
            post.setAnswernerId(i+"1");
            post.setAnswernerPhoto("");
            post.setLength(i+"5");
            post.setPrice(i+"10");
            post.setQuestionerContent("GOOD"+i);
            post.setQuestionerId(i+"2");
            post.setQuestionerPhoto("");
            post.setVoiceContent("yes"+i);
            mAdapter.addPost(post);
        }
    }

    void init2(){
        for(int i = 5; i < 11; i++){
            Post post = new Post();
            post.setAnswernerId(i+"1");
            post.setAnswernerPhoto("");
            post.setLength(i+"5");
            post.setPrice(i+"10");
            post.setQuestionerContent("GOOD"+i);
            post.setQuestionerId(i+"2");
            post.setQuestionerPhoto("");
            post.setVoiceContent("yes"+i);
            mAdapter.addPost(post);
        }
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
