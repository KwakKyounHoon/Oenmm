package com.onemeter.omm.onemm.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.adapter.SearchResultAdapter;
import com.onemeter.omm.onemm.data.NetWorkResultType;
import com.onemeter.omm.onemm.data.SearchResult;
import com.onemeter.omm.onemm.manager.NetworkManager;
import com.onemeter.omm.onemm.manager.NetworkRequest;
import com.onemeter.omm.onemm.request.SearchRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchResultFragment extends Fragment {


    public SearchResultFragment() {
        // Required empty public constructor
    }
    @BindView(R.id.edit_search)
    EditText searchView;
    @BindView(R.id.list)
    RecyclerView list;
    SearchResultAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);
        ButterKnife.bind(this, view);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mAdapter = new SearchResultAdapter();
        list.setAdapter(mAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(manager);
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String keyword = charSequence.toString();
                if(charSequence.length()>2) {
                SearchRequest request = new SearchRequest(getContext(), keyword, 1, 20);
                mAdapter.clear();
                NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType<SearchResult[]>>() {
                    @Override
                    public void onSuccess(NetworkRequest<NetWorkResultType<SearchResult[]>> request, NetWorkResultType<SearchResult[]> result) {
                        mAdapter.addAll(result.getResult());
                    }

                    @Override
                    public void onFail(NetworkRequest<NetWorkResultType<SearchResult[]>> request, int errorCode, String errorMessage, Throwable e) {

                    }
                });
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mAdapter.setOnAdapterItemClickListener(new SearchResultAdapter.OnAdapterItemClickLIstener() {
            @Override
            public void onAdapterItemClick(View view, SearchResult searchResult, int position) {
                ((TabSearchFragment) (getParentFragment())).showOther(searchResult.getUserId());
            }
        });
        return view;
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
