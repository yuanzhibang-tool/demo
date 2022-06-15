<?php

class OauthApiHelper
{

    public static function checkCode($appId, $code, $type)
    {
        // 先获取token
        $api = OauthApiHelper::getApiByPath("/OAuth2/checkCode");
        $postData = [
            'app_id' => $appId,
            'code' => $code,
            'type' => $type
        ];
        try {
            $responseData = OauthApiHelper::apiRequest($api, $postData);
            return $responseData;
        } catch (DHOpenAuthErrorException $e) {
            $statusCode = $e->getCode();
            if ($statusCode == "4102") {
                # !code无效
                return false;
            } else {
                throw $e;
            }
        }
    }


    public static function getAppUserCount($appId)
    {
        // 先获取token
        $api = OauthApiHelper::getApiByPath("/CommonResource/getUserCount");
        $postData = [
            'app_id' => $appId,
            'access_token' => OauthApiHelper::getServerAccessToken($appId)
        ];
        $responseData = OauthApiHelper::apiRequest($api, $postData);
        $count = $responseData['user_count'];
        return $count;
    }

    public static function getAppUserList($appId)
    {
        // 先获取token
        $api = OauthApiHelper::getApiByPath("/CommonResource/getUserList");
        $postData = [
            'app_id' => $appId,
            'access_token' => OauthApiHelper::getServerAccessToken($appId),
            'load_more_id' => 0,
            'load_more_count' => 100
        ];
        $responseData = OauthApiHelper::apiRequest($api, $postData);
        return $responseData;
    }

    public static function getUserAppAccess($appId, $openId)
    {
        // 先获取token
        $api = OauthApiHelper::getApiByPath("/UserResource/getAppAccess");
        $postData = [
            'open_id' => $openId,
            'app_id' => $appId,
            'access_token' => OauthApiHelper::getServerAccessToken($appId)
        ];
        $responseData = OauthApiHelper::apiRequest($api, $postData);
        return $responseData;
    }

    public static function getUserIsAppAdded($appId, $openId)
    {
        // 先获取token
        $api = OauthApiHelper::getApiByPath("/UserResource/getAppIsAdded");
        $postData = [
            'open_id' => $openId,
            'app_id' => $appId,
            'access_token' => OauthApiHelper::getServerAccessToken($appId)
        ];
        $responseData = OauthApiHelper::apiRequest($api, $postData);
        return $responseData['is_added'];
    }


    public static function getUserBaseInfo($appId, $openId)
    {
        // 先获取token
        $api = OauthApiHelper::getApiByPath("/UserResource/getUserBaseInfo");
        $postData = [
            'open_id' => $openId,
            'app_id' => $appId,
            'access_token' => OauthApiHelper::getServerAccessToken($appId)
        ];
        try {
            $responseData = OauthApiHelper::apiRequest($api, $postData);
            return $responseData;
        } catch (DHOpenAuthErrorException $e) {
            $statusCode = $e->getCode();
            if ($statusCode == "4103") {
                # !没有权限获取，提示用户授权
                return false;
            } else {
                throw $e;
            }
        }
    }

    public static function getApiByPath($path)
    {
        $api = "https://oauth.yuanzhibang.com$path";
        return $api;
    }

    public static function getServerAccessToken($appId)
    {
        return "d11141568b557a405715d35db3f1cc40e076a1957e637889c7c071beefdc0137";
    }

    public static function apiRequest($url, $postData)
    {
        $response = ApiRequestHelper::post($url, $postData, []);
        $responseInfo = json_decode($response, true);
        if ($responseInfo == null) {
            throw new DHOpenAuthErrorException('网络错误', "0000", $responseInfo);
        } else {
            $status = $responseInfo['status'];
            $message = $responseInfo['message'];
            $responseData = $responseInfo['data'];
            if ("2000" == $status) {
                return $responseData;
            } else {
                throw new DHOpenAuthErrorException($message, $status, $responseInfo);
            }
        }
    }
}
