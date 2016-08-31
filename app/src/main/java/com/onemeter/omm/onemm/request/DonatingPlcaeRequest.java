package com.onemeter.omm.onemm.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.onemeter.omm.onemm.data.DonatingPlace;
import com.onemeter.omm.onemm.data.NetWorkResultType;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-08-30.
 */
public class DonatingPlcaeRequest extends AbstractRequest<NetWorkResultType<DonatingPlace[]>> {
    Request request;
    public DonatingPlcaeRequest(Context context, int pageNo, int count){
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("donations")
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
        return new TypeToken<NetWorkResultType<DonatingPlace>>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
