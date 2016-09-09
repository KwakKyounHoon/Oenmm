package com.onemeter.omm.onemm.fragment;


import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
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
import com.onemeter.omm.onemm.request.AddFollowReqeust;
import com.onemeter.omm.onemm.request.BlockRequest;
import com.onemeter.omm.onemm.request.OtherDataRequest;
import com.onemeter.omm.onemm.request.OtherPostRequest;
import com.onemeter.omm.onemm.request.RemoveFollowRequest;
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
        mAdapter = new OtherAdapter();
        list.setAdapter(mAdapter);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(manager);

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
                try {
                    playAudio(otherData.getVoiceMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onAdapterTabType(View view, int type) {
                mAdapter.clearPost();
                tabType = type;
                if(tabType == 1){
                    if(categoryType){
                        OtherPostRequest request = new OtherPostRequest(getContext(), id, "from", "0", 1, 20);
                        NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                            @Override
                            public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                                mAdapter.addAllPost(result.getResult());
                            }

                            @Override
                            public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                            }
                        });
                    }else{
                        OtherPostRequest request = new OtherPostRequest(getContext(), id, "from", "1", 1, 20);
                        NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                            @Override
                            public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                                mAdapter.addAllPost(result.getResult());
                            }

                            @Override
                            public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                            }
                        });
                    }
                }else{
                    if(categoryType){
                        OtherPostRequest request = new OtherPostRequest(getContext(), id, "to", "0", 1, 20);
                        NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                            @Override
                            public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                                mAdapter.addAllPost(result.getResult());
                            }

                            @Override
                            public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                            }
                        });
                    }else{
                        OtherPostRequest request = new OtherPostRequest(getContext(), id, "to", "1", 1, 20);
                        NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
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
            }

            @Override
            public void onAdapterCategoryItemClick(boolean flag) {
                mAdapter.clearPost();
                categoryType = flag;
                if(flag){
                    if(tabType == 1){
                        OtherPostRequest request = new OtherPostRequest(getContext(), id, "from", "0", 1, 20);
                        NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                            @Override
                            public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                                mAdapter.addAllPost(result.getResult());
                            }

                            @Override
                            public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                            }
                        });
                    }else{
                        OtherPostRequest request = new OtherPostRequest(getContext(), id, "to", "0", 1, 20);
                        NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                            @Override
                            public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                                mAdapter.addAllPost(result.getResult());
                            }

                            @Override
                            public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                            }
                        });
                    }
                }else{
                    if(tabType == 1){
                        OtherPostRequest request = new OtherPostRequest(getContext(), id, "from", "1", 1, 20);
                        NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                            @Override
                            public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                                mAdapter.addAllPost(result.getResult());
                            }

                            @Override
                            public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                            }
                        });
                    }else{
                        OtherPostRequest request = new OtherPostRequest(getContext(), id, "to", "1", 1, 20);
                        NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
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
            }

            @Override
            public void onAdapterItemClick(View view, Post post, int position) {
                if(post.getPayInfo().equals("1")){
                    if(getParentFragment() instanceof TabMyFragment){
                        ((TabMyFragment) (getParentFragment())).showListenToOn(post);
                    }else if(getParentFragment() instanceof TabHomeFragment){
                        ((TabHomeFragment) (getParentFragment())).showListenToOn(post);
                    }else if(getParentFragment() instanceof TabRankFragment){
                        ((TabRankFragment) (getParentFragment())).showListenToOn(post);
                    }else{
                        ((TabSearchFragment) (getParentFragment())).showListenToOn(post);
                    }
                }else{
                    if(getParentFragment() instanceof TabMyFragment){
                        ((TabMyFragment) (getParentFragment())).showListenToOff(post);
                    }else if(getParentFragment() instanceof TabHomeFragment){
                        ((TabHomeFragment) (getParentFragment())).showListenToOff(post);
                    }else if(getParentFragment() instanceof TabRankFragment){
                        ((TabRankFragment) (getParentFragment())).showListenToOff(post);
                    }else{
                        ((TabSearchFragment) (getParentFragment())).showListenToOff(post);
                    }
                }
            }

            @Override
            public void onAdapterPlayClick(View view, Post post, int position) {
                Toast.makeText(getContext(),post.getVoiceContent(),Toast.LENGTH_SHORT).show();
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

                mAdapter.clearPost();

        OtherPostRequest otherPostRequest = new OtherPostRequest(getContext(),id,"from","0", 20, 1);
        NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,otherPostRequest, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
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
                        Toast.makeText(getContext(), "취소버튼 동작 구현",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getContext(), "확인버튼 동작 구현"+listPosition,Toast.LENGTH_SHORT).show();
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

}
