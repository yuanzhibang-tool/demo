package com.yuanzhibang.demo.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.gson.internal.LinkedTreeMap;
import com.yuanzhibang.demo.EnvHelper;

/**
 * Unit test for simple App.
 */
public class OauthApiHelperTest {

    @Test
    public void test_getApiByPath() {
        String api = OauthApiHelper.getApiByPath("/x");
        String expectApi = "https://oauth.yuanzhibang.com/x";
        assertEquals(api, expectApi);
    }

    @Test
    public void test_checkCode_valid() {
        String appId = EnvHelper.getEnvValue("TEST_APP_ID");
        String code = EnvHelper.getEnvValue("TEST_CODE");
        try {
            Object response = OauthApiHelper.checkCode(appId, code, "access_token", null);
            assertEquals(response.getClass(), LinkedTreeMap.class);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void test_checkCode_invalid() {
        String appId = EnvHelper.getEnvValue("TEST_APP_ID");
        String code = "123";
        try {
            boolean response = (boolean) OauthApiHelper.checkCode(appId, code, "access_token", null);
            assertFalse(response);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void test_getAppUserCount() {
        String appId = EnvHelper.getEnvValue("TEST_APP_ID");
        String code = EnvHelper.getEnvValue("TEST_CODE");
        try {
            int count = OauthApiHelper.getAppUserCount(appId, code, null);
            assertTrue(count >= 0);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void test_getAppUserList() {
        String appId = EnvHelper.getEnvValue("TEST_APP_ID");
        String code = EnvHelper.getEnvValue("TEST_CODE");
        try {
            ArrayList<Map<String, String>> response = OauthApiHelper.getAppUserList(appId, code, null);
            assertEquals(response.getClass(), ArrayList.class);
            assertEquals(response.get(0).getClass(), LinkedTreeMap.class);
            assertEquals(response.get(0).get("id").getClass(), String.class);
            assertEquals(response.get(0).get("open_id").getClass(), String.class);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void test_getUserAppAccess() {
        String appId = EnvHelper.getEnvValue("TEST_APP_ID");
        String openId = EnvHelper.getEnvValue("TEST_OPEN_ID");
        String code = EnvHelper.getEnvValue("TEST_CODE");
        try {
            ArrayList<String> response = OauthApiHelper.getUserAppAccess(appId, openId, code, null);
            assertEquals(response.get(0).getClass(), String.class);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void test_getUserIsAppAdded() {
        String appId = EnvHelper.getEnvValue("TEST_APP_ID");
        String openId = EnvHelper.getEnvValue("TEST_OPEN_ID");
        String code = EnvHelper.getEnvValue("TEST_CODE");
        try {
            Boolean response = OauthApiHelper.getUserIsAppAdded(appId, openId, code, null);
            assertEquals(response.getClass(), Boolean.class);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void test_getUserBaseInfo() {
        String appId = EnvHelper.getEnvValue("TEST_APP_ID");
        String openId = EnvHelper.getEnvValue("TEST_OPEN_ID");
        String code = EnvHelper.getEnvValue("TEST_CODE");
        try {
            Object response = OauthApiHelper.getUserBaseInfo(appId, openId, code, null);
            assertEquals(response.getClass(), LinkedTreeMap.class);
            Map<String, String> responseMap = (Map<String, String>) response;
            assertEquals(responseMap.get("nick").getClass(), String.class);
            assertEquals(responseMap.get("avatar").getClass(), String.class);
            assertEquals(responseMap.get("sex").getClass(), String.class);
        } catch (Exception e) {
            assertTrue(false);
        }
    }
}
