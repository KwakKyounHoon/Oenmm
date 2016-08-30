package com.onemeter.omm.onemm.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.onemeter.omm.onemm.data.NetWorkResultType;
import com.onemeter.omm.onemm.data.SettingSave;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-08-30.
 */
public class SettingSaveRequest extends AbstractRequest<NetWorkResultType<SettingSave[]>> {
    Request request;
    public SettingSaveRequest(Context context){
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("users")
                .addPathSegment("me")
                .addQueryParameter("point", "1")
                .build();

        request = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
    }
    @Override
    protected Type getType() {
        return new TypeToken<NetWorkResultType<SettingSave[]>>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
