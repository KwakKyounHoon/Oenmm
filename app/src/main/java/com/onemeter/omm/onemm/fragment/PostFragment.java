package com.onemeter.omm.onemm.fragment;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.onemeter.omm.onemm.MainActivity;
import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.adapter.PostAdapter;
import com.onemeter.omm.onemm.data.NetWorkResultType;
import com.onemeter.omm.onemm.data.Post;
import com.onemeter.omm.onemm.manager.NetworkManager;
import com.onemeter.omm.onemm.manager.NetworkRequest;
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

    public PostFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        ButterKnife.bind(this, view);
        mAdapter = new PostAdapter();
        list.setAdapter(mAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(manager);
        mAdapter.setOnAdapterItemClickListener(new PostAdapter.OnAdapterItemClickLIstener() {
            @Override
            public void onAdapterPostItemClick(View view, Post post, int position) {
                Toast.makeText(getContext(), post.getAnswernerId(), Toast.LENGTH_SHORT).show();
                if (post.getPayInfo().equals("0")) {
                    ((TabHomeFragment) (getParentFragment())).showListenToOff(post);
                }
            }

            @Override
            public void onAdpaterPlayClick(View view, final Post post, int position) {
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
                        mHandler.removeCallbacks(countRunnable);
                        mHandler.post(countRunnable);
                    }

                    @Override
                    public void onFail(NetworkRequest<NetWorkResultType<String>> request, int errorCode, String errorMessage, Throwable e) {

                    }
                });
            }
        });

//        init();
        FollowPostListRequest request = new FollowPostListRequest(getContext(), 1, 20);
        NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
            @Override
            public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                mAdapter.addAll(result.getResult());
            }

            @Override
            public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

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
        player.setDataSource(url);
        player.prepare();
        player.start();
    }

    long startTime = -1;
    String endTime = "";
    int timePosition;
    Handler mHandler = new Handler(Looper.getMainLooper());

    Runnable countRunnable = new Runnable() {
        @Override
        public void run() {
            long time = SystemClock.elapsedRealtime();
            if (startTime == -1) {
                startTime = time;
            }
            int gap = (int) (time - startTime);
            int endTimeV = Integer.parseInt(endTime);
            int count = endTimeV - gap / 1000;
            int rest = 1000 - gap % 1000;
            if (count > 0) {
//                listenView.("0 : " + count);
                mAdapter.setTime("0 : " + count, timePosition);
                mHandler.postDelayed(this, rest);
            }else{
                killMediaPlayer();
                mAdapter.setTime("닫변 듣기", timePosition);
            }
        }
    };
}
