package com.onemeter.omm.onemm.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.onemeter.omm.onemm.data.NetWorkResultType;
import com.onemeter.omm.onemm.data.SearchResult;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-08-30.
 */
public class SearchRequest extends AbstractRequest<NetWorkResultType<SearchResult[]>> {
    Request request;
    public SearchRequest(Context context,String word, String pageNo, String count){
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("users")
                .addQueryParameter("word", word)
                .addQueryParameter("pageNo", pageNo)
                .addQueryParameter("count", count)
                .build();

        request = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
    }
    @Override
    protected Type getType() {
        return new TypeToken<NetWorkResultType<SearchResult>>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
