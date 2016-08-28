package com.onemeter.omm.onemm.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onemeter.omm.onemm.MainActivity;
import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.adapter.MyAdapter;
import com.onemeter.omm.onemm.data.MyData;
import com.onemeter.omm.onemm.data.Post;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyPageFragment extends Fragment {

    @BindView(R.id.list)
    RecyclerView list;

    //    public static final int POST_TYPE_RECEIVE_COM = 1;
//    public static final int POST_TYPE_RECEIVE_INCOM = 2;
//    public static final int POST_TYPE_SEND_COM = 3;
//    public static final int POST_TYPE_SEND_INCOM = 4;
//    public static final int POST_TYPE_LISTEN = 5;
//
//    int type;
    int tabType = 1;
    boolean comFlag = true;

    boolean firstFlag = true;

    public MyPageFragment() {
        // Required empty public constructor
    }

    MyAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (firstFlag) {
            mAdapter = new MyAdapter();
            mAdapter.setAdatperPosition(tabType, comFlag);
        }
        firstFlag = false;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_page, container, false);
        ButterKnife.bind(this, view);
        list.setAdapter(mAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(manager);
        mAdapter.setOnAdapterItemClickListener(new MyAdapter.OnAdapterItemClickLIstener() {
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
            public void onAdatperModyfiyClick(View view, MyData myData, int position) {
                ((TabMyFragment)getParentFragment()).showProfile();
            }

            @Override
            public void onAdapterCategory(boolean flag) {
                comFlag = flag;
                if (comFlag){
                    mAdapter.clearPost();
                    init();
                }else{
                    mAdapter.clearPost();
                    init2();
                }
            }

            @Override
            public void onAdapterTabType(View view, int num) {
                tabType = num;
                if(num == 1){
                    mAdapter.clearPost();
                    init();
                }else if(num == 2){
                    mAdapter.clearPost();
                    init2();
                }else if(num == 3){
                    mAdapter.clearPost();
                    init3();
                }

            }

            @Override
            public void onAdapterItemClick(View view, Post post, int position) {

            }

            @Override
            public void onAdapterPlayItemClick(View view, Post post, int position) {

            }


        });

        initInfo();
        mAdapter.clearPost();
        init();
        return view;
    }
    void initInfo(){
        MyData myData = new MyData();
        myData.setDonationName("유니세프");
        myData.setFollower("100");
        myData.setFollowing("140");
        myData.setName("곽견훈");
        myData.setNickname("곽곽곽");
        myData.setStateMessage("시작!");
        myData.setUserId("me");
        myData.setVoiceMessage("good");
        mAdapter.addMyData(myData);
    }

    void init(){
        for(int i = 0; i < 5; i++){
            Post post = new Post();
            post.setAnswernerId(i+"1");
            post.setLength(i+"5");
            post.setPrice(i+"10");
            post.setQuestionerContent("GOOD"+i);
            post.setQuestionerId(i+"2");
            post.setVoiceContent("yes"+i);
            mAdapter.addPost(post);
        }
    }

    void init2(){
        for(int i = 5; i < 11; i++){
            Post post = new Post();
            post.setAnswernerId(i+"1");
            post.setLength(i+"5");
            post.setPrice(i+"10");
            post.setQuestionerContent("GOOD"+i);
            post.setQuestionerId(i+"2");
            post.setVoiceContent("yes"+i);
            mAdapter.addPost(post);
        }
    }

    void init3(){
        for(int i = 12; i < 20; i++){
            Post post = new Post();
            post.setAnswernerId(i+"1");
            post.setLength(i+"5");
            post.setPrice(i+"10");
            post.setQuestionerContent("GOOD"+i);
            post.setQuestionerId(i+"2");
            post.setVoiceContent("yes"+i);
            mAdapter.addPost(post);
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) (getActivity())).changeHomeAsUp(false);
//        Toast.makeText(getActivity(),"ggg",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
