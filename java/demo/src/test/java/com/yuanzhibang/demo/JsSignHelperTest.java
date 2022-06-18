package com.yuanzhibang.demo;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

public class JsSignHelperTest {
    @Test
    public void test_getPureUrl() {
        String url = "https://yuanzhibang.com/a/b/?x=1&v=x#12";
        String pureUrl = JsSignHelper.getPureUrl(url);
        String expectUrl = "https://yuanzhibang.com/a/b";
        assertEquals(pureUrl, expectUrl);
        url = "https://yuanzhibang.com:80/a/b/?x=1&v=x#12";
        pureUrl = JsSignHelper.getPureUrl(url);
        expectUrl = "https://yuanzhibang.com:80/a/b";
        assertEquals(pureUrl, expectUrl);
        url = "https://yuanzhibang.com:80/a/b/";
        pureUrl = JsSignHelper.getPureUrl(url);
        expectUrl = "https://yuanzhibang.com:80/a/b";
        assertEquals(pureUrl, expectUrl);
    }

    @Test
    public void test_getSign() {
        String sign = JsSignHelper.getSign(
                "a4dcdk", "1234", 1654850924, "https://yuanzhibang.com/a/b/?x=1&v=x#12");
        assertEquals(sign, "a8cb02e00c2759372954bf5516d110066b911aa4");
    }

    @Test
    public void test_getJsSignInfo() {
        String appId = EnvHelper.getEnvValue("TEST_APP_ID");
        String url = "https://yuanzhibang.com/a/b/?x=1&v=x#12";
        String testJsTickert = "123";
        HashMap<String, String> signInfo = JsSignHelper.getJsSignInfo(
                appId, url, testJsTickert);
        assertEquals(signInfo.get("app_id"), appId);
        assertEquals(signInfo.get("url"), url);
        long timeDiff = System.currentTimeMillis() / 1000L - Integer.parseInt(signInfo.get("timestamp").toString());
        assertEquals(timeDiff < 10, true);
        String expectSign = JsSignHelper.getSign(
                testJsTickert, signInfo.get("nonce_str"), Integer.parseInt(signInfo.get("timestamp")), url);
        assertEquals(signInfo.get("signature"), expectSign);
    }
}
