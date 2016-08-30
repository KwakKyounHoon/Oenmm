package com.onemeter.omm.onemm.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.onemeter.omm.onemm.MainActivity;
import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.adapter.MyAdapter;
import com.onemeter.omm.onemm.data.MyData;
import com.onemeter.omm.onemm.data.NetWorkResultType;
import com.onemeter.omm.onemm.data.Post;
import com.onemeter.omm.onemm.manager.NetworkManager;
import com.onemeter.omm.onemm.manager.NetworkRequest;
import com.onemeter.omm.onemm.request.MyDataReqeust;
import com.onemeter.omm.onemm.request.MyPostRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyPageFragment extends Fragment {

    @BindView(R.id.list)
    RecyclerView list;

    public static final String POST_TYPE_RECEIVE = "from";
    public static final String POST_TYPE_INCOM = "0";
    public static final String POST_TYPE_SEND = "to";
    public static final String POST_TYPE_COM = "1";
    public static final String POST_TYPE_LISTEN = "3";

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
//                    init();
                    switch (tabType){
                        case 1: {
                            MyPostRequest request = new MyPostRequest(getContext(), "from", "1", "1", "20");
                            NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                                @Override
                                public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                                    mAdapter.addAllPost(result.getResult());
                                }

                                @Override
                                public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                                }
                            });
                        }
                        break;
                        case 2: {
                            MyPostRequest request = new MyPostRequest(getContext(), "to", "1", "1", "20");
                            NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                                @Override
                                public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                                    mAdapter.addAllPost(result.getResult());
                                }

                                @Override
                                public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                                }
                            });
                        }
                        break;
                    }
                }else{
                    mAdapter.clearPost();
//                    init2();
                    switch (tabType){
                        case 1: {
                            MyPostRequest request = new MyPostRequest(getContext(), "from", "0", "1", "20");
                            NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                                @Override
                                public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                                    mAdapter.addAllPost(result.getResult());
                                }

                                @Override
                                public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                                }
                            });
                        }
                        break;
                        case 2: {
                            MyPostRequest request = new MyPostRequest(getContext(), "to", "0", "1", "20");
                            NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                                @Override
                                public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                                    mAdapter.addAllPost(result.getResult());
                                }

                                @Override
                                public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                                }
                            });
                        }
                        break;
                    }
                }
            }

            @Override
            public void onAdapterTabType(View view, int num) {
                tabType = num;
                if(num == 1){
                    mAdapter.clearPost();
                    if(comFlag){
                        MyPostRequest request = new MyPostRequest(getContext(), "to", "1", "1", "20");
                        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                            @Override
                            public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                                mAdapter.addAllPost(result.getResult());
                            }

                            @Override
                            public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                            }
                        });
                    }else{
                        MyPostRequest request = new MyPostRequest(getContext(), "to", "0", "1", "20");
                        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                            @Override
                            public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                                mAdapter.addAllPost(result.getResult());
                            }

                            @Override
                            public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                            }
                        });
                    }
                }else if(num == 2){
                    mAdapter.clearPost();
                    if(comFlag){
                        MyPostRequest request = new MyPostRequest(getContext(), "from", "1", "1", "20");
                        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                            @Override
                            public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                                mAdapter.addAllPost(result.getResult());
                            }

                            @Override
                            public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                            }
                        });
                    }else{
                        MyPostRequest request = new MyPostRequest(getContext(), "from", "0", "1", "20");
                        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                            @Override
                            public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                                mAdapter.addAllPost(result.getResult());
                            }

                            @Override
                            public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                            }
                        });
                    }
                }else if(num == 3){
                    mAdapter.clearPost();
                    MyPostRequest request = new MyPostRequest(getContext(), "to", "0", "1", "20");
                    NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                        @Override
                        public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                            mAdapter.addAllPost(result.getResult());
                        }

                        @Override
                        public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                        }
                    });
                }

            }

            @Override
            public void onAdapterItemClick(View view, Post post, int position) {

            }

            @Override
            public void onAdapterPlayItemClick(View view, Post post, int position) {

            }


        });

        MyDataReqeust reqeust = new MyDataReqeust(getContext());
        NetworkManager.getInstance().getNetworkData(reqeust, new NetworkManager.OnResultListener<NetWorkResultType<MyData[]>>() {
            @Override
            public void onSuccess(NetworkRequest<NetWorkResultType<MyData[]>> request, NetWorkResultType<MyData[]> result) {
                mAdapter.addMyData(result.getResult());
            }

            @Override
            public void onFail(NetworkRequest<NetWorkResultType<MyData[]>> request, int errorCode, String errorMessage, Throwable e) {
                Toast.makeText(getContext(),errorCode+", "+errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
        mAdapter.clearPost();
        MyPostRequest request = new MyPostRequest(getContext(), "from", "1", "1", "20");
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
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
    @OnClick(R.id.btn_setting)
    public void settingClick(View view){
        ((TabMyFragment)getParentFragment()).showSetting();
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
