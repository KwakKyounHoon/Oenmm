package com.onemeter.omm.onemm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.DefaultAudience;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.onemeter.omm.onemm.data.NetWorkResultType;
import com.onemeter.omm.onemm.manager.NetworkManager;
import com.onemeter.omm.onemm.manager.NetworkRequest;
import com.onemeter.omm.onemm.manager.PropertyManager;
import com.onemeter.omm.onemm.request.FacebookLoginRequest;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.btn_login)
    ImageButton login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mLoginManager = LoginManager.getInstance();
        callbackManager = CallbackManager.Factory.create();

//                Intent intent = new Intent(LoginActivity.this, AgreeActivity.class);
//                startActivity(intent);
//                finish();

    }

    @OnClick(R.id.btn_login)
    public void loginClick(View view){
        loginFacebook();
    }

    LoginManager mLoginManager;
    CallbackManager callbackManager;

    private void loginFacebook() {
        mLoginManager.setDefaultAudience(DefaultAudience.FRIENDS);
        mLoginManager.setLoginBehavior(LoginBehavior.NATIVE_WITH_FALLBACK);
        mLoginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                processAfterFacebookLogin();
//                Toast.makeText(LoginActivity.this, "login manager...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        mLoginManager.logInWithReadPermissions(this, Arrays.asList("email"));
    }

    private void processAfterFacebookLogin() {
        final AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            String token = accessToken.getToken();
            FacebookLoginRequest request = new FacebookLoginRequest(LoginActivity.this, token);
            NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetWorkResultType>() {
                @Override
                public void onSuccess(NetworkRequest<NetWorkResultType> request, NetWorkResultType result) {
                    String facebookId = accessToken.getUserId();
                    PropertyManager.getInstance().setFacebookId(facebookId);
                    Intent intent = new Intent(LoginActivity.this, AgreeActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onFail(NetworkRequest<NetWorkResultType> request, int errorCode, String errorMessage, Throwable e) {
                }
            });

//                    Toast.makeText(LoginActivity.this, token, Toast.LENGTH_SHORT).show();
//            Log.i("test",token);
//                    Toast.makeText(LoginActivity.this, accessToken.getUserId(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
        Toast.makeText(this,data.getStringExtra("email"),Toast.LENGTH_SHORT).show();
    }
}
