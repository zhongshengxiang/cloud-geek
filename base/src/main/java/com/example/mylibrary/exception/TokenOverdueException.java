package com.example.mylibrary.exception;

/**
 * Created by Administrator on 2017/2/15.
 */

public class TokenOverdueException extends RuntimeException {
    public TokenOverdueException(String message) {
        super(message);
    }
}
