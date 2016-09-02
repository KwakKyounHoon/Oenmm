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
 * Created by Tacademy on 2016-08-30.
 */
public class ModifyProfileRequest extends AbstractRequest<NetWorkResultType> {
    Request request;
    MediaType mediaType  = MediaType.parse("THREE_GPP");
    public ModifyProfileRequest(Context context, String nickname, String name, String stateMessage, File voiceMessage){
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("users")
                .addPathSegment("me")
                .addQueryParameter("type","0")
                .build();

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("nickname", nickname)
                .addFormDataPart("name", name)
                .addFormDataPart("stateMessage", stateMessage);

        if (voiceMessage != null) {
            builder.addFormDataPart("voiceMessage", voiceMessage.getName(),
                    RequestBody.create(mediaType, voiceMessage));
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
