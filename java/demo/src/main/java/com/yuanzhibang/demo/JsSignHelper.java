package com.yuanzhibang.demo;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.UUID;
import java.net.*;

// !用来根据url生成js签名信息的类
public class JsSignHelper {

    public static String getPureUrl(String urlString) {
        try {
            // !此为当前页面的url,为去除掉锚点和参数的部分,如果获取的url最后为/结尾,去除
            URL url = new URL(urlString);
            String protocol = url.getProtocol();
            String authority = url.getAuthority();
            String path = url.getPath();
            String pureUrlString = String.format("%s://%s%s", protocol, authority, path);
            if (pureUrlString.endsWith("/")) {
                // !如果最后为/结尾则去掉
                pureUrlString = pureUrlString.substring(0, pureUrlString.length() - 1);
            }
            return pureUrlString;
        } catch (Exception e) {
            return null;
        }
    }

    public static HashMap<String, String> getJsSignInfo(String appId, String urlString, String jsTicket) {
        String noncestr = UUID.randomUUID().toString(); // 此为随机生成的字符串,最长为128个字符串;
        long timestamp = System.currentTimeMillis() / 1000L;
        try {
            String sha1 = JsSignHelper.getSign(jsTicket, noncestr, timestamp, urlString);
            HashMap<String, String> returnMap = new HashMap<String, String>();
            returnMap.put("app_id", appId);
            returnMap.put("timestamp", String.format("%d", timestamp));
            returnMap.put("nonce_str", noncestr);
            returnMap.put("signature", sha1);
            returnMap.put("url", urlString);
            return returnMap;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getSign(String jsTicket, String noncestr, long timestamp, String urlString) {
        try {
            String pureUrlString = JsSignHelper.getPureUrl(urlString);
            if (pureUrlString.endsWith("/")) {
                // !如果最后为/结尾则去掉
                pureUrlString = pureUrlString.substring(0, pureUrlString.length() - 1);
            }
            String signString = String.format("js_ticket=%s&nonce_str=%s&timestamp=%d&url=%s",
                    jsTicket, noncestr, timestamp, pureUrlString);
            String sha1 = "";
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(signString.getBytes("utf8"));
            sha1 = String.format("%040x", new BigInteger(1, digest.digest()));
            return sha1;
        } catch (Exception e) {
            return null;
        }
    }
}