package com.onemeter.omm.onemm.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-08-23.
 */
public class MyPageData {
    private MyData myData;
    private List<Post> posts = new ArrayList<>();

    public MyData getMyData() {
        return myData;
    }

    public void setMyData(MyData myData) {
        this.myData = myData;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
