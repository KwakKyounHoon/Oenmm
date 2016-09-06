package com.onemeter.omm.onemm.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.onemeter.omm.onemm.MyApplication;
import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.FollowPerson;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Tacademy on 2016-08-29.
 */
public class FollowPersonViewHolder extends RecyclerView.ViewHolder implements Checkable {


    @BindView(R.id.image_questioner)
    ImageView photoView;
    @BindView(R.id.image_blur)
    ImageView blurView;
    @BindView(R.id.text_questioner_name)
    TextView nameView;

    boolean isChecked;

    public FollowPersonViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.onPersonItemClick(view, followPerson, getAdapterPosition());
                }
            }
        });

    }

    FollowPerson followPerson;

    public void setFollowPerson(FollowPerson followPerson) {
        this.followPerson = followPerson;
        nameView.setText(followPerson.getName());
        Glide.with(photoView.getContext())
                .load(followPerson.getPhoto())
                .bitmapTransform(new CropCircleTransformation(MyApplication.getContext()))
                .error(R.drawable.ic_profile_image_default)
                .into(photoView);
    }

    @Override
    public void setChecked(boolean checked) {
        if (isChecked != checked) {
            isChecked = checked;
            drawCheck();
        }
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {
        setChecked(!isChecked);
    }

    private void drawCheck() {
        if (isChecked) {
            blurView.setVisibility(View.VISIBLE);
        } else {
            blurView.setVisibility(View.GONE);
        }
    }

    public interface OnPersonItemClickListener {
        public void onPersonItemClick(View view, FollowPerson followPerson, int position);
    }

    OnPersonItemClickListener listener;

    public void setOnPersonItemClickListener(OnPersonItemClickListener listener) {
        this.listener = listener;
    }
}
