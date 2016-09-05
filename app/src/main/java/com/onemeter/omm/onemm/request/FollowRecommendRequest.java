package com.onemeter.omm.onemm.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.onemeter.omm.onemm.data.FollowPerson;
import com.onemeter.omm.onemm.data.NetWorkResultType;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-08-30.
 */
public class FollowRecommendRequest extends AbstractRequest<NetWorkResultType<FollowPerson[]>> {
    Request request;
    public FollowRecommendRequest(Context context){
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("users")
                .addQueryParameter("type", "0")
                .build();

        request = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
    }
    @Override
    protected Type getType() {
        return new TypeToken<NetWorkResultType<FollowPerson[]>>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
