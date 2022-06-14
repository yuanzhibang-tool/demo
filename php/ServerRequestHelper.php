<?php

class ServerRequestHelper
{
    public function post($url, $params = array(), $headers = array(), $proxy = null)
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
        curl_setopt($httph, CURLOPT_SSL_VERIFYHOST, 1);
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
