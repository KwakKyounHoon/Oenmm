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
    public OtherPostRequest(Context context, String id, String direction, String answer, String pageNo, String count){
        HttpUrl url = getHttpsBaseUrlBuilder()
                .addPathSegment("questions")
                .addPathSegment(id)
                .addQueryParameter("direction", direction)
                .addQueryParameter("answer", answer)
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
        return new TypeToken<NetWorkResultType<Post[]>>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
