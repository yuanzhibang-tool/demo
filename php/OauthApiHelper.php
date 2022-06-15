<?php

class ApiRequestHelper
{

    public function apiRequest($url, $data)
    {
        $postData = array(
            'app_id' => $this->appId,
            'access_token' => $this->serverAccessToken
        );

        $postData = array_merge($postData, $data);
        $response = ApiRequestHelper::post($url, $postData, []);
        $responseInfo = json_decode($response, true);
        if ($responseInfo == null) {
            throw new DHOpenAuthErrorException('网络错误', 0000, $responseInfo);
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
