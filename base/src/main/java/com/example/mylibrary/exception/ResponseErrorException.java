package com.example.mylibrary.exception;

/**
 * Created by Vinctor on 2016/5/17.
 */
public class ResponseErrorException extends RuntimeException {

    public ResponseErrorException(String detailMessage) {
        super(detailMessage);
    }
}
