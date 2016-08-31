package com.onemeter.omm.onemm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.onemeter.omm.onemm.fragment.ProfileAfterFragment;
import com.onemeter.omm.onemm.fragment.ProfileBeforeFragment;
import com.onemeter.omm.onemm.fragment.ProfilingFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.onemeter.omm.onemm.R.id;
import static com.onemeter.omm.onemm.R.layout;

public class ProfileActivity extends AppCompatActivity {

    @BindView(id.back)
    ImageView back;
    @BindView(id.check)
    ImageView check;

    Fragment f;
    ImageView before, ing , after;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);


        f = new ProfileBeforeFragment();
                getSupportFragmentManager().beginTransaction()
                .add(R.id.container, f)
                .commit();


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

    public void setFrag (View view) {
        Fragment f = null;
        if(view == findViewById(R.id.image_before)) {
            f = new ProfilingFragment();

        } else if (view == findViewById(R.id.image_ing)) {
            f = new ProfileAfterFragment();

        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, f)
                .commit();

    }
}
