package com.yuanzhibang.demo.api;

public class OpenAuthErrorException extends Exception {
    public String code;
    public String message;

    OpenAuthErrorException(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
