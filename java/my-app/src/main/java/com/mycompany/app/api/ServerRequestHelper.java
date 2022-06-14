package com.mycompany.app.api;

import java.io.IOException;
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

public class ServerRequestHelper {

    public static Object post(String url, Map<String, String> params, Map<String, String> headers, RequestProxy proxy) {
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
                    // request.SE(entry.getKey(), entry.getValue());
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
                responseString.getClass();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                request.releaseConnection();
            }
            return null;
        } catch (Exception e) {
            return null;
        }

    }

    public static void test() {
        // building http client:Y@x.orzzzzzz.com:7789
        RequestProxy proxy = new RequestProxy("x.orzzzzzz.com", 7789, "proxy_user", "F2pkto4GtRPAqTpY");
        ServerRequestHelper.post("https://api-service.yuanzhibang.com/api/v1/Ip/getClientIp", null, null, proxy);
    }
}
