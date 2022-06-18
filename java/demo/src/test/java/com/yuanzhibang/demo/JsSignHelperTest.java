package com.yuanzhibang.demo;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class JsSignHelperTest {
    @Test
    public void test_getPureUrl() {
        String url = "https://yuanzhibang.com/a/b/?x=1&v=x#12";
        String pureUrl = JsSignHelper.getPureUrl(url);
        String expectUrl = "https://yuanzhibang.com/a/b";
        assertEquals(pureUrl, expectUrl);
    }
}
