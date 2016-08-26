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

    //    public static final int POST_TYPE_RECEIVE_COM = 1;
//    public static final int POST_TYPE_RECEIVE_INCOM = 2;
//    public static final int POST_TYPE_SEND_COM = 3;
//    public static final int POST_TYPE_SEND_INCOM = 4;
//    public static final int POST_TYPE_LISTEN = 5;
//
//    int type;
    int tabType = 1;
    boolean isCom = true;

    boolean firstFlag = true;

    public MyPageFragment() {
        // Required empty public constructor
    }

    MyAdapter mAdatper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (firstFlag) {
            manager = getChildFragmentManager();
            mAdatper = new MyAdapter(manager);
            mAdatper.setAdatperPosition(tabType, isCom);
        }
        firstFlag = false;

        init1();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_page, container, false);
        ButterKnife.bind(this, view);
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

            @Override
            public void onAdapterCategory(boolean flag) {
                isCom = flag;
                init();
            }

            @Override
            public void onAdapterTabType(View view, int num) {
                tabType = num;
                if(num == 3) isCom = true;
                init();
            }

            @Override
            public void onAdapterPostItemClick(View view, PostData postData, int position) {
                if(!isCom){
                    ((TabMyFragment)getParentFragment()).showReply();
                }
            }


        });

        return view;
    }
    void init1(){
        MyPageData myPageData = new MyPageData();
        MyData myData = new MyData();
        myPageData.setMyData(myData);

        List<PostData> postDatas = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            PostData postData = new PostData();
            postData.setName("받은 질문, 답변 완료 "+i);
            postDatas.add(postData);
        }
        myPageData.setPostDatas(postDatas);
        mAdatper.addMyData(myPageData);
    }

    void  init(){
        if(tabType == 1){
            if(isCom){
                MyPageData myPageData = new MyPageData();
                MyData myData = new MyData();
                myPageData.setMyData(myData);

                List<PostData> postDatas = new ArrayList<>();
                for(int i = 0; i < 5; i++){
                    PostData postData = new PostData();
                    postData.setName("받은 질문, 답변 완료 "+i);
                    postDatas.add(postData);
                }
                myPageData.setPostDatas(postDatas);
                mAdatper.addMyData(myPageData);
            }else{
                MyPageData myPageData = new MyPageData();
                MyData myData = new MyData();
                myPageData.setMyData(myData);

                List<PostData> postDatas = new ArrayList<>();
                for(int i = 0; i < 5; i++){
                    PostData postData = new PostData();
                    postData.setName("받은 질문, 답변 미완료 "+i);
                    postDatas.add(postData);
                }
                myPageData.setPostDatas(postDatas);
                mAdatper.addMyData(myPageData);
            }
        }else if(tabType == 2){
            if(isCom){
                MyPageData myPageData = new MyPageData();
                MyData myData = new MyData();
                myPageData.setMyData(myData);

                List<PostData> postDatas = new ArrayList<>();
                for(int i = 0; i < 5; i++){
                    PostData postData = new PostData();
                    postData.setName("보낸 질문, 답변 완료 "+i);
                    postDatas.add(postData);
                }
                myPageData.setPostDatas(postDatas);
                mAdatper.addMyData(myPageData);
            }else{
                MyPageData myPageData = new MyPageData();
                MyData myData = new MyData();
                myPageData.setMyData(myData);

                List<PostData> postDatas = new ArrayList<>();
                for(int i = 0; i < 5; i++){
                    PostData postData = new PostData();
                    postData.setName("보낸 질문, 답변 미완료 "+i);
                    postDatas.add(postData);
                }
                myPageData.setPostDatas(postDatas);
                mAdatper.addMyData(myPageData);
            }
        }else if(tabType == 3){
            MyPageData myPageData = new MyPageData();
            MyData myData = new MyData();
            myPageData.setMyData(myData);

            List<PostData> postDatas = new ArrayList<>();
            for(int i = 0; i < 5; i++){
                PostData postData = new PostData();
                postData.setName("나도 듣기 보관함"+i);
                postDatas.add(postData);
            }
            myPageData.setPostDatas(postDatas);
            mAdatper.addMyData(myPageData);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) (getActivity())).changeHomeAsUp(false);
//        Toast.makeText(getActivity(),"ggg",Toast.LENGTH_SHORT).show();
//        Log.i("MyPage",tabType + " " + isCom + "");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
