package com.onemeter.omm.onemm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AgreeActivity extends AppCompatActivity {
    Toolbar toolbar;
    @BindView(R.id.btn_agree)
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agree);
        ButterKnife.bind(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AgreeActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.toolbar_profile);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                int id = item.getItemId();
                if (id == R.id.tool_back) {

                    Intent intent = new Intent(AgreeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                }
                return true;
            }
        });
    }
}