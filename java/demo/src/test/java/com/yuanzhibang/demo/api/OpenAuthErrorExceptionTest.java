package com.yuanzhibang.demo.api;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class OpenAuthErrorExceptionTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void test_init() {
        OpenAuthErrorException error = new OpenAuthErrorException("code", "error message");
        assertEquals(error.code, "code");
        assertEquals(error.message, "error message");

    }
}
