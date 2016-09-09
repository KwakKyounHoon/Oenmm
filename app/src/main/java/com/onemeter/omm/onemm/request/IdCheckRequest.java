package com.onemeter.omm.onemm.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.onemeter.omm.onemm.data.NetWorkResultType;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-09-07.
 */
public class IdCheckRequest extends AbstractRequest<NetWorkResultType<String>> {
    Request request;
    public IdCheckRequest(Context context, String nickname){
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("users")
                .addPathSegment("me")
                .addQueryParameter("nickname", nickname)
                .build();

        request = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
    }
    @Override
    protected Type getType() {
        return new TypeToken<NetWorkResultType<String>>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
