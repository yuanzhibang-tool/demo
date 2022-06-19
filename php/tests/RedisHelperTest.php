<?php

namespace tests;

use RedisHelper;
use PHPUnit\Framework\TestCase;
use tests\DotEnv;

(new DotEnv(__DIR__ . '/.env'))->load();

final class RedisHelperTest extends TestCase
{
    public static  $redisConfig = [
        "host" => "demo-dev-cache-redis",
        "port" => 6379,
        "auth" => "p8WOmXgzZg",
    ];

    public static function setUpBeforeClass(): void
    {
        $appId = getenv("TEST_APP_ID");
        $redis = new \Redis();
        $redis->connect(self::$redisConfig['host'], self::$redisConfig['port'], 10);
        $redis->auth(self::$redisConfig['auth']);
        $redis->select(0);
        $key = "type/server_access_token_info/app_id/$appId";
        $value = '{"server_access_token": "server_access_token:082675bb1bcbdc3b824fb040abdfd4e4b5e36e422af60365949e17e372cbcd4c", "expires_in": 1654575742}';
        $redis->set($key, $value);
        $key = "type/js_ticket_info/app_id/$appId";
        $value = '{"js_ticket":"js_ticket:082675bb1bcbdc3b824fb040abdfd4e4b5e36e422af60365949e17e372cbcd4c", "expires_in": 1654575742}';
        $redis->set($key, $value);
    }

    public function test_getServerAccessTokenCacheKey(): void
    {
        $appId = getenv("TEST_APP_ID");
        $key = RedisHelper::getServerAccessTokenCacheKey($appId);
        $expectKey = "type/server_access_token_info/app_id/$appId";
        $this->assertSame($key, $expectKey);
    }

    public function test_getJsTicketCacheKey(): void
    {
        $appId = getenv("TEST_APP_ID");
        $key = RedisHelper::getJsTicketCacheKey($appId);
        $expectKey = "type/js_ticket_info/app_id/$appId";
        $this->assertSame($key, $expectKey);
    }
    public function test_getJsTicket(): void
    {
        $appId = getenv("TEST_APP_ID");
        $helper = new RedisHelper(self::$redisConfig);
        $code = $helper->getJsTicket($appId);
        $expectCode = "js_ticket:082675bb1bcbdc3b824fb040abdfd4e4b5e36e422af60365949e17e372cbcd4c";
        $this->assertSame($code, $expectCode);
    }
    public function test_getServerAccessToken(): void
    {
        $appId = getenv("TEST_APP_ID");
        $helper = new RedisHelper(self::$redisConfig);
        $code = $helper->getServerAccessToken($appId);
        $expectCode = "server_access_token:082675bb1bcbdc3b824fb040abdfd4e4b5e36e422af60365949e17e372cbcd4c";
        $this->assertSame($code, $expectCode);
    }
}
