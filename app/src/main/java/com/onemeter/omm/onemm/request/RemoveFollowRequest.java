package com.onemeter.omm.onemm.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.onemeter.omm.onemm.data.NetWorkResultType;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-09-01.
 */
public class RemoveFollowRequest extends AbstractRequest<NetWorkResultType>{
    Request request;
    public RemoveFollowRequest(Context context, String uid){
        HttpUrl url = getHttpsBaseUrlBuilder()
                .addPathSegment("users")
                .addPathSegment(uid)
                .addPathSegment("follows")
                .build();
        request = new Request.Builder()
                .url(url)
                .delete()
                .tag(context)
                .build();
    }
    @Override
    protected Type getType() {
        return new TypeToken<NetWorkResultType>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
