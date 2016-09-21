package com.onemeter.omm.onemm.fragment;


import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onemeter.omm.onemm.MainActivity;
import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.adapter.PostAdapter;
import com.onemeter.omm.onemm.data.NetWorkResultType;
import com.onemeter.omm.onemm.data.Post;
import com.onemeter.omm.onemm.manager.NetworkManager;
import com.onemeter.omm.onemm.manager.NetworkRequest;
import com.onemeter.omm.onemm.manager.PropertyManager;
import com.onemeter.omm.onemm.request.FollowPostListRequest;
import com.onemeter.omm.onemm.request.ReplyListenRequest;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostFragment extends Fragment {

    @BindView(R.id.list)
    RecyclerView list;
    PostAdapter mAdapter;
    boolean isLastItem;
    int pageNo = 1;
    private final int COUNT = 10;
    public PostFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new PostAdapter();
        FollowPostListRequest request = new FollowPostListRequest(getContext(), pageNo, COUNT);
        NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
            @Override
            public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                mAdapter.addAll(result.getResult());
                pageNo++;
            }

            @Override
            public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        ButterKnife.bind(this, view);
        list.setAdapter(mAdapter);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(manager);

        list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(isLastItem && newState == RecyclerView.SCROLL_STATE_IDLE){
                    FollowPostListRequest request = new FollowPostListRequest(getContext(), pageNo, COUNT);
                    NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                        @Override
                        public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                            mAdapter.addAll(result.getResult());
                            pageNo++;
                        }

                        @Override
                        public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                        }
                    });
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = manager.getItemCount();
                int lastVisibleItemPosition = manager.findLastCompletelyVisibleItemPosition();
                if(totalItemCount >0 && lastVisibleItemPosition != RecyclerView.NO_POSITION && (totalItemCount -1 <= lastVisibleItemPosition)){
                    isLastItem = true;
                }else{
                    isLastItem = false;
                }
            }
        });


        mAdapter.setOnAdapterItemClickListener(new PostAdapter.OnAdapterItemClickLIstener() {
            @Override
            public void onAdapterPostItemClick(View view, Post post, int position) {
                if (post.getPayInfo().equals("0")) {
                    ((TabHomeFragment) (getParentFragment())).showListenToOff(post);
                }
            }

            @Override
            public void onAdpaterPlayClick(View view, final Post post, int position) {
                if (post.getPayInfo().equals("1")) {
                    killMediaPlayer();
                    timePosition = position;
                    startTime = -1;
                    ReplyListenRequest request = new ReplyListenRequest(getContext(), post.getAnswerId());
                    NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP, request, new NetworkManager.OnResultListener<NetWorkResultType<String>>() {
                        @Override
                        public void onSuccess(NetworkRequest<NetWorkResultType<String>> request, NetWorkResultType<String> result) {
                            endTime = post.getLength();
                            try {
                                playAudio(result.getResult());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            startflag = true;
                            mHandler.removeCallbacks(countRunnable);
                            mHandler.post(countRunnable);
                        }

                        @Override
                        public void onFail(NetworkRequest<NetWorkResultType<String>> request, int errorCode, String errorMessage, Throwable e) {

                        }
                    });
                }
            }

            @Override
            public void onAdapterAnswerClick(View view, Post post, int position) {
                if(!post.getAnswernerId().equals(PropertyManager.getInstance().getMyId())){
                    showOtherPage(post.getAnswernerId());
                }else{
                    showMyPage();
                }
            }

            @Override
            public void onAdapterQuestionerClick(View view, Post post, int position){
                if(!post.getQuestionerId().equals(PropertyManager.getInstance().getMyId())){
                    showOtherPage(post.getQuestionerId());
                }else{
                    showMyPage();
                }
            }
        });

        return view;
    }

    void init() {
        for (int i = 0; i < 5; i++) {
            Post post = new Post();
            post.setAnswernerId(i + "1");
            post.setAnswernerPhoto("");
            post.setLength(i + "5");
            post.setPrice(i + "10");
            post.setQuestionerContent("GOOD" + i);
            post.setQuestionerId(i + "2");
            post.setQuestionerPhoto("");
            post.setVoiceContent("yes" + i);
            mAdapter.addPost(post);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) (getActivity())).changeHomeAsUp(false);
    }

    MediaPlayer player;

    private void killMediaPlayer() {
        if(player != null){
            try {
                player.release();
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }


    private void playAudio(String url) throws Exception{
        killMediaPlayer();
        player = new MediaPlayer();
        Uri uri = Uri.parse(url);
        player.setDataSource(getContext(), uri, NetworkManager.getInstance().getCookieHeader(url));
        player.prepare();
        player.start();
    }

    long startTime = -1;
    String endTime = "";
    int timePosition;
    boolean startflag = true;
    Handler mHandler = new Handler(Looper.getMainLooper());

    Runnable countRunnable = new Runnable() {
        @Override
        public void run() {
            if (startflag) {
                long time = SystemClock.elapsedRealtime();
                if (startTime == -1) {
                    startTime = time;
                }
                int gap = (int) (time - startTime);
                int endTimeV = Integer.parseInt(endTime);
                int count = endTimeV - gap / 1000;
                int rest = 1000 - gap % 1000;
                if (count > 0) {
                    mAdapter.setTime("0 : " + count, timePosition);
                    mHandler.postDelayed(this, rest);
                } else {
                    killMediaPlayer();
                    mAdapter.setTime("닫변 듣기", timePosition);
                }
            }
        }
    };

    @Override
    public void onStop() {
        super.onStop();
        killMediaPlayer();
        startflag = false;
    }

    private void showOtherPage(String id) {
        ((TabHomeFragment) (getParentFragment())).showOther(id);
    }

    private void showMyPage() {
        ((TabHomeFragment) (getParentFragment())).showMy();
    }
}
