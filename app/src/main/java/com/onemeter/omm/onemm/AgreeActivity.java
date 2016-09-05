package com.onemeter.omm.onemm;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AgreeActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.btn_agree)
    Button agree;
    @BindView(R.id.text_box1)
    TextView editUse;
    @BindView(R.id.text_box2)
    TextView editInfo;
    @BindView(R.id.check_use)
    CheckBox checkUse;
    @BindView(R.id.check_info)
    CheckBox checkInfo;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agree);
        ButterKnife.bind(this);
        
        editInfo.setMovementMethod(new ScrollingMovementMethod());
        editUse.setMovementMethod(new ScrollingMovementMethod());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        checkInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agreeCheck();
            }
        });

        checkUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agreeCheck();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AgreeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (agreeCheck()) {
                    Intent intent = new Intent(AgreeActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_1:
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.submenu_item_1:
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.submenu_item_2:
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean agreeCheck() {
        if (checkUse.isChecked() && checkInfo.isChecked()) {
            agree.setBackgroundColor(Color.parseColor("#f82040"));
            return true;

        } else {
            agree.setBackgroundColor(Color.BLACK);
            return false;
        }

    }
}