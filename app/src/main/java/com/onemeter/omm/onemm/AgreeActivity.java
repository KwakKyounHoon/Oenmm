package com.onemeter.omm.onemm;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
    ImageView checkUse;
    @BindView(R.id.check_info)
    ImageView checkInfo;

    Toolbar toolbar;
    Boolean info = false;
    Boolean use = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agree);
        ButterKnife.bind(this);

        editInfo.setMovementMethod(new ScrollingMovementMethod());
        editUse.setMovementMethod(new ScrollingMovementMethod());
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);

        checkInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!info) {
                    checkInfo.setImageResource(R.drawable.ic_checkbox_on);
                    info = true;
                } else  {
                    checkInfo.setImageResource(R.drawable.ic_checkbox_off);
                    info = false;
                }
                agreeCheck();
            }
        });
        checkUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!use) {
                    checkUse.setImageResource(R.drawable.ic_checkbox_on);
                    use = true;
                } else  {
                    checkUse.setImageResource(R.drawable.ic_checkbox_off);
                    use = false;
                }
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

    public boolean agreeCheck() {
        if (use && info) {
            agree.setBackgroundColor(Color.parseColor("#f82040"));
            return true;

        } else {
            agree.setBackgroundColor(Color.BLACK);
            return false;
        }

    }
}