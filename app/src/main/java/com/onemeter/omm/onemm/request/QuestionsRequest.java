package com.onemeter.omm.onemm.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.onemeter.omm.onemm.data.NetWorkResultType;

import java.lang.reflect.Type;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Tacademy on 2016-08-30.
 */
public class QuestionsRequest extends AbstractRequest<NetWorkResultType<String>> {
    Request request;
    public QuestionsRequest(Context context, String price, String content, String answernerId){
        HttpUrl.Builder builder = getBaseUrlBuilder();
        builder.addPathSegment("questions");

        RequestBody body = new FormBody.Builder()
                .add("price",price)
                .add("content",content)
                .add("answernerId",answernerId)
                .build();

        request = new Request.Builder()
                .url(builder.build())
                .post(body)
                .tag(context)
                .build();
    }
    @Override
    protected Type getType() {
        return new TypeToken<NetWorkResultType<String>>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
