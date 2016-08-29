package com.onemeter.omm.onemm.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.onemeter.omm.onemm.data.NetWorkResultType;
import com.onemeter.omm.onemm.data.OtherData;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-08-29.
 */
public class OtherDataRequest extends AbstractRequest<NetWorkResultType<OtherData>> {
    Request request;

    public OtherDataRequest(Context context, String id){
        HttpUrl url = getHttpsBaseUrlBuilder()
                .addPathSegment("users")
                .addQueryParameter("id", id)
                .build();

        request = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
    }

    @Override
    protected Type getType() {
        return new TypeToken<NetWorkResultType<OtherData>>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
