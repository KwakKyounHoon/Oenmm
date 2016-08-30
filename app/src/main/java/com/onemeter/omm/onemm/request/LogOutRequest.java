package com.onemeter.omm.onemm.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-08-30.
 */
public class LogOutRequest extends AbstractRequest<String> {
    Request request;
    public LogOutRequest(Context context){
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("auth")
                .addPathSegment("logout")
                .build();

        request = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
    }
    @Override
    protected Type getType() {
        return new TypeToken<String>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
