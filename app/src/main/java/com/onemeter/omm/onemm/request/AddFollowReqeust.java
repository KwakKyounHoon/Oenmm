package com.onemeter.omm.onemm.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.onemeter.omm.onemm.data.NetWorkResultType;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Tacademy on 2016-08-30.
 */
public class AddFollowReqeust extends AbstractRequest<NetWorkResultType> {
    Request request;
    public AddFollowReqeust(Context context, String uid){
        HttpUrl.Builder builder = getBaseUrlBuilder();
        builder.addPathSegment("users")
                .addPathSegment("me")
                .addPathSegment("follows");

        RequestBody body = new FormBody.Builder()
                .add("userId",uid)
                .build();

        request = new Request.Builder()
                .url(builder.build())
                .post(body)
                .tag(context)
                .build();
    }

    List<String> uids = new ArrayList<>();
    public AddFollowReqeust(Context context, List<String> uid){
        this.uids = uid;
        HttpUrl.Builder builder = getBaseUrlBuilder();
        builder.addPathSegment("users")
                .addPathSegment("me")
                .addPathSegment("follows");

        FormBody.Builder builder1 = new FormBody.Builder();

        for(int i=0; i<uids.size(); i++){
            builder1.add("userId",uids.get(i));
        }

        RequestBody body = builder1.build();

        request = new Request.Builder()
                .url(builder.build())
                .post(body)
                .tag(context)
                .build();
    }


    @Override
    protected Type getType() {
        return new TypeToken<NetWorkResultType>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
