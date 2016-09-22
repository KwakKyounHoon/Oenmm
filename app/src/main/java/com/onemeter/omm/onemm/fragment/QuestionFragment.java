package com.onemeter.omm.onemm.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.onemeter.omm.onemm.MyApplication;
import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.NetWorkResultType;
import com.onemeter.omm.onemm.data.OtherData;
import com.onemeter.omm.onemm.manager.NetworkManager;
import com.onemeter.omm.onemm.manager.NetworkRequest;
import com.onemeter.omm.onemm.request.QuestionsRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends Fragment {


    public QuestionFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.text_answer_message)
    TextView messageView;
    @BindView(R.id.text_answer_nickname)
    TextView nicknameView;
    @BindView(R.id.text_answer_name)
    TextView nameView;
    @BindView(R.id.image_answerner)
    ImageView imageVIew;
    @BindView(R.id.edit_cost)
    EditText costView;
    @BindView(R.id.edit_question)
    EditText questionView;
    @BindView(R.id.image_check)
    ImageView checkView;
    @BindView(R.id.btn_pay)
    Button pay;
    @BindView(R.id.text_number)
    TextView number;


    public static String OTHER_DATA = "otherdata";
    OtherData otherData;
    Boolean check = false;

    public static QuestionFragment newInstance(OtherData otherData) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putSerializable(OTHER_DATA, otherData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            otherData = (OtherData) getArguments().getSerializable(OTHER_DATA);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        ButterKnife.bind(this, view);



        questionView.addTextChangedListener(new TextWatcher() {
            String currentCount;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if(charSequence.length() > 99) {
                    Toast.makeText(getContext() , "최대 허용 글자수는 100자 내외입니다." , Toast.LENGTH_SHORT).show();
                }
                number.setText("("+charSequence.length()+"자"+"/100자)");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        checkView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!check) {
                    checkView.setImageResource(R.drawable.ic_checkbox_on);
                    check = true;
                } else {
                    checkView.setImageResource(R.drawable.ic_checkbox_off);
                    check = false;
                }
                agreeCheck();
            }
        });
        init();
        return view;
    }

    @OnClick(R.id.btn_pay)
    public void payClick(View view) {
        String cost = costView.getText().toString();
        if (check) {
            String content = questionView.getText().toString();
            if (!TextUtils.isEmpty(cost) && !TextUtils.isEmpty(content) && Integer.parseInt(cost) >= 5000) {
                QuestionsRequest request = new QuestionsRequest(getContext(), cost, content, otherData.getUserId());
                NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP, request, new NetworkManager.OnResultListener<NetWorkResultType<String>>() {
                    @Override
                    public void onSuccess(NetworkRequest<NetWorkResultType<String>> request, NetWorkResultType<String> result) {
                        if (result.getResult().equals("0")) {
                            Toast.makeText(getContext(), "결제가 완료 되었습니다.", Toast.LENGTH_SHORT).show();
                            popFragment();
                        } else {
                            Toast.makeText(getContext(), "잔액이 부족합니다.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFail(NetworkRequest<NetWorkResultType<String>> request, int errorCode, String errorMessage, Throwable e) {

                    }
                });
            }else if(Integer.parseInt(cost) < 5000) {
                Toast.makeText(getContext(), " 최소 5000원 이상의 금액을 설정 하세요. ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void init() {
        messageView.setText(otherData.getStateMessage());
        nicknameView.setText(otherData.getNickname());
        nameView.setText(otherData.getName());
        Glide.with(imageVIew.getContext())
                .load(otherData.getPhoto())
                .error(R.drawable.ic_profile_image_default)
                .bitmapTransform(new CropCircleTransformation(MyApplication.getContext()))
                .into(imageVIew);
    }

    private void popFragment() {
        if (getParentFragment() instanceof TabMyFragment) {
            ((TabMyFragment) (getParentFragment())).popFragment();
        } else if (getParentFragment() instanceof TabHomeFragment) {
            ((TabHomeFragment) (getParentFragment())).popFragment();
        } else if (getParentFragment() instanceof TabRankFragment) {
            ((TabRankFragment) (getParentFragment())).popFragment();
        } else {
            ((TabSearchFragment) (getParentFragment())).popFragment();
        }
    }

    @OnClick(R.id.btn_back)
    public void backclick(View view) {
        if (getParentFragment() instanceof TabMyFragment) {
            ((TabMyFragment) (getParentFragment())).popFragment();
        } else if (getParentFragment() instanceof TabHomeFragment) {
            ((TabHomeFragment) (getParentFragment())).popFragment();
        } else if (getParentFragment() instanceof TabRankFragment) {
            ((TabRankFragment) (getParentFragment())).popFragment();
        } else {
            ((TabSearchFragment) (getParentFragment())).popFragment();
        }
    }

    String finalMoney;
    public boolean agreeCheck() {
        if (check) {
            pay.setBackgroundColor(Color.parseColor("#f82040"));
        } else {
            pay.setBackgroundColor(Color.BLACK);
        }
        return true;
    }
}
