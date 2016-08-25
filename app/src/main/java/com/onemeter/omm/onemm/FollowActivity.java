package com.onemeter.omm.onemm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class FollowActivity extends ActionBarActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        toolbar.inflateMenu(R.menu.toolbar_profile);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                int id = item.getItemId();
                if (id == R.id.tool_check) {

                    Intent intent = new Intent(FollowActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } else if (id == R.id.tool_back) {

                    Intent intent = new Intent(FollowActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    finish();

                }
                return true;
            }
        });
    }
}
