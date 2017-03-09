package com.example.myapplication.Model;

/**
 * Created by Vinctor on 2016/5/9.
 */
public class ResponseBean<T> {
    public boolean succeeded;
    public int errcode;
    public String errmsg;
    public T result;

    public void setResult(T ss) {
        result = ss;
    }

    public T getResult() {
        return result;
    }

}
