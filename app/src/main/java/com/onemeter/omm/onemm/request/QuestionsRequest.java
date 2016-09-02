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
public class QuestionsRequest extends AbstractRequest<NetWorkResultType> {
    Request request;
    public QuestionsRequest(Context context, String price, String date, String content, String responsorId){
        HttpUrl.Builder builder = getBaseUrlBuilder();
        builder.addPathSegment("questions");

        RequestBody body = new FormBody.Builder()
                .add("price",price)
                .add("date",date)
                .add("content",content)
                .add("responsor_id",responsorId)
                .build();

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
