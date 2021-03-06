<?php
// !用来根据url生成js签名信息的类
class JsSignHelper
{
    public static function getGuid()
    {
        if (function_exists('com_create_guid') === true) {
            return trim(com_create_guid(), '{}');
        }
        return sprintf('%04X%04X-%04X-%04X-%04X-%04X%04X%04X', mt_rand(0, 65535), mt_rand(0, 65535), mt_rand(0, 65535), mt_rand(16384, 20479), mt_rand(32768, 49151), mt_rand(0, 65535), mt_rand(0, 65535), mt_rand(0, 65535));
    }
    public static function getPureUrl($url)
    {
        $urlInfo = parse_url($url);
        $scheme = $urlInfo['scheme'];
        $host = $urlInfo['host'];
        $path = $urlInfo['path'];
        if (isset($urlInfo['port'])) {
            $port = $urlInfo['port'];
            $url = "$scheme://$host:$port$path";
        } else {
            $url = "$scheme://$host$path";
        }
        if (substr($url, -1) === "/") {
            $url = substr($url, 0, strlen($url) - 1);
        }
        return $url;
    }

    public static function getJsSignInfo($appId, $url, $jsTicket)
    {
        // 获取当前时间戳
        $timestamp = time();
        // 生成nonce
        $noncestr = JsSignHelper::getGuid();
        // 组合签名字符串
        $sign = JsSignHelper::getSign($jsTicket, $noncestr, $timestamp, $url);
        $returnData = array();
        $returnData['app_id'] = $appId;
        $returnData['timestamp'] = "$timestamp";
        $returnData['nonce_str'] = $noncestr;
        $returnData['signature'] = $sign;
        $returnData['url'] = $url;
        return $returnData;
    }

    public static function getSign($jsTicket, $noncestr, $timestamp, $url)
    {
        // 取出url中的path之前的部分,参数和锚点不参与计算
        $url = JsSignHelper::getPureUrl($url);
        $string = "js_ticket=" . $jsTicket . "&nonce_str=" . $noncestr . "&timestamp=" . $timestamp . "&url=" . $url;
        $sign = sha1($string);
        return $sign;
    }
}
