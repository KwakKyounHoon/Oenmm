package com.onemeter.omm.onemm.request;

import com.google.gson.Gson;
import com.onemeter.omm.onemm.manager.NetworkRequest;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.ResponseBody;

/**
 * Created by Tacademy on 2016-08-09.
 */
public abstract class AbstractRequest<T> extends NetworkRequest<T> {
    private String DUMY = "ec2-52-78-135-2.ap-northeast-2.compute.amazonaws.com";
    private String REALURL = "ec2-52-78-139-46.ap-northeast-2.compute.amazonaws.com";

    protected HttpUrl.Builder getHttpsBaseUrlBuilder() {
        HttpUrl.Builder builder = new HttpUrl.Builder();
        builder.scheme("https");
//        builder.host(DUMY);
        builder.host(REALURL);
        builder.port(4433);
        return builder;
    }

    protected HttpUrl.Builder getBaseUrlBuilder() {
        HttpUrl.Builder builder = new HttpUrl.Builder();
        builder.scheme("http");
//        builder.host(DUMY);
        builder.host(REALURL);
        builder.port(8080);
        return builder;
    }

    @Override
    protected T parse(ResponseBody body) throws IOException {
        String text = body.string();
        Gson gson = new Gson();
        T result = gson.fromJson(text, getType());
        return result;
    }

    protected abstract Type getType();

}
