package com.onemeter.omm.onemm.data;

/**
 * Created by Tacademy on 2016-08-29.
 */
public class NetWorkResultType<T> {
    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    private T result;
}
