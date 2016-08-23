package com.onemeter.omm.onemm.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.PostData;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Kwak on 2016-08-23.
 */
public class PostViewHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.text_name)
    TextView nameView;

    public PostViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    PostData data;
    public void setPost(PostData data){
        this.data = data;
        nameView.setText(data.getName());
    }
}
