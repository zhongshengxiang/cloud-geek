package com.example.myapplication.exception;

/**
 * Created by Vinctor on 2016/5/17.
 */
public class NullListException extends RuntimeException {
    public NullListException() {
        super();
    }

    public NullListException(String detailMessage) {
        super(detailMessage);
    }
}
