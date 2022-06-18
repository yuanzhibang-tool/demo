package com.yuanzhibang.demo.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OauthApiHelper {

    @SuppressWarnings("unchecked")
    public static Object checkCode(String appId, String code, String type, RequestProxy proxy) throws Exception {
        // 先获取token
        String api = OauthApiHelper.getApiByPath("/OAuth2/checkCode");
        Map<String, String> postData = new HashMap<String, String>();
        postData.put("code", code);
        postData.put("app_id", appId);
        postData.put("type", type);

        try {
            Map<String, String> responseData = (Map<String, String>) OauthApiHelper.apiRequest(api, postData, proxy);
            return responseData;
        } catch (OpenAuthErrorException e) {
            if (e.code.equals("4102")) {
                // !code无效
                return false;
            } else {
                throw e;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static Object getAppUserCount(String appId, String accessToken, RequestProxy proxy) throws Exception {
        // 先获取token
        String api = OauthApiHelper.getApiByPath("/CommonResource/getUserCount");
        Map<String, String> postData = new HashMap<String, String>();
        postData.put("app_id", appId);
        postData.put("access_token", accessToken);
        Map<String, Double> responseData = (Map<String, Double>) OauthApiHelper.apiRequest(api, postData, proxy);
        int count = responseData.get("user_count").intValue();
        return count;
    }

    @SuppressWarnings("unchecked")
    public static Object getAppUserList(String appId, String accessToken, RequestProxy proxy) throws Exception {
        // 先获取token
        String api = OauthApiHelper.getApiByPath("/CommonResource/getUserList");
        Map<String, String> postData = new HashMap<String, String>();
        postData.put("app_id", appId);
        postData.put("access_token", accessToken);
        postData.put("load_more_id", "0");
        postData.put("load_more_count", "100");

        ArrayList<Object> responseData = (ArrayList<Object>) OauthApiHelper.apiRequest(api, postData, proxy);
        return responseData;
    }

    @SuppressWarnings("unchecked")
    public static Object getUserAppAccess(String appId, String openId, String accessToken, RequestProxy proxy)
            throws Exception {
        // 先获取token
        String api = OauthApiHelper.getApiByPath("/UserResource/getAppAccess");
        Map<String, String> postData = new HashMap<String, String>();
        postData.put("open_id", openId);
        postData.put("app_id", appId);
        postData.put("access_token", accessToken);
        ArrayList<String> responseData = (ArrayList<String>) OauthApiHelper.apiRequest(api, postData, proxy);
        return responseData;
    }

    @SuppressWarnings("unchecked")
    public static Object getUserIsAppAdded(String appId, String openId, String accessToken, RequestProxy proxy)
            throws Exception {
        // 先获取token
        String api = OauthApiHelper.getApiByPath("/UserResource/getAppIsAdded");
        Map<String, String> postData = new HashMap<String, String>();
        postData.put("open_id", openId);
        postData.put("app_id", appId);
        postData.put("access_token", accessToken);
        Map<String, Boolean> responseData = (Map<String, Boolean>) OauthApiHelper.apiRequest(api, postData, proxy);
        Boolean isAdded = responseData.get("is_added");
        return isAdded;
    }

    @SuppressWarnings("unchecked")
    public static Object getUserBaseInfo(String appId, String openId, String accessToken, RequestProxy proxy)
            throws Exception {
        // 先获取token
        String api = OauthApiHelper.getApiByPath("/UserResource/getUserBaseInfo");
        Map<String, String> postData = new HashMap<String, String>();
        postData.put("open_id", openId);
        postData.put("app_id", appId);
        postData.put("access_token", accessToken);

        try {
            Map<String, String> responseData = (Map<String, String>) OauthApiHelper.apiRequest(api, postData, proxy);
            return responseData;
        } catch (OpenAuthErrorException e) {
            if (e.code.equals("4103")) {
                // !没有权限获取，提示用户授权
                return false;
            } else {
                throw e;
            }
        }
    }

    public static String getApiByPath(String path) {
        String api = String.format("https://oauth.yuanzhibang.com%s", path);
        return api;
    }

    @SuppressWarnings("unchecked")
    public static Object apiRequest(String url, Map<String, String> postData, RequestProxy proxy) throws Exception {
        Map<String, Object> response = (Map<String, Object>) ApiRequestHelper.post(url, postData, null, true, proxy);
        if (response == null) {
            throw new OpenAuthErrorException("0000", "网络错误");
        } else {
            String status = (String) response.get("status");
            String message = (String) response.get("message");
            Object responseData = response.get("data");
            if ("2000".equals(status)) {
                return responseData;
            } else {
                throw new OpenAuthErrorException(status, message);
            }
        }
    }

    public static void test() {
        String appId = "101170";
        String openId = "b3dFUWFoMW0vUFgwSGxzWlNOV3JLc2pFRENnSlp6Z2NBMFpsZ3NvQXVMVTR2RnJsUkRtQU5MS1Z3V2hSYzdtQ3hnQkZzelhjT0lXbTBGWmVOdHBRYTAwNys0NisramlxU21PZ3lrb1o5Q3FORC96bStTNW5ZbEtiRjRLeUQ5SVFsN1gyUHVld1lJaDkvWGJqZ0trNGx3eWZaUWhORDc1UjBWSGFDWVpFNlhnPQ";
        try {
            // Object result = OauthApiHelper.checkCode(appId,
            // "4ca1cc7e4439e743fe26e6b6fe8428d8905b272d226d0a7830957b945c184b21",
            // "access_token");
            // Object result = OauthApiHelper.getAppUserCount(appId);
            // Object result = OauthApiHelper.getAppUserList(appId);
            // Object result = OauthApiHelper.getUserAppAccess(appId, openId);
            // Object result = OauthApiHelper.getUserIsAppAdded(appId, openId);
            Object result = OauthApiHelper.getUserBaseInfo(appId, openId, null, null);
            result.getClass();
        } catch (Exception e) {
            e.getCause();
        }

    }
}
