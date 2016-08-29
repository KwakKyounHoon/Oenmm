package com.onemeter.omm.onemm.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.onemeter.omm.onemm.data.MyData;
import com.onemeter.omm.onemm.data.NetWorkResultType;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-08-29.
 */
public class MyDataReqeust extends AbstractRequest<NetWorkResultType<MyData>> {
    Request request;
    public MyDataReqeust(Context context){
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("users")
                .addPathSegment("me")
                .build();

        request = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
    }
    @Override
    protected Type getType() {
        return new TypeToken<NetWorkResultType<MyData>>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
