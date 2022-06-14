package com.mycompany.app;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

public class ServerRequestHelper {
    public static void test() {
        Unirest.setTimeouts(0, 0);
        try {
            HttpResponse<String> response = Unirest
                    .post("https://baidu.com")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .field("platform_id", "100")
                    .field("account", "124084")
                    .field("mobile", "17743504351")
                    .asString();
            System.out.println(response.getBody());
        } catch (Exception e) {

        }

    }
}
