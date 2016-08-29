package com.onemeter.omm.onemm.data;

public class PostResult {
    private Post[] result;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Post[] getResult() {
        return this.result;
    }

    public void setResult(Post[] result) {
        this.result = result;
    }
}
