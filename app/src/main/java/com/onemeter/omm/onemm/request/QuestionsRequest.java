package com.onemeter.omm.onemm.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Tacademy on 2016-08-30.
 */
public class QuestionsRequest extends AbstractRequest<String> {
    Request request;
    public QuestionsRequest(Context context, int price, Date date, String content, int responsorId){
        HttpUrl.Builder builder = getBaseUrlBuilder();
        builder.addPathSegment("questions");

        RequestBody body = new FormBody.Builder()
                .add("price",price+"")
                .add("date",date+"")
                .add("content",content)
                .add("responsor_id",responsorId+"")
                .build();

        request = new Request.Builder()
                .url(builder.build())
                .post(body)
                .tag(context)
                .build();
    }
    @Override
    protected Type getType() {
        return new TypeToken<String>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
