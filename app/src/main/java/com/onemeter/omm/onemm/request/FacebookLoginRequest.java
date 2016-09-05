package com.onemeter.omm.onemm.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.onemeter.omm.onemm.data.NetWorkResultType;

import java.lang.reflect.Type;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Tacademy on 2016-08-22.
 */
public class FacebookLoginRequest extends AbstractRequest<NetWorkResultType> {
    Request mRequest;

    public FacebookLoginRequest(Context context, String tokeon) {
        HttpUrl url = getHttpsBaseUrlBuilder()
                .addPathSegment("auth")
                .addPathSegment("facebook")
                .addPathSegment("token")
                .build();
        RequestBody body = new FormBody.Builder()
                .add("access_token", tokeon)
                .build();
        mRequest = new Request.Builder()
                .url(url)
                .post(body)
                .tag(context)
                .build();
    }



    @Override
    protected Type getType() {
        return new TypeToken<NetWorkResultType>() {
        }.getType();
    }


    @Override
    public Request getRequest() {
        return mRequest;
    }
}