package com.onemeter.omm.onemm.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class OtherPageData {


    public OtherData getOtherData() {
        return otherData;
    }

    public void setOtherData(OtherData otherData) {
        this.otherData = otherData;
    }

    OtherData otherData;
    List<Post> postDatas = new ArrayList<>();

    public List<Post> getPostDatas() {
        return postDatas;
    }

    public void setPostDatas(List<Post> postDatas) {
        this.postDatas = postDatas;
    }
}
