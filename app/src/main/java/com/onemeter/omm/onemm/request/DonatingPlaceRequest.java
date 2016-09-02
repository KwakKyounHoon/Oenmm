package com.onemeter.omm.onemm.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.onemeter.omm.onemm.data.DonatingPlace;
import com.onemeter.omm.onemm.data.NetWorkResultType;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-09-01.
 */
public class DonatingPlaceRequest extends AbstractRequest<NetWorkResultType<DonatingPlace[]>> {
    Request request;
    public DonatingPlaceRequest(Context context, String id){
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("donations")
                .addPathSegment(id)
                .build();

        request = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
    }
    @Override
    protected Type getType() {
        return new TypeToken<NetWorkResultType<DonatingPlace[]>>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
