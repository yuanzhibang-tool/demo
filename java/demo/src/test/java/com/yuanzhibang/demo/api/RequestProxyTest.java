package com.yuanzhibang.demo.api;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class RequestProxyTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void test_init() {
        RequestProxy proxy = new RequestProxy("proxy_host", 77, "username", "password");
        assertEquals(proxy.host, "proxy_host");
        assertEquals(proxy.port, 77);
        assertEquals(proxy.username, "username");
        assertEquals(proxy.password, "password");

    }
}
