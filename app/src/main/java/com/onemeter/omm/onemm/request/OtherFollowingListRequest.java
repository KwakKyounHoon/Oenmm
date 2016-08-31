package com.onemeter.omm.onemm.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.onemeter.omm.onemm.data.Following;
import com.onemeter.omm.onemm.data.NetWorkResultType;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-08-30.
 */
public class OtherFollowingListRequest extends AbstractRequest<NetWorkResultType<Following[]>> {
    Request request;
    public OtherFollowingListRequest(Context context, String id, int pageNo, int count){
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("follows")
                .addPathSegment(id)
                .addQueryParameter("direction", "to")
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
        return new TypeToken<NetWorkResultType<Following[]>>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
