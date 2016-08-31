package com.onemeter.omm.onemm.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Tacademy on 2016-08-30.
 */
public class ModifyProfileRequest extends AbstractRequest<String> {
    Request request;
    public ModifyProfileRequest(Context context, String nickname, String name, String stateMessage, File voiceMessage){
        HttpUrl.Builder builder = getBaseUrlBuilder();
        builder.addPathSegment("users");
        builder.addPathSegment("me");

        RequestBody body = new FormBody.Builder()
                .add("nickname",nickname)
                .build();

        request = new Request.Builder()
                .url(builder.build())
                .put(body)
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
