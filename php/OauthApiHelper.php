<?php

require_once dirname(dirname(__FILE__)) . '/php/ApiRequestHelper.php';
require_once dirname(dirname(__FILE__)) . '/php/OpenAuthErrorException.php';

class OauthApiHelper
{

    public static function checkCode($appId, $code, $type, $proxy = null)
    {
        // 先获取token
        $api = OauthApiHelper::getApiByPath("/OAuth2/checkCode");
        $postData = [
            'app_id' => $appId,
            'code' => $code,
            'type' => $type
        ];
        try {
            $responseData = OauthApiHelper::apiRequest($api, $postData, $proxy);
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


    public static function getAppUserCount($appId, $accessToken, $proxy = null)
    {
        // 先获取token
        $api = OauthApiHelper::getApiByPath("/CommonResource/getUserCount");
        $postData = [
            'app_id' => $appId,
            'access_token' => $accessToken,
        ];
        $responseData = OauthApiHelper::apiRequest($api, $postData, $proxy);
        $count = $responseData['user_count'];
        return $count;
    }

    public static function getAppUserList($appId, $accessToken,  $proxy = null)
    {
        // 先获取token
        $api = OauthApiHelper::getApiByPath("/CommonResource/getUserList");
        $postData = [
            'app_id' => $appId,
            'access_token' => $accessToken,
            'load_more_id' => 0,
            'load_more_count' => 100
        ];
        $responseData = OauthApiHelper::apiRequest($api, $postData, $proxy);
        return $responseData;
    }

    public static function getUserAppAccess($appId, $openId, $accessToken, $proxy = null)
    {
        // 先获取token
        $api = OauthApiHelper::getApiByPath("/UserResource/getAppAccess");
        $postData = [
            'open_id' => $openId,
            'app_id' => $appId,
            'access_token' => $accessToken
        ];
        $responseData = OauthApiHelper::apiRequest($api, $postData, $proxy);
        return $responseData;
    }

    public static function getUserIsAppAdded($appId, $openId, $accessToken, $proxy = null)
    {
        // 先获取token
        $api = OauthApiHelper::getApiByPath("/UserResource/getAppIsAdded");
        $postData = [
            'open_id' => $openId,
            'app_id' => $appId,
            'access_token' => $accessToken
        ];
        $responseData = OauthApiHelper::apiRequest($api, $postData, $proxy);
        return $responseData['is_added'];
    }


    public static function getUserBaseInfo($appId, $openId, $accessToken, $proxy = null)
    {
        // 先获取token
        $api = OauthApiHelper::getApiByPath("/UserResource/getUserBaseInfo");
        $postData = [
            'open_id' => $openId,
            'app_id' => $appId,
            'access_token' => $accessToken
        ];
        try {
            $responseData = OauthApiHelper::apiRequest($api, $postData, $proxy);
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

    public static function apiRequest($url, $postData, $proxy = null)
    {
        $response = ApiRequestHelper::post($url, $postData, [], $proxy);
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
