package com.onemeter.omm.onemm.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class OtherPageData {
    OtherData otherData;
    List<PostData> postDatas = new ArrayList<>();

    public OtherData getOtherData() {
        return otherData;
    }

    public void setOtherData(OtherData otherData) {
        this.otherData = otherData;
    }

    public List<PostData> getPostDatas() {
        return postDatas;
    }

    public void setPostDatas(List<PostData> postDatas) {
        this.postDatas = postDatas;
    }
}
