package com.onemeter.omm.onemm.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.onemeter.omm.onemm.data.Follower;
import com.onemeter.omm.onemm.data.NetWorkResultType;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-08-31.
 */
public class MyFollowerRequest extends AbstractRequest<NetWorkResultType<Follower[]>> {
    Request request;
    public MyFollowerRequest(Context context, int pageNo, int count){
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("users")
                .addPathSegment("me")
                .addPathSegment("follows")
                .addQueryParameter("direction", "from")
                .addQueryParameter("pageNo", pageNo+"")
                .addQueryParameter("count", count+"")
                .build();

        request = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
    }
    @Override
    protected Type getType() {
        return new TypeToken<NetWorkResultType<Follower[]>>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
