package com.onemeter.omm.onemm.fragment;


import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.adapter.OtherAdapter;
import com.onemeter.omm.onemm.data.NetWorkResultType;
import com.onemeter.omm.onemm.data.OtherData;
import com.onemeter.omm.onemm.data.Post;
import com.onemeter.omm.onemm.manager.NetworkManager;
import com.onemeter.omm.onemm.manager.NetworkRequest;
import com.onemeter.omm.onemm.manager.PropertyManager;
import com.onemeter.omm.onemm.request.AddFollowReqeust;
import com.onemeter.omm.onemm.request.BlockRequest;
import com.onemeter.omm.onemm.request.OtherDataRequest;
import com.onemeter.omm.onemm.request.OtherPostRequest;
import com.onemeter.omm.onemm.request.ProfileVoiceRequest;
import com.onemeter.omm.onemm.request.RemoveFollowRequest;
import com.onemeter.omm.onemm.request.ReplyListenRequest;
import com.onemeter.omm.onemm.request.ReportRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class OtherFragment extends Fragment {

    public static String OTHER_ID = "id";
    private String id;
    int tabType = 1;
    boolean categoryType = true;
    boolean isLastItem;
    int pageNo = 1;
    private final int COUNT = 10;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.list)
    RecyclerView list;

    @BindView(R.id.text_nickname)
    TextView nickNameView;

    @BindView(R.id.btn_back)
    ImageView backView;
    ShareActionProvider mShareActionProvider;

    MediaPlayer player;

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
        mAdapter = new OtherAdapter();
        OtherPostRequest otherPostRequest = new OtherPostRequest(getContext(),id,"from","0", pageNo, COUNT);
        NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,otherPostRequest, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem shareMenuItem = menu.findItem(R.id.menu_item_1);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareMenuItem);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        AppCompatActivity menu = (AppCompatActivity) getActivity();
        toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        menu.setSupportActionBar(toolbar);
        menu.getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.inflateMenu(R.menu.toolbar_menu);
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_other, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        list.setAdapter(mAdapter);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(manager);

        list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(isLastItem && newState == RecyclerView.SCROLL_STATE_IDLE){
                    if(tabType == 1){
                        if(categoryType){
                            OtherPostRequest request = new OtherPostRequest(getContext(), id, "from", "0", pageNo, COUNT);
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
                        }else{
                            OtherPostRequest request = new OtherPostRequest(getContext(), id, "from", "1", pageNo, COUNT);
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
                    }else{
                        if(categoryType){
                            OtherPostRequest request = new OtherPostRequest(getContext(), id, "to", "0", pageNo, COUNT);
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
                        }else{
                            OtherPostRequest request = new OtherPostRequest(getContext(), id, "to", "1", pageNo, COUNT);
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

        mAdapter.setOnAdapterItemClickListener(new OtherAdapter.OnAdapterItemClickLIstener() {
            @Override
            public void onAdapterQuestionClick(View view, OtherData otherData) {
                if(getParentFragment() instanceof TabMyFragment){
                    ((TabMyFragment) (getParentFragment())).showQuestion(otherData);
                }else if(getParentFragment() instanceof TabHomeFragment){
                    ((TabHomeFragment)getParentFragment()).showQuestion(otherData);
                }else if(getParentFragment() instanceof TabRankFragment){
                    ((TabRankFragment) (getParentFragment())).showQuestion(otherData);
                }else{
                    ((TabSearchFragment) (getParentFragment())).showQuestion(otherData);
                }
            }

            @Override
            public void onAdapterFollowClick(View view, boolean flag) {
                if(flag){
                    AddFollowReqeust request = new AddFollowReqeust(getContext(), id);
                    NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType>() {
                        @Override
                        public void onSuccess(NetworkRequest<NetWorkResultType> request, NetWorkResultType result) {
                            Toast.makeText(getContext(), result.getMessage()+"",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFail(NetworkRequest<NetWorkResultType> request, int errorCode, String errorMessage, Throwable e) {

                        }
                    });
                }else{
                    RemoveFollowRequest request = new RemoveFollowRequest(getContext(), id);
                    NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType>() {
                        @Override
                        public void onSuccess(NetworkRequest<NetWorkResultType> request, NetWorkResultType result) {
                            Toast.makeText(getContext(), result.getMessage(),Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFail(NetworkRequest<NetWorkResultType> request, int errorCode, String errorMessage, Throwable e) {

                        }
                    });
                }
            }

            @Override
            public void onAdapterFollowingClick(View view, OtherData otherData, int position) {
                if(getParentFragment() instanceof TabMyFragment){
                    ((TabMyFragment) (getParentFragment())).showFollwing(id);
                }else if(getParentFragment() instanceof TabHomeFragment){
                    ((TabHomeFragment)getParentFragment()).showFollwing(id);
                }else if(getParentFragment() instanceof TabRankFragment){
                    ((TabRankFragment) (getParentFragment())).showFollwing(id);
                }else{
                    ((TabSearchFragment) (getParentFragment())).showFollwing(id);
                }
            }

            @Override
            public void onAdapterFollowerClick(View view, OtherData otherData, int position) {
                if(getParentFragment() instanceof TabMyFragment){
                    ((TabMyFragment) (getParentFragment())).showFollwer(id);
                }else if(getParentFragment() instanceof TabHomeFragment){
                    ((TabHomeFragment)getParentFragment()).showFollwer(id);
                }else if(getParentFragment() instanceof TabRankFragment){
                    ((TabRankFragment) (getParentFragment())).showFollwer(id);
                }else{
                    ((TabSearchFragment) (getParentFragment())).showFollwer(id);
                }
            }

            @Override
            public void onAdapterSoundClick(View view, OtherData otherData, int position) {
                Toast.makeText(getContext(), "소리", Toast.LENGTH_SHORT).show();
                ProfileVoiceRequest request = new ProfileVoiceRequest(getContext(), otherData.getUserId());
                NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP, request, new NetworkManager.OnResultListener<NetWorkResultType<String>>() {
                    @Override
                    public void onSuccess(NetworkRequest<NetWorkResultType<String>> request, NetWorkResultType<String> result) {
                        try {
                            playAudio(result.getResult());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(NetworkRequest<NetWorkResultType<String>> request, int errorCode, String errorMessage, Throwable e) {

                    }
                });
                try {
                    playAudio(otherData.getVoiceMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onAdapterTabType(View view, int type) {
                pageNo = 1;
                killMediaPlayer();
                mAdapter.clearPost();
                startflag = false;
                tabType = type;
                if(tabType == 1){
                    if(categoryType){
                        OtherPostRequest request = new OtherPostRequest(getContext(), id, "from", "0", pageNo, COUNT);
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
                    }else{
                        OtherPostRequest request = new OtherPostRequest(getContext(), id, "from", "1", pageNo, COUNT);
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
                }else{
                    if(categoryType){
                        OtherPostRequest request = new OtherPostRequest(getContext(), id, "to", "0", pageNo, COUNT);
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
                    }else{
                        OtherPostRequest request = new OtherPostRequest(getContext(), id, "to", "1", pageNo, COUNT);
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
            public void onAdapterCategoryItemClick(boolean flag) {
                pageNo = 1;
                killMediaPlayer();
                mAdapter.clearPost();
                startflag = false;
                categoryType = flag;
                if(flag){
                    if(tabType == 1){
                        OtherPostRequest request = new OtherPostRequest(getContext(), id, "from", "0", pageNo, COUNT);
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
                    }else{
                        OtherPostRequest request = new OtherPostRequest(getContext(), id, "to", "0", pageNo, COUNT);
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
                }else{
                    if(tabType == 1){
                        OtherPostRequest request = new OtherPostRequest(getContext(), id, "from", "1", pageNo, COUNT);
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
                    }else{
                        OtherPostRequest request = new OtherPostRequest(getContext(), id, "to", "1", pageNo, COUNT);
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
            public void onAdapterItemClick(View view, Post post, int position) {
                if(post.getPayInfo().equals("0")){
                    showListenToOff(post, position);
                }
            }

            @Override
            public void onAdapterPlayClick(View view, final Post post, int position) {
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
                }else{
                    showListenToOff(post, position);
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

        OtherDataRequest request = new OtherDataRequest(getContext(), id);
        NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<OtherData>>() {
            @Override
            public void onSuccess(NetworkRequest<NetWorkResultType<OtherData>> request, NetWorkResultType<OtherData> result) {
                mAdapter.addOtherData(result.getResult());
                if(result.getResult().getNickname() != null){
                    nickNameView.setText(result.getResult().getName());
                }
            }

            @Override
            public void onFail(NetworkRequest<NetWorkResultType<OtherData>> request, int errorCode, String errorMessage, Throwable e) {

            }
        });

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { // 신고 차단 UI 구분
        switch (item.getItemId()) {
            case R.id.menu_item_1:
                return true;
            case R.id.submenu_item_1:
                onListDialog();
                return true;
            case R.id.submenu_item_2:
                onDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    String[] items = {"사칭 계정입니다.", "불쾌한 게시물을 올렸습니다.",
            "광고성 게시물을 올렸습니다."};

    public void onDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("정말 차단하시겠습니까?")
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Toast.makeText(getContext(), "취소버튼 동작 구현",Toast.LENGTH_SHORT).show();
                    }
                }).setPositiveButton("예, 확실합니다", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                Toast.makeText(getContext(), "확인버튼 동작 구현",Toast.LENGTH_SHORT).show();
                BlockRequest request = new BlockRequest(getContext(), id);
                NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType>() {
                    @Override
                    public void onSuccess(NetworkRequest<NetWorkResultType> request, NetWorkResultType result) {
                        Toast.makeText(getContext(), result.getMessage() ,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail(NetworkRequest<NetWorkResultType> request, int errorCode, String errorMessage, Throwable e) {

                    }
                });
            }
        });
        builder.show();
    }


    public void onListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("신고하는 이유");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position) {
                final int listPosition = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("정말 신고하시겠습니까?")
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getContext(), "취소버튼 동작 구현",Toast.LENGTH_SHORT).show();
                            }
                        }).setPositiveButton("예, 확실합니다", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (listPosition){
                            case 0 : {
                                ReportRequest request = new ReportRequest(getContext(), id, 1);
                                NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType>() {
                                    @Override
                                    public void onSuccess(NetworkRequest<NetWorkResultType> request, NetWorkResultType result) {
                                        Toast.makeText(getContext(), result.getMessage(),Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFail(NetworkRequest<NetWorkResultType> request, int errorCode, String errorMessage, Throwable e) {

                                    }
                                });
                                break;
                            }
                            case 1 :{
                                ReportRequest request = new ReportRequest(getContext(), id, 2);
                                NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType>() {
                                    @Override
                                    public void onSuccess(NetworkRequest<NetWorkResultType> request, NetWorkResultType result) {
                                        Toast.makeText(getContext(), result.getMessage(),Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFail(NetworkRequest<NetWorkResultType> request, int errorCode, String errorMessage, Throwable e) {

                                    }
                                });
                                break;
                            }
                            case 2 :{
                                ReportRequest request = new ReportRequest(getContext(), id, 3);
                                NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType>() {
                                    @Override
                                    public void onSuccess(NetworkRequest<NetWorkResultType> request, NetWorkResultType result) {
                                        Toast.makeText(getContext(), result.getMessage(),Toast.LENGTH_SHORT).show();
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
        });
        builder.show();
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

    @Override
    public void onResume() {
        super.onResume();
        checkPayInfo();
        mAdapter.setFlag(categoryType);
        mAdapter.setTabPosition(tabType);
        mAdapter.setTime("답변 듣기", timePosition);
    }

    private void showOtherPage(String id) {
        ((TabHomeFragment) (getParentFragment())).showOther(id);
    }

    private void showMyPage() {
        ((TabHomeFragment) (getParentFragment())).showMy();
    }

    private void showListenToOff(Post post, int payPosition){
        if(getParentFragment() instanceof TabMyFragment){
            ((TabMyFragment) (getParentFragment())).showListenToOff(post, payPosition);
        }else if(getParentFragment() instanceof TabHomeFragment){
            ((TabHomeFragment) (getParentFragment())).showListenToOff(post, payPosition);
        }else if(getParentFragment() instanceof TabRankFragment){
            ((TabRankFragment) (getParentFragment())).showListenToOff(post, payPosition);
        }else{
            ((TabSearchFragment) (getParentFragment())).showListenToOff(post, payPosition);
        }
    }

    private void checkPayInfo(){
        if(getParentFragment() instanceof TabMyFragment){
            TabMyFragment f = (TabMyFragment) getParentFragment();
            if(f.getPayPosition() != 0) {
                mAdapter.setPayPosition(f.getPayPosition());
                f.setPayPosition(0);
            }
        }else if(getParentFragment() instanceof TabHomeFragment){
            TabHomeFragment f = (TabHomeFragment) getParentFragment();
            if(f.getPayPosition() != 0) {
                mAdapter.setPayPosition(f.getPayPosition());
                f.setPayPosition(0);
            }
        }else if(getParentFragment() instanceof TabRankFragment){
            TabRankFragment f = (TabRankFragment) getParentFragment();
            if(f.getPayPosition() != 0) {
                mAdapter.setPayPosition(f.getPayPosition());
                f.setPayPosition(0);
            }
        }else{
            TabSearchFragment f = (TabSearchFragment) getParentFragment();
            if(f.getPayPosition() != 0) {
                mAdapter.setPayPosition(f.getPayPosition());
                f.setPayPosition(0);
            }
        }
    }
}
