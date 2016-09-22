package com.onemeter.omm.onemm.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.onemeter.omm.onemm.MyApplication;
import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.NetWorkResultType;
import com.onemeter.omm.onemm.data.Post;
import com.onemeter.omm.onemm.manager.NetworkManager;
import com.onemeter.omm.onemm.manager.NetworkRequest;
import com.onemeter.omm.onemm.request.ListenPayRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListenToOffFragment extends Fragment {
    public static String POST ="post";
    public static String PAYPOSITION = "payposition";
    Post post;
    @BindView(R.id.text_questioner_name)
    TextView qNameView;
    @BindView(R.id.text_answer_name)
    TextView aNameVIew;
    @BindView(R.id.text_question)
    TextView questionView;
    @BindView(R.id.text_cost)
    TextView moneyView;
    @BindView(R.id.text_play_time)
    TextView timeView;
    @BindView(R.id.text_listen)
    TextView listenView;
    @BindView(R.id.image_questioner)
    ImageView qImageVIew;
    @BindView(R.id.image_answerner)
    ImageView aImageview;
    @BindView(R.id.check_use)
    ImageView checkUse;
    @BindView(R.id.check_info)
    ImageView checkInfo;
    @BindView(R.id.btn_pay)
    Button payBtn;

    Boolean info = false;
    Boolean use = false;

    public ListenToOffFragment() {
        // Required empty public constructor
    }

    public static ListenToOffFragment newInstance(Post post, int payPosition) {
        ListenToOffFragment fragment = new ListenToOffFragment();
        Bundle args = new Bundle();
        args.putSerializable(POST, post);
        args.putInt(PAYPOSITION, payPosition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            post = (Post) getArguments().getSerializable(POST);
        }


    }

    public boolean payCheck() {
        if (use && info) {
            payBtn.setBackgroundColor(Color.parseColor("#f82040"));
            return true;

        } else {
            payBtn.setBackgroundColor(Color.BLACK);
            return false;
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listento_off, container, false);
        ButterKnife.bind(this, view);

        checkInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!info) {
                    checkInfo.setImageResource(R.drawable.ic_checkbox_on);
                    info = true;
                } else  {
                    checkInfo.setImageResource(R.drawable.ic_checkbox_off);
                    info = false;
                }
                payCheck();
            }
        });

        checkUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!use) {
                    checkUse.setImageResource(R.drawable.ic_checkbox_on);
                    use = true;
                } else  {
                    checkUse.setImageResource(R.drawable.ic_checkbox_off);
                    use = false;
                }
                payCheck();
            }
        });


        init();
        return view;
    }

    @OnClick(R.id.btn_pay)
    public void payClick(View view){
        // 결제완료 버튼 누를 떄 구현 - > ListenToOnFragment로 View Chagne 및 답변듣기 버튼 활성화
        if(use && info) {
            ListenPayRequest request = new ListenPayRequest(getContext(), post.getAnswerId());
            NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP, request, new NetworkManager.OnResultListener<NetWorkResultType>() {
                @Override
                public void onSuccess(NetworkRequest<NetWorkResultType> request, NetWorkResultType result) {
                    Toast.makeText(getContext(), "성공", Toast.LENGTH_SHORT).show();
                    popFragment();
                }

                @Override
                public void onFail(NetworkRequest<NetWorkResultType> request, int errorCode, String errorMessage, Throwable e) {
                    Toast.makeText(getContext(), "실패", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getContext(), "체크박스 비활성화", Toast.LENGTH_SHORT).show();
        }
    }

    public void init(){
        qNameView.setText(post.getQuestionerName());
        aNameVIew.setText(post.getAnswernerName());
        questionView.setText(post.getQuestionerContent());
        timeView.setText(post.getLength());
        listenView.setText(post.getListenCount());
        moneyView.setText(post.getPrice());
        Glide.with(qImageVIew.getContext())
                .load(post.getQuestionerPhoto())
                .bitmapTransform(new CropCircleTransformation(MyApplication.getContext()))
                .error(R.drawable.ic_profile_image_default)
                .into(qImageVIew);
        Glide.with(aImageview.getContext())
                .load(post.getAnswernerPhoto())
                .bitmapTransform(new CropCircleTransformation(MyApplication.getContext()))
                .error(R.drawable.ic_profile_image_default)
                .into(aImageview);
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

    private void popFragment(){
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

}
