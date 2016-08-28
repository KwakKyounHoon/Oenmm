package com.onemeter.omm.onemm.fragment;


import android.os.Bundle;
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
import com.onemeter.omm.onemm.data.Post;

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
                Toast.makeText(getContext(),post.getAnswernerId(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdpaterPlayClick(View view, Post post, int position) {
                Toast.makeText(getContext(),post.getVoiceContent(),Toast.LENGTH_SHORT).show();
            }
        });

        init();
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

//    @OnClick(R.id.btn_user)
//    public void onUserClick(){
//        if(getParentFragment() instanceof TabMyFragment){
//            ((TabMyFragment) (getParentFragment())).showOther("test");
//        }else if(getParentFragment() instanceof TabHomeFragment){
//            ((TabHomeFragment) (getParentFragment())).showOther("test");
//        }else if(getParentFragment() instanceof TabRankFragment){
//            ((TabRankFragment) (getParentFragment())).showOther("test");
//        }else{
//            ((TabSearchFragment) (getParentFragment())).showOther("test");
//        }
//    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) (getActivity())).changeHomeAsUp(false);
    }
}
