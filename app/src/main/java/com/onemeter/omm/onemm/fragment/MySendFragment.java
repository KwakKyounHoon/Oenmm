package com.onemeter.omm.onemm.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.adapter.PostAdapter;
import com.onemeter.omm.onemm.data.PostData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MySendFragment extends Fragment {
    @BindView(R.id.list)
    RecyclerView list;
    PostAdapter mAdapter;

    public MySendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_receive, container, false);
        ButterKnife.bind(this,view);
        mAdapter = new PostAdapter();
        list.setAdapter(mAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(manager);

        List<PostData> postDatas = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            PostData postData = new PostData();
            postData.setName(""+i);
            postDatas.add(postData);
        }

        mAdapter.addPostDats(postDatas);
        // Inflate the layout for this fragment
        return view;
    }

}
