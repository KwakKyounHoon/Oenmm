package com.onemeter.omm.onemm.fragment;


import android.media.MediaPlayer;
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

import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.adapter.RankPopularAdapter;
import com.onemeter.omm.onemm.data.NetWorkResultType;
import com.onemeter.omm.onemm.data.Post;
import com.onemeter.omm.onemm.manager.NetworkManager;
import com.onemeter.omm.onemm.manager.NetworkRequest;
import com.onemeter.omm.onemm.manager.PropertyManager;
import com.onemeter.omm.onemm.request.PopularPostListRequest;
import com.onemeter.omm.onemm.request.ReplyListenRequest;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RankPopularFragment extends Fragment {

    @BindView(R.id.list)
    RecyclerView list;

    RankPopularAdapter mAdapter;

    Boolean categoryFlag = true;

    public RankPopularFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new RankPopularAdapter();
        PopularPostListRequest request = new PopularPostListRequest(getContext(), 0);
        NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
            @Override
            public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                mAdapter.addAll(result.getResult());
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
        View view = inflater.inflate(R.layout.fragment_rank_popular, container, false);
        ButterKnife.bind(this,view);
        list.setAdapter(mAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(manager);

        mAdapter.setOnAdapterItemClickListener(new RankPopularAdapter.OnAdapterItemClickLIstener() {
            @Override
            public void onAdapterItemClick(View view, Post post, int position) {
                if(post.getPayInfo().equals("0")){
                    ((RankFragment) (getParentFragment())).showListenToOff(post);
                }
            }

            @Override
            public void onAdapterPlayClick(View view, final Post rankPopular, int position) {
                if (rankPopular.getPayInfo().equals("1")) {
                    timePosition = position;
                    startTime = -1;
                    ReplyListenRequest request = new ReplyListenRequest(getContext(), rankPopular.getAnswerId());
                    NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP, request, new NetworkManager.OnResultListener<NetWorkResultType<String>>() {
                        @Override
                        public void onSuccess(NetworkRequest<NetWorkResultType<String>> request, NetWorkResultType<String> result) {
                            endTime = rankPopular.getLength();
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
            public void onAdapterCategoryItemClick(Boolean flag, int position) {
                mAdapter.setTime("답변 듣기", position);
                killMediaPlayer();
                startflag = false;
                categoryFlag = flag;
                if(categoryFlag){
                    mAdapter.clearRankPopular();
                    PopularPostListRequest request = new PopularPostListRequest(getContext(), 0);
                    NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                        @Override
                        public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                            mAdapter.addAll(result.getResult());
                        }

                        @Override
                        public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                        }
                    });
                }else{
                    mAdapter.clearRankPopular();
                    PopularPostListRequest request = new PopularPostListRequest(getContext(), 1);
                    NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                        @Override
                        public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                            mAdapter.addAll(result.getResult());
                        }

                        @Override
                        public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

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
//        PopularPostListRequest request = new PopularPostListRequest(getContext(), 0);
//        NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
//            @Override
//            public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
//                mAdapter.addAll(result.getResult());
//            }
//
//            @Override
//            public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {
//
//            }
//        });

        return view;
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
//                listenView.("0 : " + count);
                    mAdapter.setTime("0 : " + count, timePosition);
                    mHandler.postDelayed(this, rest);
                } else {
                    killMediaPlayer();
                    mAdapter.setTime("닫변 듣기", timePosition);
                }
            }
        }
    };

    boolean startflag = true;

    @Override
    public void onStop() {
        super.onStop();
        killMediaPlayer();
        startflag = false;
    }

    private void showOtherPage(String id) {
        ((RankFragment) (getParentFragment())).showOther(id);
    }

    private void showMyPage() {
        ((RankFragment) (getParentFragment())).showMy();
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.setFlag(categoryFlag);
    }
}
