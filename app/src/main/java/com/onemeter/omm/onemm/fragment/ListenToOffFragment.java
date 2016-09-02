package com.onemeter.omm.onemm.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.Post;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListenToOffFragment extends Fragment {
    public static String POST ="post";
    Post post;
    @BindView(R.id.text_questioner_name)
    TextView qNameView;
    @BindView(R.id.text_answer_name)
    TextView aNameVIew;
    @BindView(R.id.text_question)
    TextView questionView;
    @BindView(R.id.text_money)
    TextView moneyView;
    @BindView(R.id.text_time)
    TextView timeView;
    @BindView(R.id.text_count)
    TextView listenView;
    @BindView(R.id.image_questioner)
    ImageView qImageVIew;
    @BindView(R.id.image_answerner)
    ImageView aImageview;

    public ListenToOffFragment() {
        // Required empty public constructor
    }

    public static ListenToOffFragment newInstance(Post post) {
        ListenToOffFragment fragment = new ListenToOffFragment();
        Bundle args = new Bundle();
        args.putSerializable(POST, post);
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listento_off, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    public void init(){
        qNameView.setText(post.getQuestionerName());
        aNameVIew.setText(post.getAnswernerName());
        questionView.setText(post.getQuestionerContent());
        timeView.setText(post.getLength());
        listenView.setText(post.getListenCount());
        moneyView.setText(post.getPrice());

    }

    @OnClick(R.id.btn_pay)
    public void payClick(View view){

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

}
