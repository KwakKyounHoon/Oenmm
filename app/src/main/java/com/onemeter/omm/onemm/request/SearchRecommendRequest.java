package com.onemeter.omm.onemm.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.onemeter.omm.onemm.data.NetWorkResultType;
import com.onemeter.omm.onemm.data.SearchRecommend;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-08-30.
 */
public class SearchRecommendRequest extends AbstractRequest<NetWorkResultType<SearchRecommend[]>> {
    Request request;
    public SearchRecommendRequest(Context context){
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("users")
                .addQueryParameter("type", "1")
                .build();

        request = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
    }
    @Override
    protected Type getType() {
        return new TypeToken<NetWorkResultType<SearchRecommend[]>>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
