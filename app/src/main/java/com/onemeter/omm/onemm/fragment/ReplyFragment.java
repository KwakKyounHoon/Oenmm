package com.onemeter.omm.onemm.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
public class ReplyFragment extends Fragment {
    Post post;

    public ReplyFragment() {
        // Required empty public constructor
    }

    public static String POST = "post";

    public static ReplyFragment newInstance(Post post) {
        ReplyFragment fragment = new ReplyFragment();
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

    @BindView(R.id.text_question)
    TextView qestionView;
    @BindView(R.id.image_questioner)
    ImageView qustionerView;
    @BindView(R.id.text_cost)
    TextView costView;
    @BindView(R.id.text_questioner_name)
    TextView qNameView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reply, container, false);
        ButterKnife.bind(this,view);
        setHasOptionsMenu(true);
        setData();
        return view;
    }

    public void setData(){
        qestionView.setText(post.getQuestionerContent());
        costView.setText(post.getPrice());
        qNameView.setText(post.getQuestionerName());
//        Glide.with(qustionerView.getContext())
//                .load(post.getQuestionerPhoto())
//                .into(qustionerView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            ((TabMyFragment) (getParentFragment())).popFragment();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_back)
    public void backClick(View view){
            ((TabMyFragment) (getParentFragment())).popFragment();
        }
    }



