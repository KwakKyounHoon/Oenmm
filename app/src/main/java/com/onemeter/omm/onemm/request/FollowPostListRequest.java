package com.onemeter.omm.onemm.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.onemeter.omm.onemm.data.NetWorkResultType;
import com.onemeter.omm.onemm.data.Post;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-08-29.
 */
public class FollowPostListRequest extends AbstractRequest<NetWorkResultType<Post[]>> {
    Request request;

    public FollowPostListRequest(Context context, int pageNo, int count){
        HttpUrl url = getHttpsBaseUrlBuilder()
                .addPathSegment("questions")
                .addQueryParameter("direction", "to")
                .addQueryParameter("celebrity", "true")
                .addQueryParameter("pageNo", pageNo+"")
                .addQueryParameter("count", count+"")
                .build();

        request = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
    }

    @Override
    public Request getRequest() {
        return request;
    }


    @Override
    protected Type getType() {
        return new TypeToken<NetWorkResultType<Post[]>>(){}.getType();
    }

}
