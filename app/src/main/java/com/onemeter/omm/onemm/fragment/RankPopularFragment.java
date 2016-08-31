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
import com.onemeter.omm.onemm.data.NetWorkResultType;
import com.onemeter.omm.onemm.data.Post;
import com.onemeter.omm.onemm.manager.NetworkManager;
import com.onemeter.omm.onemm.manager.NetworkRequest;
import com.onemeter.omm.onemm.request.PopularPostListRequest;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RankPopularFragment extends Fragment {

    @BindView(R.id.list)
    RecyclerView list;

    RankPopularAdapter mAdapter;

    Boolean tabType = true;

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
            public void onAdapterItemClick(View view, Post post, int position) {
                if(post.getPayInfo().equals("0")){
                    ((RankFragment) (getParentFragment())).showListenToOff(post);
                }
            }

            @Override
            public void onAdapterPlayClick(View view, Post rankPopular, int position) {
                Toast.makeText(getContext(), rankPopular.getVoiceContent(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterCategoryItemClick(Boolean flag) {
                tabType = flag;
                if(tabType){
                    mAdapter.clearRankPopular();
                    PopularPostListRequest request = new PopularPostListRequest(getContext(), 0);
                    NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                        @Override
                        public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                            mAdapter.addAll(result.getResult());
                        }

                        @Override
                        public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                        }
                    });
                }else{
                    mAdapter.clearRankPopular();
                    PopularPostListRequest request = new PopularPostListRequest(getContext(), 1);
                    NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
                        @Override
                        public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                            mAdapter.addAll(result.getResult());
                        }

                        @Override
                        public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

                        }
                    });
                }
            }
        });

        PopularPostListRequest request = new PopularPostListRequest(getContext(), 0);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetWorkResultType<Post[]>>() {
            @Override
            public void onSuccess(NetworkRequest<NetWorkResultType<Post[]>> request, NetWorkResultType<Post[]> result) {
                mAdapter.addAll(result.getResult());
            }

            @Override
            public void onFail(NetworkRequest<NetWorkResultType<Post[]>> request, int errorCode, String errorMessage, Throwable e) {

            }
        });

        return view;
    }

}
