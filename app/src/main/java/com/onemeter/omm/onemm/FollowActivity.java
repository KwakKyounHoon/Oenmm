package com.onemeter.omm.onemm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.onemeter.omm.onemm.adapter.FollowPersonAdapter;
import com.onemeter.omm.onemm.data.FollowPerson;
import com.onemeter.omm.onemm.data.NetWorkResultType;
import com.onemeter.omm.onemm.manager.NetworkManager;
import com.onemeter.omm.onemm.manager.NetworkRequest;
import com.onemeter.omm.onemm.request.FollowRecommendRequest;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class FollowActivity extends AppCompatActivity {

    RecyclerView listView;
    FollowPersonAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        ButterKnife.bind(this);

        listView = (RecyclerView) findViewById(R.id.follow_list);
        mAdapter = new FollowPersonAdapter();
        listView.setAdapter(mAdapter);

        GridLayoutManager manager =
                new GridLayoutManager(this, 3);
        listView.setLayoutManager(manager);

        initData();
    }

    @OnClick(R.id.btn_back)
    public void backClick(View view){
        Intent intent = new Intent(FollowActivity.this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.btn_check)
    public void checkClick(View view){
        Intent intent = new Intent(FollowActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    private void initData() {
//        Random r = new Random();
//        for (int i = 0; i < 27; i++) {
//            FollowPerson p = new FollowPerson();
//            p.setName("name ::" + i);
//            p.setPhoto("photo :: " + i);
//            mAdapter.add(p);
//        }
        FollowRecommendRequest request = new FollowRecommendRequest(this);
        NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP, request, new NetworkManager.OnResultListener<NetWorkResultType<FollowPerson[]>>() {
            @Override
            public void onSuccess(NetworkRequest<NetWorkResultType<FollowPerson[]>> request, NetWorkResultType<FollowPerson[]> result) {
                mAdapter.addAll(result.getResult());
            }

            @Override
            public void onFail(NetworkRequest<NetWorkResultType<FollowPerson[]>> request, int errorCode, String errorMessage, Throwable e) {

            }
        });
    }
}
