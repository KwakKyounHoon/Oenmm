package com.onemeter.omm.onemm.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.SearchResult;
import com.onemeter.omm.onemm.viewholder.SearchResultViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Tacademy on 2016-09-02.
 */
public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultViewHolder> implements SearchResultViewHolder.OnSearchResultItemClickListener{
    List<SearchResult> searchResults = new ArrayList<>();

    public void addAll(SearchResult[] results){
        searchResults.addAll(Arrays.asList(results));
        notifyDataSetChanged();
    }

    public void clear(){
        searchResults.clear();
    }

    @Override
    public SearchResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_search_result,parent,false);
        return new SearchResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchResultViewHolder holder, int position) {
        SearchResultViewHolder srvh = (SearchResultViewHolder)holder;
        srvh.setSearchResult(searchResults.get(position));
        srvh.setSearchResultItemClickListener(this);
    }

    @Override
    public int getItemCount() {
        if(searchResults.size() <= 0) return 0;
        return searchResults.size();
    }

    @Override
    public void onItemClick(View view, SearchResult searchResult, int position) {
        if(listener != null){
            listener.onAdapterItemClick(view, searchResult, position);
        }
    }

    public interface OnAdapterItemClickLIstener {
        public void onAdapterItemClick(View view, SearchResult searchResult, int position);
    }

    OnAdapterItemClickLIstener listener;
    public void setOnAdapterItemClickListener(OnAdapterItemClickLIstener listener) {
        this.listener = listener;
    }
}
