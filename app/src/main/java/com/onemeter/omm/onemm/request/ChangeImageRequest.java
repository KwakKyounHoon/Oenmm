package com.onemeter.omm.onemm.request;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.gson.reflect.TypeToken;
import com.onemeter.omm.onemm.MyApplication;
import com.onemeter.omm.onemm.data.NetWorkResultType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Tacademy on 2016-09-02.
 */
public class ChangeImageRequest extends AbstractRequest<NetWorkResultType> {

    Request request;
    MediaType jpeg = MediaType.parse("image/jpeg");
    public ChangeImageRequest(Context context, File file){
        File resizingFIle = resizing(file.getPath());
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("users")
                .addPathSegment("me")
                .addQueryParameter("type","1")
                .build();

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        if (resizingFIle != null) {
            builder.addFormDataPart("photo", resizingFIle.getName(),
                    RequestBody.create(jpeg, resizingFIle));
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

    private File resizing(String url){
        Bitmap srcBmp = BitmapFactory.decodeFile(url);

        int iWidth = 300;   // 축소시킬 너비
        int iHeight = 300;   // 축소시킬 높이
        float fWidth  = srcBmp.getWidth();
        float fHeight = srcBmp.getHeight();

        // 원하는 널이보다 클 경우의 설정
        if(fWidth > iWidth) {
            float mWidth = (float) (fWidth / 100);
            float fScale = (float) (iWidth / mWidth);
            fWidth *= (fScale / 100);
            fHeight *= (fScale / 100);
        // 원하는 높이보다 클 경우의 설정
        }else if (fHeight > iHeight) {
            float mHeight = (float) (fHeight / 100);
            float fScale = (float) (iHeight / mHeight);
            fWidth *= (fScale / 100);
            fHeight *= (fScale / 100);
        }

        FileOutputStream fosObj = null;
        try {
            Bitmap resizedBmp = Bitmap.createScaledBitmap(srcBmp, (int)fWidth, (int)fHeight, true);;
            fosObj = new FileOutputStream(MyApplication.getContext().getCacheDir()+"/resizing");
            resizedBmp.compress(Bitmap.CompressFormat.JPEG, 100, fosObj);
        } catch (Exception e){
        } finally {
            try {
                fosObj.flush();
                fosObj.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File pathFile = new File(MyApplication.getContext().getCacheDir()+"/resizing");

        return pathFile;
    }
}
