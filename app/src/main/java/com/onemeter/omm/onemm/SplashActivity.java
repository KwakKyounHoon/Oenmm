package com.onemeter.omm.onemm;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.firebase.iid.FirebaseInstanceId;
import com.onemeter.omm.onemm.data.MyData;
import com.onemeter.omm.onemm.data.NetWorkResultType;
import com.onemeter.omm.onemm.manager.NetworkManager;
import com.onemeter.omm.onemm.manager.NetworkRequest;
import com.onemeter.omm.onemm.manager.PropertyManager;
import com.onemeter.omm.onemm.request.FacebookLoginRequest;
import com.onemeter.omm.onemm.request.MyDataReqeust;

public class SplashActivity extends AppCompatActivity {

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        loginManager = LoginManager.getInstance();
        callbackManager = CallbackManager.Factory.create();

        if (PropertyManager.getInstance().getRegistrationId().equals("")) {
            registerToken();
        } else {
            doRealStart();
        }
    }

    private void registerToken() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                PropertyManager.getInstance().setRegistrationId(refreshedToken);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                doRealStart();
            }
        }.execute();
    }

    private void doRealStart() {
        MyDataReqeust request = new MyDataReqeust(this);
        NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP, request, new NetworkManager.OnResultListener<NetWorkResultType<MyData>>() {
            @Override
            public void onSuccess(NetworkRequest<NetWorkResultType<MyData>> request, NetWorkResultType<MyData> result) {
                PropertyManager.getInstance().setMyId(result.getResult().getUserId());
                moveMainActivity();
            }

            @Override
            public void onFail(NetworkRequest<NetWorkResultType<MyData>> request, int errorCode, String errorMessage, Throwable e) {
                loginSharedPreference();
            }
        });
    }

    private void loginSharedPreference() {
        if (isFacebookLogin()) {
            processFacebookLogin();
        }  else {
            moveLoginActivity();
        }
    }

    private boolean isFacebookLogin() {
        if (!TextUtils.isEmpty(PropertyManager.getInstance().getFacebookId())) {
            return true;
        }
        return false;
    }

    private void processFacebookLogin() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if(accessToken == null || !accessToken.getUserId().equals(PropertyManager.getInstance().getFacebookId())){
            resetFacebookAndMoveLoginActivity();
            return;
        }
        if (accessToken != null) {
            String token = accessToken.getToken();
            FacebookLoginRequest request = new FacebookLoginRequest(this, token, PropertyManager.getInstance().getRegistrationId());
            NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP, request, new NetworkManager.OnResultListener<NetWorkResultType<String>>() {
                @Override
                public void onSuccess(NetworkRequest<NetWorkResultType<String>> request, NetWorkResultType<String> result) {
                    if(result.getResult().equals("1")) {
                        moveMainActivity();
                    } else {
                        resetFacebookAndMoveLoginActivity();
                    }
                }

                @Override
                public void onFail(NetworkRequest<NetWorkResultType<String>> request, int errorCode, String errorMessage, Throwable e) {
                    loginManager.logOut();
                    facebookLogin();
                }
            });
        } else {
            facebookLogin();
        }
    }

    LoginManager loginManager;
    CallbackManager callbackManager;

    private void resetFacebookAndMoveLoginActivity() {
        loginManager.logOut();
        PropertyManager.getInstance().setFacebookId("");
        moveLoginActivity();
    }

    private void moveLoginActivity() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        }, 1000);
    }

    private void facebookLogin() {
        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult result) {
                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                if (!accessToken.getUserId().equals(PropertyManager.getInstance().getFacebookId())) {
                    resetFacebookAndMoveLoginActivity();
                    return;
                }
                FacebookLoginRequest request = new FacebookLoginRequest(SplashActivity.this, accessToken.getToken(), PropertyManager.getInstance().getRegistrationId());

                NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP, request, new NetworkManager.OnResultListener<NetWorkResultType<String>>() {
                    @Override
                    public void onSuccess(NetworkRequest<NetWorkResultType<String>> request, NetWorkResultType<String> result) {
                        if(result.getResult().equals("1")) {
                            moveMainActivity();
                        } else {
                            resetFacebookAndMoveLoginActivity();
                        }
                    }

                    @Override
                    public void onFail(NetworkRequest<NetWorkResultType<String>> request, int errorCode, String errorMessage, Throwable e) {
                        loginManager.logOut();
                        facebookLogin();
                    }
                });

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        loginManager.logInWithReadPermissions(this, null);
    }

    private void moveMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}

