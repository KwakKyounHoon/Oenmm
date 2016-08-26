package com.onemeter.omm.onemm.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.PostData;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tacademy on 2016-08-24.
 */

public class MyPostViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.text_question)
    TextView nameView;
    public MyPostViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.onPostItemClick(view, postData, getAdapterPosition());
                }
            }
        });
    }

    PostData postData;
    public void setPost(PostData postData){
        this.postData = postData;
        nameView.setText(postData.getName());
    }

    public interface OnMyPostItemClickListener {
        public void onPostItemClick(View view, PostData postData, int position);
    }

    OnMyPostItemClickListener listener;

    public void setOnMyPostItemClickListener(OnMyPostItemClickListener listener) {
        this.listener = listener;
    }
}
