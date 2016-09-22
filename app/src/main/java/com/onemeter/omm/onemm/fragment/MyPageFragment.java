package com.onemeter.omm.onemm.fragment;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.onemeter.omm.onemm.MultiSwipeRefreshLayout;
import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.adapter.MyAdapter;
import com.onemeter.omm.onemm.data.MyData;
import com.onemeter.omm.onemm.data.NetWorkResultType;
import com.onemeter.omm.onemm.data.Post;
import com.onemeter.omm.onemm.manager.NetworkManager;
import com.onemeter.omm.onemm.manager.NetworkRequest;
import com.onemeter.omm.onemm.request.ChangeImageRequest;
import com.onemeter.omm.onemm.request.MyDataReqeust;
import com.onemeter.omm.onemm.request.MyListenRequest;
import com.onemeter.omm.onemm.request.MyPostRequest;
import com.onemeter.omm.onemm.request.ProfileVoiceRequest;
import com.onemeter.omm.onemm.request.RemoveImageRequest;
import com.onemeter.omm.onemm.request.ReplyListenRequest;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyPageFragment extends Fragment {

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

    @BindView(R.id.list)
    RecyclerView list;

    @BindView(R.id.text_nickname)
    TextView nickNameView;

    MultiSwipeRefreshLayout refreshLayout;

    public static final String POST_TYPE_RECEIVE = "from";
    public static final String POST_TYPE_INCOM = "0";
    public static final String POST_TYPE_SEND = "to";
    public static final String POST_TYPE_COM = "1";
    public static final String POST_TYPE_LISTEN = "3";

    boolean isLastItem;
    int pageNo = 1;
    private final int COUNT = 10;

    MediaPlayer player;
    int pauseTime;

    int tabType = 1;
    boolean comFlag = true;

    public MyPageFragment() {
        // Required empty public constructor
    }

    MyAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new MyAdapter();

        MyPostRequest request = new MyPostRequest(getContext(), "from", "1", pageNo, COUNT);
        NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
            @Override
            public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                mAdapter.addAllPost(result.getResult());
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
        View view = inflater.inflate(R.layout.fragment_my_page, container, false);
        ButterKnife.bind(this, view);
        refreshLayout = (MultiSwipeRefreshLayout)view.findViewById(R.id.refresh_view);
        refreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE);
        refreshLayout.setScrollChild(R.id.list);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.setHeaderPlayInfo(false);
                killMediaPlayer();
                startflag = false;
                mState = PlayState.STOPPED;
                mAdapter.setTime("답변 듣기", timePosition);
                mAdapter.clearPost();
                pageNo = 1;
                MyDataReqeust reqeust = new MyDataReqeust(getContext());
                NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,reqeust, new NetworkManager.OnResultListener<NetWorkResultType<MyData>>() {
                    @Override
                    public void onSuccess(NetworkRequest<NetWorkResultType<MyData>> request, NetWorkResultType<MyData> result) {
                        String nickName = "User NickName";
                        if(result.getResult().getNickname() != null) {
                            nickName = result.getResult().getNickname();
                        }
                        mAdapter.addMyData(result.getResult());
                        nickNameView.setText(nickName);
                    }
                    @Override
                    public void onFail(NetworkRequest<NetWorkResultType<MyData>> request, int errorCode, String errorMessage, Throwable e) {

                    }
                });
                if (tabType == 1) {
                    if (comFlag) {
                        MyPostRequest request = new MyPostRequest(getContext(), "from", "1", pageNo, COUNT);
                        NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                            @Override
                            public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                                mAdapter.addAllPost(result.getResult());
                                pageNo++;
                                refreshLayout.setRefreshing(false);
                            }

                            @Override
                            public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                            }
                        });
                    } else {
                        MyPostRequest request = new MyPostRequest(getContext(), "from", "0", pageNo, COUNT);
                        NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                            @Override
                            public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                                mAdapter.addAllPost(result.getResult());
                                pageNo++;
                                refreshLayout.setRefreshing(false);
                            }

                            @Override
                            public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                            }
                        });
                    }
                } else if (tabType == 2) {
                    if (comFlag) {
                        MyPostRequest request = new MyPostRequest(getContext(), "to", "1", pageNo, COUNT);
                        NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                            @Override
                            public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                                mAdapter.addAllPost(result.getResult());
                                pageNo++;
                                refreshLayout.setRefreshing(false);
                            }

                            @Override
                            public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                            }
                        });
                    } else {
                        MyPostRequest request = new MyPostRequest(getContext(), "to", "0", pageNo, COUNT);
                        NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                            @Override
                            public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                                mAdapter.addAllPost(result.getResult());
                                pageNo++;
                                refreshLayout.setRefreshing(false);
                            }

                            @Override
                            public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                            }
                        });
                    }
                } else if (tabType == 3) {
                    MyListenRequest request = new MyListenRequest(getContext(), pageNo, COUNT);
                    NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                        @Override
                        public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                            mAdapter.addAllPost(result.getResult());
                            pageNo++;
                            refreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                        }
                    });
                }
            }
        });
        list.setAdapter(mAdapter);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(manager);

        list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(isLastItem && newState == RecyclerView.SCROLL_STATE_IDLE){
                    if (tabType == 1) {
                        if (comFlag) {
                            MyPostRequest request = new MyPostRequest(getContext(), "from", "1", pageNo, COUNT);
                            NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                                @Override
                                public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                                    mAdapter.addAllPost(result.getResult());
                                    pageNo++;
                                }

                                @Override
                                public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                                }
                            });
                        } else {
                            MyPostRequest request = new MyPostRequest(getContext(), "from", "0", pageNo, COUNT);
                            NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                                @Override
                                public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                                    mAdapter.addAllPost(result.getResult());
                                    pageNo++;
                                }

                                @Override
                                public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                                }
                            });
                        }
                    } else if (tabType == 2) {
                        if (comFlag) {
                            MyPostRequest request = new MyPostRequest(getContext(), "to", "1", pageNo, COUNT);
                            NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                                @Override
                                public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                                    mAdapter.addAllPost(result.getResult());
                                    pageNo++;
                                }

                                @Override
                                public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                                }
                            });
                        } else {
                            MyPostRequest request = new MyPostRequest(getContext(), "to", "0", pageNo, COUNT);
                            NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                                @Override
                                public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                                    mAdapter.addAllPost(result.getResult());
                                    pageNo++;
                                }

                                @Override
                                public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                                }
                            });
                        }
                    } else if (tabType == 3) {
                        MyListenRequest request = new MyListenRequest(getContext(), pageNo, COUNT);
                        NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                            @Override
                            public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                                mAdapter.addAllPost(result.getResult());
                                pageNo++;
                            }

                            @Override
                            public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                            }
                        });
                    }
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

        mAdapter.setOnAdapterItemClickListener(new MyAdapter.OnAdapterItemClickLIstener() {
            @Override
            public void onAdapterDonateClick(View view, MyData myData, int position) {
                showDonatingPlace(myData.getDonationId());
            }

            @Override
            public void onAdapterFollowingClick(View view, MyData myData, int position) {
                showFollowing("-1");
            }

            @Override
            public void onAdapterFollowerClick(View view, MyData myData, int position) {
                showFollower("-1");
            }

            @Override
            public void onAdapterSoundClick(View view, MyData myData, int position) {
                ProfileVoiceRequest request = new ProfileVoiceRequest(getContext(), "me");
                NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP, request, new NetworkManager.OnResultListener<NetWorkResultType<String>>() {
                    @Override
                    public void onSuccess(NetworkRequest<NetWorkResultType<String>> request, NetWorkResultType<String> result) {
                        try {
                            playAudio(result.getResult());
                            mAdapter.setHeaderPlayInfo(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(NetworkRequest<NetWorkResultType<String>> request, int errorCode, String errorMessage, Throwable e) {

                    }
                });
            }

            @Override
            public void onAdapterPhotoClick(View view, MyData myData, int position) {
                onListDialog();

            }

            @Override
            public void onAdatperModyfiyClick(View view, MyData myData, int position) {
                showModifyProfile(myData);
            }

            @Override
            public void onAdapterCategory(boolean flag, int position) {
                mAdapter.setHeaderPlayInfo(false);
                pageNo = 1;
                mAdapter.setTime("답변 듣기", position);
                killMediaPlayer();
                startflag = false;
                comFlag = flag;
                if (comFlag) {
                    mAdapter.clearPost();
                    switch (tabType) {
                        case 1: {
                            MyPostRequest request = new MyPostRequest(getContext(), "from", "1", pageNo, COUNT);
                            NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                                @Override
                                public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                                    mAdapter.addAllPost(result.getResult());
                                    pageNo++;
                                }

                                @Override
                                public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                                }
                            });
                        }
                        break;
                        case 2: {
                            MyPostRequest request = new MyPostRequest(getContext(), "to", "1", pageNo, COUNT);
                            NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                                @Override
                                public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                                    mAdapter.addAllPost(result.getResult());
                                    pageNo++;
                                }

                                @Override
                                public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                                }
                            });
                        }
                        break;
                    }
                } else {
                    mAdapter.clearPost();
                    switch (tabType) {
                        case 1: {
                            MyPostRequest request = new MyPostRequest(getContext(), "from", "0", pageNo, COUNT);
                            NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                                @Override
                                public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                                    mAdapter.addAllPost(result.getResult());
                                    pageNo++;
                                }

                                @Override
                                public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                                }
                            });
                        }
                        break;
                        case 2: {
                            MyPostRequest request = new MyPostRequest(getContext(), "to", "0", pageNo, COUNT);
                            NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                                @Override
                                public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                                    mAdapter.addAllPost(result.getResult());
                                    pageNo++;
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
            public void onAdapterTabType(View view, int num, int position) {
                mAdapter.setHeaderPlayInfo(false);
                pageNo = 1;
                mAdapter.setTime("답변 듣기", position);
                killMediaPlayer();
                startflag = false;
                tabType = num;
                if (num == 1) {
                    mAdapter.clearPost();
                    if (comFlag) {
                        MyPostRequest request = new MyPostRequest(getContext(), "from", "1", pageNo, COUNT);
                        NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                            @Override
                            public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                                mAdapter.addAllPost(result.getResult());
                                pageNo++;
                            }

                            @Override
                            public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                            }
                        });
                    } else {
                        MyPostRequest request = new MyPostRequest(getContext(), "from", "0", pageNo, COUNT);
                        NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                            @Override
                            public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                                mAdapter.addAllPost(result.getResult());
                                pageNo++;
                            }

                            @Override
                            public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                            }
                        });
                    }
                } else if (num == 2) {
                    mAdapter.clearPost();
                    if (comFlag) {
                        MyPostRequest request = new MyPostRequest(getContext(), "to", "1", pageNo, COUNT);
                        NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                            @Override
                            public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                                mAdapter.addAllPost(result.getResult());
                                pageNo++;
                            }

                            @Override
                            public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                            }
                        });
                    } else {
                        MyPostRequest request = new MyPostRequest(getContext(), "to", "0", pageNo, COUNT);
                        NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                            @Override
                            public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                                mAdapter.addAllPost(result.getResult());
                                pageNo++;
                            }

                            @Override
                            public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                            }
                        });
                    }
                } else if (num == 3) {
                    mAdapter.clearPost();
                    MyListenRequest request = new MyListenRequest(getContext(), pageNo, COUNT);
                    NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                        @Override
                        public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                            mAdapter.addAllPost(result.getResult());
                            pageNo++;
                        }

                        @Override
                        public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                        }
                    });
                }

            }

            @Override
            public void onAdapterItemClick(View view, Post post, int position) {
                if (!comFlag) {
                    if (tabType == 1) {
                        showReplay(post);
                    }
                }
            }

            @Override
            public void onAdapterPlayItemClick(View view, final Post post, int position) {
                switch (mState){
                    case STOPPED : {
                        startPlay(post, position);
                        break;
                    }
                    case STARTED: {
                        if(timePosition == position) {
                            startflag = false;
                            mHandler.removeCallbacks(countRunnable);
                            player.pause();
                            pauseTime = count;
                            mState = PlayState.PAUSED;
                        }else{
                            startPlay(post, position);
                        }
                        break;
                    }

                    case PAUSED:{
                        if(timePosition == position) {
                            startflag = true;
                            player.start();
                            endTime = String.valueOf(pauseTime);
                            mState = PlayState.STARTED;
                            startTime = -1;
                            mHandler.removeCallbacks(countRunnable);
                            mHandler.post(countRunnable);
                            break;
                        }else{
                            startPlay(post, position);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onAdapterQuestionerClick(View view, Post post, int position) {
                if (tabType == 1 || tabType == 3) {
                    showOtherPage(post.getQuestionerId());
                }
            }

            @Override
            public void onAdapterAnswerClick(View view, Post post, int position) {
                if (tabType == 2 || tabType == 3)
                    showOtherPage(post.getAnswernerId());
            }


        });

        return view;
    }

    private void startPlay(final Post post, int position) {
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


    String[] items = {"촬영", "앨범에서 선택", "프로필사진 삭제"};
    public void onListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("프로필 사진 설정");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position) {
                switch (position){
                    case 0 : {
                        getCaptureImage();
                        break;
                    }
                    case 1 : {
                        getGalleryImage();
                        break;
                    }
                    case 2 : {
                        RemoveImageRequest request = new RemoveImageRequest(getContext());
                        NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType>() {
                            @Override
                            public void onSuccess(NetworkRequest<NetWorkResultType> request, NetWorkResultType result) {
                                Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFail(NetworkRequest<NetWorkResultType> request, int errorCode, String errorMessage, Throwable e) {

                            }
                        });
                        break;
                    }
                }
            }
        });
        builder.show();
    }

    private static final int RC_GET_IMAGE = 1;

    private void getGalleryImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, RC_GET_IMAGE);
    }

    private static final int RC_CATPURE_IMAGE = 2;

    private void getCaptureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getSaveFile());
        startActivityForResult(intent, RC_CATPURE_IMAGE);
    }

    File savedFile = null;
    File uploadFile = null;
    private Uri getSaveFile() {
        File dir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM
        ),"camera");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        savedFile = new File(dir, "1mm_" + System.currentTimeMillis() + ".jpeg");
        return Uri.fromFile(savedFile);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_GET_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                Cursor c = getContext().getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                if (c.moveToNext()) {
                    String path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
                    uploadFile = new File(path);
                    ChangeImageRequest request = new ChangeImageRequest(getContext(), uploadFile);
                    NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType>() {
                        @Override
                        public void onSuccess(NetworkRequest<NetWorkResultType> request, NetWorkResultType result) {
                            Toast.makeText(getContext(),result.getMessage(),Toast.LENGTH_SHORT).show();
                            refresh();
                        }

                        @Override
                        public void onFail(NetworkRequest<NetWorkResultType> request, int errorCode, String errorMessage, Throwable e) {
                        }
                    });
                }
            }
        } else if (requestCode == RC_CATPURE_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                uploadFile = savedFile;
                ChangeImageRequest request = new ChangeImageRequest(getContext(), uploadFile);
                NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType>() {
                    @Override
                    public void onSuccess(NetworkRequest<NetWorkResultType> request, NetWorkResultType result) {
                        Toast.makeText(getContext(),result.getMessage(),Toast.LENGTH_SHORT).show();
                        refresh();
                    }

                    @Override
                    public void onFail(NetworkRequest<NetWorkResultType> request, int errorCode, String errorMessage, Throwable e) {
                    }
                });
            }
        }
    }

    private void refresh(){
        mAdapter.clearPost();
        pageNo = 1;
        MyDataReqeust reqeust = new MyDataReqeust(getContext());
        NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,reqeust, new NetworkManager.OnResultListener<NetWorkResultType<MyData>>() {
            @Override
            public void onSuccess(NetworkRequest<NetWorkResultType<MyData>> request, NetWorkResultType<MyData> result) {
                String nickName = "User NickName";
                if(result.getResult().getNickname() != null) {
                    nickName = result.getResult().getNickname();
                }
                mAdapter.addMyData(result.getResult());
                nickNameView.setText(nickName);
            }
            @Override
            public void onFail(NetworkRequest<NetWorkResultType<MyData>> request, int errorCode, String errorMessage, Throwable e) {

            }
        });

        if (tabType == 1) {
            if (comFlag) {
                MyPostRequest request = new MyPostRequest(getContext(), "from", "1", pageNo, COUNT);
                NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                    @Override
                    public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                        mAdapter.addAllPost(result.getResult());
                        pageNo++;
                    }

                    @Override
                    public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                    }
                });
            } else {
                MyPostRequest request = new MyPostRequest(getContext(), "from", "0", pageNo, COUNT);
                NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                    @Override
                    public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                        mAdapter.addAllPost(result.getResult());
                        pageNo++;
                    }

                    @Override
                    public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                    }
                });
            }
        } else if (tabType == 2) {
            if (comFlag) {
                MyPostRequest request = new MyPostRequest(getContext(), "to", "1", pageNo, COUNT);
                NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                    @Override
                    public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                        mAdapter.addAllPost(result.getResult());
                        pageNo++;
                    }

                    @Override
                    public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                    }
                });
            } else {
                MyPostRequest request = new MyPostRequest(getContext(), "to", "0", pageNo, COUNT);
                NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                    @Override
                    public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                        mAdapter.addAllPost(result.getResult());
                        pageNo++;
                    }

                    @Override
                    public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                    }
                });
            }
        } else if (tabType == 3) {
            MyListenRequest request = new MyListenRequest(getContext(), pageNo, COUNT);
            NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                @Override
                public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                    mAdapter.addAllPost(result.getResult());
                    pageNo++;
                }

                @Override
                public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                }
            });
        }
    }

    @OnClick(R.id.btn_setting)
    public void settingClick(View view) {
        showSetting();
    }


    @Override
    public void onResume() {
        super.onResume();
        mAdapter.setHeaderPlayInfo(false);
        mState = PlayState.STOPPED;
        mAdapter.setFlag(comFlag);
        mAdapter.setTabPosition(tabType);
        mAdapter.setTime("답변 듣기", timePosition);
        MyDataReqeust reqeust = new MyDataReqeust(getContext());
        NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,reqeust, new NetworkManager.OnResultListener<NetWorkResultType<MyData>>() {
            @Override
            public void onSuccess(NetworkRequest<NetWorkResultType<MyData>> request, NetWorkResultType<MyData> result) {
                String nickName = "User NickName";
                if(result.getResult().getNickname() != null) {
                    nickName = result.getResult().getNickname();
                }
                mAdapter.addMyData(result.getResult());
                nickNameView.setText(nickName);
            }
            @Override
            public void onFail(NetworkRequest<NetWorkResultType<MyData>> request, int errorCode, String errorMessage, Throwable e) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

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
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mAdapter.setHeaderPlayInfo(false);
            }
        });
    }

    long startTime = -1;
    String endTime = "";
    int timePosition;
    int count;
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

    private void showOtherPage(String id){
        if(getParentFragment() instanceof TabMyFragment){
            ((TabMyFragment) (getParentFragment())).showOther(id);
        }else if(getParentFragment() instanceof TabHomeFragment){
            ((TabHomeFragment) (getParentFragment())).showOther(id);
        }else if(getParentFragment() instanceof TabRankFragment){
            ((TabRankFragment) (getParentFragment())).showOther(id);
        }else{
            ((TabSearchFragment) (getParentFragment())).showOther(id);
        }
    }

    private void showFollowing(String id){
        if(getParentFragment() instanceof TabMyFragment){
            ((TabMyFragment) (getParentFragment())).showFollwing(id);
        }else if(getParentFragment() instanceof TabHomeFragment){
            ((TabHomeFragment) (getParentFragment())).showFollwing(id);
        }else if(getParentFragment() instanceof TabRankFragment){
            ((TabRankFragment) (getParentFragment())).showFollwing(id);
        }else{
            ((TabSearchFragment) (getParentFragment())).showFollwing(id);
        }
    }

    private void showFollower(String id){
        if(getParentFragment() instanceof TabMyFragment){
            ((TabMyFragment) (getParentFragment())).showFollwer(id);
        }else if(getParentFragment() instanceof TabHomeFragment){
            ((TabHomeFragment) (getParentFragment())).showFollwer(id);
        }else if(getParentFragment() instanceof TabRankFragment){
            ((TabRankFragment) (getParentFragment())).showFollwer(id);
        }else{
            ((TabSearchFragment) (getParentFragment())).showFollwer(id);
        }
    }

    private void showSetting(){
        if(getParentFragment() instanceof TabMyFragment){
            ((TabMyFragment) (getParentFragment())).showSetting();
        }else if(getParentFragment() instanceof TabHomeFragment){
            ((TabHomeFragment) (getParentFragment())).showSetting();
        }else if(getParentFragment() instanceof TabRankFragment){
            ((TabRankFragment) (getParentFragment())).showSetting();
        }else{
            ((TabSearchFragment) (getParentFragment())).showSetting();
        }
    }

    private void showReplay(Post post) {
        if(getParentFragment() instanceof TabMyFragment){
            ((TabMyFragment) (getParentFragment())).showReply(post);
        }else if(getParentFragment() instanceof TabHomeFragment){
            ((TabHomeFragment) (getParentFragment())).showReply(post);
        }else if(getParentFragment() instanceof TabRankFragment){
            ((TabRankFragment) (getParentFragment())).showReply(post);
        }else{
            ((TabSearchFragment) (getParentFragment())).showReply(post);
        }
    }

    private void showDonatingPlace(String donatingiD){
        if(getParentFragment() instanceof TabMyFragment){
            ((TabMyFragment) (getParentFragment())).showDonate(donatingiD);
        }else if(getParentFragment() instanceof TabHomeFragment){
            ((TabHomeFragment) (getParentFragment())).showDonate(donatingiD);
        }else if(getParentFragment() instanceof TabRankFragment){
            ((TabRankFragment) (getParentFragment())).showDonate(donatingiD);
        }else{
            ((TabSearchFragment) (getParentFragment())).showDonate(donatingiD);
        }
    }

    private void showModifyProfile(MyData myData){
        if(getParentFragment() instanceof TabMyFragment){
            ((TabMyFragment) (getParentFragment())).showProfile(myData);
        }else if(getParentFragment() instanceof TabHomeFragment){
            ((TabHomeFragment) (getParentFragment())).showProfile(myData);
        }else if(getParentFragment() instanceof TabRankFragment){
            ((TabRankFragment) (getParentFragment())).showProfile(myData);
        }else{
            ((TabSearchFragment) (getParentFragment())).showProfile(myData);
        }
    }
}
