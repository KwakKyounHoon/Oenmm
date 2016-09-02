package com.onemeter.omm.onemm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.onemeter.omm.onemm.adapter.FollowPersonAdapter;
import com.onemeter.omm.onemm.data.FollowPerson;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FollowActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.check)
    ImageView check;

    RecyclerView listView;
    FollowPersonAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        ButterKnife.bind(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FollowActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FollowActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
        listView = (RecyclerView) findViewById(R.id.follow_list);
        mAdapter = new FollowPersonAdapter();
        listView.setAdapter(mAdapter);

        GridLayoutManager manager =
                new GridLayoutManager(this, 3);
        listView.setLayoutManager(manager);

        initData();
    }

    private void initData() {
        Random r = new Random();
        for (int i = 0; i < 27; i++) {
            FollowPerson p = new FollowPerson();
            p.setName("name ::" + i);
            p.setPhoto("photo :: " + i);
            mAdapter.add(p);
        }
    }
}
