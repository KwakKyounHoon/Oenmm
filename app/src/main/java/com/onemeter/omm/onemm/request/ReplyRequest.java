package com.onemeter.omm.onemm.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.onemeter.omm.onemm.data.NetWorkResultType;

import java.io.File;
import java.lang.reflect.Type;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Tacademy on 2016-09-07.
 */
public class ReplyRequest extends AbstractRequest<NetWorkResultType> {
    Request request;

    public ReplyRequest(Context context, String questionId, String length, String date, File voiceContent){
        HttpUrl.Builder builder = getBaseUrlBuilder();
        builder.addPathSegment("answers");

        RequestBody body = new FormBody.Builder()
                .add("questionId",questionId)
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
