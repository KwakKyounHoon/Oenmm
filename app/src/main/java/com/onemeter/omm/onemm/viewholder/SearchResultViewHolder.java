package com.onemeter.omm.onemm.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.onemeter.omm.onemm.MyApplication;
import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.SearchResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Tacademy on 2016-09-02.
 */
public class SearchResultViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.text_name)
    TextView nameView;
    @BindView(R.id.text_nickname)
    TextView nicknameView;
    @BindView(R.id.image_photo)
    ImageView photoView;

    public SearchResultViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.onItemClick(view, searchResult, getAdapterPosition());
                }
            }
        });
    }

    SearchResult searchResult;
    public void setSearchResult(SearchResult searchResult) {
            this.searchResult = searchResult;
            nameView.setText(searchResult.getName());
            nicknameView.setText(searchResult.getNickname());
            Glide.with(photoView.getContext())
                    .load(searchResult.getPhoto())
                    .bitmapTransform(new CropCircleTransformation(MyApplication.getContext()))
                    .error(R.drawable.ic_profile_image_default)
                    .into(photoView);
    }

    public interface OnSearchResultItemClickListener {
        public void onItemClick(View view, SearchResult searchResult, int position);
    }

    OnSearchResultItemClickListener listener;

    public void setSearchResultItemClickListener(OnSearchResultItemClickListener listener) {
        this.listener = listener;
    }
}
