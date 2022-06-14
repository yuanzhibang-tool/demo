package com.mycompany.app;

import java.util.HashMap;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        RedisHelper.test();
        ServerRequestHelper.test();
        System.out.println("Hello World!");
        String urlString = "https://yuanzhibang.com/a/b/?x=1&b=2#2"; //
        HashMap<String, String> jsSignInfo = JsSignHelper.getJsSignInfo("100029", urlString);
    }
}
