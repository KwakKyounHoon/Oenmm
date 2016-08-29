package com.onemeter.omm.onemm.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.FollowPerson;

/**
 * Created by Tacademy on 2016-08-29.
 */
public class FollowPersonViewHolder extends RecyclerView.ViewHolder {


    TextView nameView,photoView;

    public FollowPersonViewHolder(View itemView) {
        super(itemView);
        photoView = (TextView) itemView.findViewById(R.id.image_photo);
        nameView = (TextView)itemView.findViewById(R.id.text_name);


    }

    FollowPerson followPerson;

    public void setFollowPerson(FollowPerson followPerson) {
        this.followPerson = followPerson;
        photoView.setText(followPerson.getPhoto());
        nameView.setText(followPerson.getName());
    }
}
