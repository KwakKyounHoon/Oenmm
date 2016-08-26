package com.onemeter.omm.onemm.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.adapter.RankPopularAdapter;
import com.onemeter.omm.onemm.data.RankPopular;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RankPopularFragment extends Fragment {

    @BindView(R.id.list)
    RecyclerView list;

    RankPopularAdapter mAdapter;

    public RankPopularFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rank_popular, container, false);
        ButterKnife.bind(this,view);
        mAdapter = new RankPopularAdapter();
        list.setAdapter(mAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(manager);

        mAdapter.setOnAdapterItemClickListener(new RankPopularAdapter.OnAdapterItemClickLIstener() {
            @Override
            public void onAdapterItemClick(View view, RankPopular rankPopular, int position) {
                Toast.makeText(getContext(), rankPopular.getQuestionerId(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterPlayClick(View view, RankPopular rankPopular, int position) {
                Toast.makeText(getContext(), rankPopular.getVoiceContent(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterCategoryItemClick(Boolean flag) {
                if(flag){
                    mAdapter.clearRankPopular();
                    init();
                }else{
                    mAdapter.clearRankPopular();
                    init2();
                }
            }
        });

        init();
        return view;
    }

    public void init(){
        for(int i = 0; i < 5; i++){
            RankPopular rankPopular = new RankPopular();
            rankPopular.setLength(i+"1");
            rankPopular.setListenCount(i+"2");
            rankPopular.setPrice(i+"00000");
            rankPopular.setQuestionerContent("I can do it"+i);
            rankPopular.setQuestionerId(""+i);
            rankPopular.setQuestionerId("1"+i);
            rankPopular.setVoiceContent("Good Play"+i);
            mAdapter.addRankPopular(rankPopular);
        }
    }

    public void init2(){
        for(int i = 5; i < 11; i++){
            RankPopular rankPopular = new RankPopular();
            rankPopular.setLength(i+"1");
            rankPopular.setListenCount(i+"2");
            rankPopular.setPrice(i+"00000");
            rankPopular.setQuestionerContent("I can do it"+i);
            rankPopular.setQuestionerId(""+i);
            rankPopular.setQuestionerId("1"+i);
            rankPopular.setVoiceContent("Good Play"+i);
            mAdapter.addRankPopular(rankPopular);
        }
    }
}
