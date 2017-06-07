package com.example.rx.model;


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
