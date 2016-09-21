package com.onemeter.omm.onemm;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.onemeter.omm.onemm.fragment.BackKeyFragment;
import com.onemeter.omm.onemm.fragment.TabHomeFragment;
import com.onemeter.omm.onemm.fragment.TabMyFragment;
import com.onemeter.omm.onemm.fragment.TabRankFragment;
import com.onemeter.omm.onemm.fragment.TabSearchFragment;
import com.tsengvn.typekit.TypekitContextWrapper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tabs)
    TabLayout tabs;

    public static String TAB_TAG_HOME = "home";
    public static String TAB_TAG_SEARCH = "search";
    public static String TAB_TAG_RANK = "rank";
    public static String TAB_TAG_MY = "my";

    BackKeyFragment currentFragment = null;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                String tag = (String) tab.getTag();

                if (tag.equals(TAB_TAG_HOME)) {
                    Fragment f = getSupportFragmentManager().findFragmentByTag(tag);
                    if (f != null) {
                        getSupportFragmentManager().beginTransaction()
                                .attach(f)
                                .commit();
                        currentFragment = (TabHomeFragment) f;
                    } else {
                        f = new TabHomeFragment();
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.container, f, (String) tab.getTag())
                                .commit();
                        currentFragment = (TabHomeFragment) f;
                    }
                } else if (tag.equals(TAB_TAG_MY)) {
                    Fragment f = getSupportFragmentManager().findFragmentByTag(tag);
                    if (f != null) {
                        getSupportFragmentManager().beginTransaction()
                                .attach(f)
                                .commit();
                        currentFragment = (TabMyFragment) f;
                    } else {
                        f = new TabMyFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, f, (String) tab.getTag())
                                .commit();
                        currentFragment = (TabMyFragment) f;
                    }
                } else if (tag.equals(TAB_TAG_SEARCH)) {
                    Fragment f = getSupportFragmentManager().findFragmentByTag(tag);
                    if (f != null) {
                        getSupportFragmentManager().beginTransaction()
                                .attach(f)
                                .commit();
                        currentFragment = (TabSearchFragment) f;
                    } else {
                        f = new TabSearchFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, f, (String) tab.getTag())
                                .commit();
                        currentFragment = (TabSearchFragment) f;
                    }
                } else if (tag.equals(TAB_TAG_RANK)) {
                    Fragment f = getSupportFragmentManager().findFragmentByTag(tag);
                    if (f != null) {
                        getSupportFragmentManager().beginTransaction()
                                .attach(f)
                                .commit();
                        currentFragment = (TabRankFragment) f;
                    } else {
                        f = new TabRankFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, f, (String) tab.getTag())
                                .commit();
                        currentFragment = (TabRankFragment) f;
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab == null) return;
                String tag = (String) tab.getTag();
                Fragment f = getSupportFragmentManager().findFragmentByTag(tag);
                if (f != null) {
                    getSupportFragmentManager().beginTransaction()
                            .detach(f)
                            .commit();
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                String tag = (String) tab.getTag();
                if(tag.equals(TAB_TAG_HOME)){
                    Fragment f = getSupportFragmentManager().findFragmentByTag(tag);
                    currentFragment = (TabHomeFragment) f;
                    currentFragment.popAll();
                }else if(tag.equals(TAB_TAG_SEARCH)){
                    Fragment f = getSupportFragmentManager().findFragmentByTag(tag);
                    currentFragment = (TabSearchFragment) f;
                    currentFragment.popAll();
                }else if(tag.equals(TAB_TAG_RANK)){
                    Fragment f = getSupportFragmentManager().findFragmentByTag(tag);
                    currentFragment = (TabRankFragment) f;
                    currentFragment.popAll();
                }else if(tag.equals(TAB_TAG_MY)){
                    Fragment f = getSupportFragmentManager().findFragmentByTag(tag);
                    currentFragment = (TabMyFragment) f;
                    currentFragment.popAll();
                }
                Fragment f = getSupportFragmentManager().findFragmentByTag(tag);
                if (f != null) {
                    getSupportFragmentManager().beginTransaction()
                            .attach(f)
                            .commit();
                }
            }
        });

        TabLayout.Tab home = tabs.newTab().setIcon(R.drawable.main_home_tab).setTag(TAB_TAG_HOME);
        TabLayout.Tab search = tabs.newTab().setIcon(R.drawable.main_search_tab).setTag(TAB_TAG_SEARCH);
        TabLayout.Tab rank = tabs.newTab().setIcon(R.drawable.main_rank_tab).setTag(TAB_TAG_RANK);
        TabLayout.Tab my = tabs.newTab().setIcon(R.drawable.main_my_tab).setTag(TAB_TAG_MY);

        tabs.addTab(home);
        tabs.addTab(search);
        tabs.addTab(rank);
        tabs.addTab(my);
        tabs.setSelectedTabIndicatorColor(Color.WHITE);

        checkPermission();
    }

    public void changeHomeAsUp(boolean isBack) {
        if (isBack) {
//            getSupportActionBar().show();
//            setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
//            getSupportActionBar().setHomeAsUpIndicator(android.R.drawable.ic_input_add);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
//            getSupportActionBar().show();
//            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    public void onBackPressed() {
        if (currentFragment != null) {
            boolean isProcessed = currentFragment.onBackPressed();
            if (isProcessed) return;
        }
//        super.onBackPressed();
        if (!isBackPressed) {
            Toast.makeText(this, "'뒤로'버튼 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
            isBackPressed = true;
            mHandler.sendEmptyMessageDelayed(MESSAGE_BACK_KEY_TIMEOUT, TIMEOUT_TIME);
        } else {
            mHandler.removeMessages(MESSAGE_BACK_KEY_TIMEOUT);
            finish();
        }
    }

    public void actionBarHide() {
//        getSupportActionBar().setShowHideAnimationEnabled(false);
//        getSupportActionBar().hide();
    }

    public static final int MESSAGE_BACK_KEY_TIMEOUT = 1;
    public static final int TIMEOUT_TIME = 2000;

    Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE_BACK_KEY_TIMEOUT :
                    isBackPressed = false;
                    break;
            }
        }
    };


    boolean isBackPressed = false;

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("퍼미션 승인 필요합니다.");
                builder.setMessage("카메라, 데이터 읽기, 데이터 쓰기, 오디오 녹음 하는데 승인이 필요합니다.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        requestPermission();
                    }
                });
                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        finishNoPermission();
                    }
                });

                builder.create().show();
                return;
            }
            requestPermission();
        }
    }

    private void finishNoPermission() {
        Toast.makeText(this, "no permission", Toast.LENGTH_SHORT).show();
        finish();
    }

    private static final int RC_PERMISSION = 100;
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA
        , Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, RC_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RC_PERMISSION) {
            if (grantResults != null && grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "grant permission", Toast.LENGTH_SHORT).show();
            } else {
                finishNoPermission();
            }
        }
    }
}
