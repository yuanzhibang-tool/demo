package com.mycompany.app.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OauthApiHelper {

    @SuppressWarnings("unchecked")
    public static Object checkCode(String appId, String code, String type) throws Exception {
        // 先获取token
        String api = OauthApiHelper.getApiByPath("/UserResource/getUserBaseInfo");
        Map<String, String> postData = new HashMap<String, String>();
        postData.put("code", code);
        postData.put("app_id", appId);
        postData.put("type", type);

        try {
            Map<String, String> responseData = (Map<String, String>) OauthApiHelper.apiRequest(api, postData);
            return responseData;
        } catch (OpenAuthErrorException e) {
            if (e.code == "4102") {
                // !code无效
                return false;
            } else {
                throw e;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static Object getAppUserCount(String appId) throws Exception {
        // 先获取token
        String api = OauthApiHelper.getApiByPath("/CommonResource/getUserCount");
        Map<String, String> postData = new HashMap<String, String>();
        postData.put("app_id", appId);
        postData.put("access_token", OauthApiHelper.getServerAccessToken(appId));
        Map<String, Integer> responseData = (Map<String, Integer>) OauthApiHelper.apiRequest(api, postData);
        Integer count = responseData.get("user_count");
        return count;
    }

    @SuppressWarnings("unchecked")
    public static Object getAppUserList(String appId) throws Exception {
        // 先获取token
        String api = OauthApiHelper.getApiByPath("/CommonResource/getUserList");
        Map<String, String> postData = new HashMap<String, String>();
        postData.put("app_id", appId);
        postData.put("access_token", OauthApiHelper.getServerAccessToken(appId));
        ArrayList<Object> responseData = (ArrayList<Object>) OauthApiHelper.apiRequest(api, postData);
        return responseData;
    }

    @SuppressWarnings("unchecked")
    public static Object getUserAppAccess(String appId, String openId) throws Exception {
        // 先获取token
        String api = OauthApiHelper.getApiByPath("/UserResource/getAppAccess");
        Map<String, String> postData = new HashMap<String, String>();
        postData.put("open_id", openId);
        postData.put("app_id", appId);
        postData.put("access_token", OauthApiHelper.getServerAccessToken(appId));
        ArrayList<String> responseData = (ArrayList<String>) OauthApiHelper.apiRequest(api, postData);
        return responseData;
    }

    @SuppressWarnings("unchecked")
    public static Object getUserIsAppAdded(String appId, String openId) throws Exception {
        // 先获取token
        String api = OauthApiHelper.getApiByPath("/UserResource/getAppIsAdded");
        Map<String, String> postData = new HashMap<String, String>();
        postData.put("open_id", openId);
        postData.put("app_id", appId);
        postData.put("access_token", OauthApiHelper.getServerAccessToken(appId));
        Map<String, Boolean> responseData = (Map<String, Boolean>) OauthApiHelper.apiRequest(api, postData);
        Boolean isAdded = responseData.get("is_added");
        return isAdded;
    }

    @SuppressWarnings("unchecked")
    public static Object getUserBaseInfo(String appId, String openId) throws Exception {
        // 先获取token
        String api = OauthApiHelper.getApiByPath("/UserResource/getUserBaseInfo");
        Map<String, String> postData = new HashMap<String, String>();
        postData.put("open_id", openId);
        postData.put("app_id", appId);
        postData.put("access_token", OauthApiHelper.getServerAccessToken(appId));

        try {
            Map<String, String> responseData = (Map<String, String>) OauthApiHelper.apiRequest(api, postData);
            return responseData;
        } catch (OpenAuthErrorException e) {
            if (e.code == "4103") {
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

    public static String getServerAccessToken(String appId) {
        return "d11141568b557a405715d35db3f1cc40e076a1957e637889c7c071beefdc0137";
    }

    @SuppressWarnings("unchecked")
    public static Object apiRequest(String url, Map<String, String> postData) throws Exception {
        Map<String, Object> response = (Map<String, Object>) ApiRequestHelper.post(url, postData, null, null);
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

}
