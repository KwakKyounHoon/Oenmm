package com.onemeter.omm.onemm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.onemeter.omm.onemm.data.NetWorkResultType;
import com.onemeter.omm.onemm.manager.NetworkManager;
import com.onemeter.omm.onemm.manager.NetworkRequest;
import com.onemeter.omm.onemm.request.IdCheckRequest;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.btn_back)
    ImageView back;
    @BindView(R.id.btn_check)
    ImageView check;
    @BindView(R.id.edit_nickname)
    EditText nicknameView;

//    Fragment f;
//    ImageView before, ing , after;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        nicknameView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Toast.makeText(ProfileActivity.this, b+"", Toast.LENGTH_SHORT).show();
                if(!b){
                    IdCheckRequest request = new IdCheckRequest(ProfileActivity.this, nicknameView.getText().toString());
                    NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP, request, new NetworkManager.OnResultListener<NetWorkResultType>() {
                        @Override
                        public void onSuccess(NetworkRequest<NetWorkResultType> request, NetWorkResultType result) {
                            Toast.makeText(ProfileActivity.this, result.getMessage()+"", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFail(NetworkRequest<NetWorkResultType> request, int errorCode, String errorMessage, Throwable e) {

                        }
                    });

                }
            }
        });
//        f = new ProfileBeforeFragment();
//                getSupportFragmentManager().beginTransaction()
//                .add(R.id.container, f)
//                .commit();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, AgreeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, FollowActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

//    public void setFrag (View view) {
//        Fragment f = null;
//        if(view == findViewById(R.id.image_before)) {
//            f = new ProfilingFragment();
//
//        } else if (view == findViewById(R.id.image_ing)) {
//            f = new ProfileAfterFragment();
//
//        }
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.container, f)
//                .commit();
//
//    }
}
