package com.onemeter.omm.onemm.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.onemeter.omm.onemm.data.DonationRank;
import com.onemeter.omm.onemm.data.NetWorkResultType;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Kwak on 2016-08-29.
 */
public class RankDonationRequest extends AbstractRequest<NetWorkResultType<DonationRank>>{
    Request request;

    public RankDonationRequest(Context context){
        HttpUrl url = getHttpsBaseUrlBuilder()
                .addPathSegment("users")
                .addQueryParameter("category", "3")
                .build();

        request = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
    }
    @Override
    protected Type getType() {
        return new TypeToken<NetWorkResultType<DonationRank>>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
