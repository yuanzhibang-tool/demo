<?php

require_once dirname(dirname(__FILE__)) . '/php/OpenAuthErrorException.php';

class OauthApiHelper
{

    // !依赖php-curl拓展
    // !$proxy 格式为 ['ADDRESS' => 'proxy_host:proxy_port','AUTH' => 'proxy_user:proxy_password']
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
        $response = OauthApiHelper::post($url, $postData, [], $proxy);
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

    // !依赖php-curl拓展
    public static function post($url, $params = array(), $headers = array(), $proxy = null)
    {
        if (!is_array($params)) {
            throw new Exception("参数必须为array");
        }
        if (!is_array($headers)) {
            throw new Exception("headers必须为array");
        }
        //处理头信息
        $processedHeaders = [];
        foreach ($headers as $key => $value) {
            $processedHeaders[] = "$key:" . $value;
        }
        $httph = curl_init($url);
        if (!is_null($proxy)) {
            $proxyAddres = $proxy['ADDRESS'];
            $proxyAuth = $proxy['AUTH'];
            if (!is_null($proxyAddres)) {
                curl_setopt($httph, CURLOPT_PROXY, $proxyAddres);
                if (!is_null($proxyAuth)) {
                    curl_setopt($httph, CURLOPT_PROXYUSERPWD, $proxyAuth);
                }
            }
        }
        curl_setopt($httph, CURLOPT_SSL_VERIFYPEER, 0);
        curl_setopt($httph, CURLOPT_SSL_VERIFYHOST, 2);
        curl_setopt($httph, CURLOPT_USERAGENT, "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
        curl_setopt($httph, CURLOPT_POST, 1); // 设置为POST方式
        curl_setopt($httph, CURLOPT_POSTFIELDS, http_build_query($params));
        curl_setopt($httph, CURLOPT_RETURNTRANSFER, 1);
        curl_setopt($httph, CURLOPT_HEADER, false);
        curl_setopt($httph, CURLOPT_HTTPHEADER, $processedHeaders);
        $rst = curl_exec($httph);
        curl_close($httph);
        return $rst;
    }
}
