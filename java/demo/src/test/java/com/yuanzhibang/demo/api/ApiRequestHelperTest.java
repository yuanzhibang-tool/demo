package com.yuanzhibang.demo.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.yuanzhibang.demo.EnvHelper;

/**
 * Unit test for simple App.
 */
public class ApiRequestHelperTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void test_post_proxy() {
        String proxyHost = EnvHelper.getEnvValue("TEST_PROXY_HOST");
        String proxyPort = EnvHelper.getEnvValue("TEST_PROXY_PORT");
        String proxyUser = EnvHelper.getEnvValue("TEST_PROXY_USER");
        String proxyPassword = EnvHelper.getEnvValue("TEST_PROXY_PASSWORD");
        RequestProxy proxy = new RequestProxy(proxyHost, Integer.parseInt(proxyPort), proxyUser, proxyPassword);
        String url = "https://api-service.yuanzhibang.com/api/v1/Ip/getClientIp";
        String ip = (String) ApiRequestHelper.post(url, null, null, false, proxy);
        assertEquals(ip, EnvHelper.getEnvValue("TEST_PROXY_IP"));
    }
}
