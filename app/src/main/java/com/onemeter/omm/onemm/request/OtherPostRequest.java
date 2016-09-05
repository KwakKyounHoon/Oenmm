package com.onemeter.omm.onemm.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.onemeter.omm.onemm.data.NetWorkResultType;
import com.onemeter.omm.onemm.data.Post;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-08-30.
 */
public class OtherPostRequest extends AbstractRequest<NetWorkResultType<Post[]>> {
    Request request;
    public OtherPostRequest(Context context, String id, String direction, String type, int pageNo, int count){
        HttpUrl url = getHttpsBaseUrlBuilder()
                .addPathSegment("users")
                .addPathSegment(id)
                .addPathSegment("questions")
                .addQueryParameter("direction", direction)
                .addQueryParameter("type", type)
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
        return new TypeToken<NetWorkResultType<Post[]>>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
