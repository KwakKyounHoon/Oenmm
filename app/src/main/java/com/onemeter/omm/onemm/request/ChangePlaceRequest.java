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
 * Created by Tacademy on 2016-09-12.
 */
public class ChangePlaceRequest extends AbstractRequest<NetWorkResultType> {
    Request request;
    public ChangePlaceRequest(Context context, String donationId){
        HttpUrl.Builder builder = getBaseUrlBuilder();
        builder.addPathSegment("users")
                .addPathSegment("me")
                .addQueryParameter("type","3");

        RequestBody body = new FormBody.Builder()
                .add("donationId",donationId)
                .build();

        request = new Request.Builder()
                .url(builder.build())
                .put(body)
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
