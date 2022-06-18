package com.yuanzhibang.demo.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

public class ApiRequestHelper {

    @SuppressWarnings("unchecked")
    public static Object post(String url, Map<String, String> params, Map<String, String> headers,
            boolean parseJson, RequestProxy proxy) {
        try {
            CloseableHttpClient httpClient;
            HttpPost request = new HttpPost(url);
            if (proxy != null) {
                CredentialsProvider credsProvider = new BasicCredentialsProvider();
                credsProvider.setCredentials(
                        new AuthScope(proxy.host, proxy.port),
                        new UsernamePasswordCredentials(proxy.username, proxy.password));
                httpClient = HttpClients.custom()
                        .setDefaultCredentialsProvider(credsProvider)
                        .setProxyAuthenticationStrategy(ProxyAuthenticationStrategy.INSTANCE)
                        .build();
                HttpHost proxyHost = new HttpHost(proxy.host, proxy.port);

                RequestConfig config = RequestConfig.custom()
                        .setProxy(proxyHost)
                        .build();
                request.setConfig(config);

            } else {
                httpClient = HttpClients.custom()
                        .build();
            }

            request.setHeader("Content-Type", "multipart/form-data");
            if (params != null) {
                List<NameValuePair> postParams = new ArrayList<>();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    postParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                request.setEntity(new UrlEncodedFormEntity(postParams));
            }
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    request.setHeader(entry.getKey(), entry.getValue());
                }
            }
            try {
                HttpResponse response = httpClient.execute(request);
                String responseString = EntityUtils.toString(response.getEntity());
                try {
                    if (parseJson) {
                        Gson gson = new Gson();
                        Map<String, String> info = gson.fromJson(responseString, Map.class);
                        return info;
                    } else {
                        return responseString;
                    }
                } catch (Exception e) {
                    return null;
                }
            } catch (IOException e) {
                return null;
            } finally {
                request.releaseConnection();
            }
        } catch (UnsupportedEncodingException e) {
            return null;
        }

    }

    // public static void test() {
    // RequestProxy proxy = new RequestProxy("demo-proxy", 7789, "123", "12345678");
    // ApiRequestHelper.post("https://api-service.yuanzhibang.com/api/v1/Ip/getClientIp",
    // null, null, proxy);
    // }
}
