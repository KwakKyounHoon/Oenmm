package com.onemeter.omm.onemm.fragment;


import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onemeter.omm.onemm.MultiSwipeRefreshLayout;
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

    enum PlayState {
        IDLE,
        INITIALIED,
        PREPARED,
        STARTED,
        PAUSED,
        STOPPED,
        ERROR,
        RELEASED
    }
    PlayState mState = PlayState.STOPPED;

    int pauseTime;

    @BindView(R.id.list)
    RecyclerView list;

    @BindView(R.id.refresh_view)
    MultiSwipeRefreshLayout refreshLayout;

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
        refreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE);
        refreshLayout.setScrollChild(R.id.list);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.clearRankPopular();
                killMediaPlayer();
                mState = PlayState.STOPPED;
                startflag = false;
                mAdapter.setTime("답변 듣기", timePosition);
                if (categoryFlag) {
                    mAdapter.clearRankPopular();
                    PopularPostListRequest request = new PopularPostListRequest(getContext(), 0);
                    NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP, request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                        @Override
                        public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                            mAdapter.addAll(result.getResult());
                            refreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                        }
                    });
                } else {
                    mAdapter.clearRankPopular();
                    PopularPostListRequest request = new PopularPostListRequest(getContext(), 1);
                    NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP, request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                        @Override
                        public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                            mAdapter.addAll(result.getResult());
                            refreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                        }
                    });
                }
            }
        });
        mAdapter.setOnAdapterItemClickListener(new RankPopularAdapter.OnAdapterItemClickLIstener() {
            @Override
            public void onAdapterItemClick(View view, Post post, int position) {
                if (post.getPayInfo().equals("0")) {
                    ((RankFragment) (getParentFragment())).showListenToOff(post, position);
                }
            }

            @Override
            public void onAdapterPlayClick(View view, final Post post, int position) {
                if (post.getPayInfo().equals("1")) {
                    switch (mState) {
                        case STOPPED: {
                            startPlay(post, position);
                            break;
                        }
                        case STARTED: {
                            if (timePosition == position) {
                                startflag = false;
                                mHandler.removeCallbacks(countRunnable);
                                player.pause();
                                pauseTime = count;
                                mState = PlayState.PAUSED;
                            } else {
                                startPlay(post, position);
                            }
                            break;
                        }

                        case PAUSED: {
                            if (timePosition == position) {
                                startflag = true;
                                player.start();
                                endTime = String.valueOf(pauseTime);
                                mState = PlayState.STARTED;
                                startTime = -1;
                                mHandler.removeCallbacks(countRunnable);
                                mHandler.post(countRunnable);
                                break;
                            } else {
                                startPlay(post, position);
                                break;
                            }
                        }
                    }
                } else {
                    ((RankFragment) (getParentFragment())).showListenToOff(post, position);
                }
            }

            @Override
            public void onAdapterCategoryItemClick(Boolean flag, int position) {
                mAdapter.setTime("답변 듣기", position);
                killMediaPlayer();
                startflag = false;
                categoryFlag = flag;
                if (categoryFlag) {
                    mAdapter.clearRankPopular();
                    PopularPostListRequest request = new PopularPostListRequest(getContext(), 0);
                    NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP, request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                        @Override
                        public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                            mAdapter.addAll(result.getResult());
                        }

                        @Override
                        public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                        }
                    });
                } else {
                    mAdapter.clearRankPopular();
                    PopularPostListRequest request = new PopularPostListRequest(getContext(), 1);
                    NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP, request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
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
                if (!post.getAnswernerId().equals(PropertyManager.getInstance().getMyId())) {
                    showOtherPage(post.getAnswernerId());
                } else {
                    showMyPage();
                }
            }

            @Override
            public void onAdapterQuestionerClick(View view, Post post, int position) {
                if (!post.getQuestionerId().equals(PropertyManager.getInstance().getMyId())) {
                    showOtherPage(post.getQuestionerId());
                } else {
                    showMyPage();
                }
            }
        });

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
    int count;
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
                count = endTimeV - gap / 1000;
                int rest = 1000 - gap % 1000;
                if (count > 0) {
//                listenView.("0 : " + count);
                    mAdapter.setTime("0 : " + count, timePosition);
                    mHandler.postDelayed(this, rest);
                } else {
                    mState = PlayState.STOPPED;
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
        mState = PlayState.STOPPED;
        RankFragment fc = (RankFragment) getParentFragment();
        if(fc.getPayPosition() != 0) {
            mAdapter.setPayPosition(fc.getPayPosition());
            fc.setPayPosition(0);
        }
        mAdapter.setFlag(categoryFlag);
        mAdapter.setTime("답변 듣기", timePosition);
    }

    private void startPlay(final Post post, int position){
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
                mState = PlayState.STARTED;
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
