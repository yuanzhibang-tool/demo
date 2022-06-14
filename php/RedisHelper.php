<?php

class RedisHelper
{
    public $config;
    public function __construct($config)
    {
        $this->config = $config;
    }

    public function getJsTicket($appId)
    {
        $key = RedisHelper::getJsTicketCacheKey($appId);
        $value = $this->getRedisJsonValueByKey($key, 'js_ticket');
        return $value;
    }

    public function getServerAccessToken($appId)
    {
        // 请通过apt install php7.4-redis 类似的语句或者自己编译的方式来安装php的redis拓展
        $key = RedisHelper::getServerAccessTokenCacheKey($appId);
        $value = $this->getRedisJsonValueByKey($key, 'server_access_token');
        return $value;
    }

    static public function getServerAccessTokenCacheKey($appId)
    {
        return "type/server_access_token_info/app_id/$appId";
    }
    static public function getJsTicketCacheKey($appId)
    {
        return "type/js_ticket_info/app_id/$appId";
    }
    public function getRedisJsonValueByKey($key, $codeKey)
    {
        // 请通过apt install php7.4-redis 类似的语句或者自己编译的方式来安装php的redis拓展,具体的redis用法要参照不同的phpredis 版本
        $redis = new Redis();
        $redis->connect($this->config['host'], $this->config['port'], 10);
        $redis->auth($this->config['auth']);
        $redis->select(0);
        $infoString = $redis->get($key);
        $info = json_decode($infoString, true);
        $result = $info[$codeKey];
        $redis->close();
        return $result;
    }
}


$config = [
    "host" => "demo-dev-cache-redis",
    "port" => 6379,
    "auth" => 'p8WOmXgzZg',
];
$redisHelper = new RedisHelper($config);
$jsTicket = $redisHelper->getJsTicket("100027");
$serverAccessToken = $redisHelper->getServerAccessToken("100027");
var_dump($jsTicket);
var_dump($serverAccessToken);
