package com.onemeter.omm.onemm.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.onemeter.omm.onemm.data.NetWorkResultType;

import java.io.File;
import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Tacademy on 2016-09-07.
 */
public class ReplyRequest extends AbstractRequest<NetWorkResultType> {
    Request request;
    MediaType mediaType  = MediaType.parse("audio/three_gpp");
    public ReplyRequest(Context context, String questionId, String length, File voiceContent){
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("answers")
                .build();

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("questionId", questionId)
                .addFormDataPart("length", length);

        if (voiceContent != null) {
            builder.addFormDataPart("voiceContent", voiceContent.getName(),
                    RequestBody.create(mediaType, voiceContent));
        }
        RequestBody body = builder.build();
        request = new Request.Builder()
                .url(url)
                .put(body)
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
