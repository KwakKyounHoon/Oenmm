package com.onemeter.omm.onemm.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Tacademy on 2016-08-30.
 */
public class BlockRequest extends AbstractRequest<String> {
    Request request;
    public BlockRequest(Context context, int blockedId){
        HttpUrl.Builder builder = getBaseUrlBuilder();
        builder.addPathSegment("blocks");

        RequestBody body = new FormBody.Builder()
                .add("blockedId",blockedId+"")
                .build();

        request = new Request.Builder()
                .url(builder.build())
                .post(body)
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
