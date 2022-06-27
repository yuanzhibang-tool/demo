<?php

namespace tests;

use PHPUnit\Framework\TestCase;
use tests\DotEnv;
use OauthApiHelper;

(new DotEnv(__DIR__ . '/.env'))->load();

final class OauthApiHelperTest extends TestCase
{
    public function test_post_proxy(): void
    {
        $getIpApi = "https://api-service.yuanzhibang.com/api/v1/Ip/getClientIp";
        $proxyAddress = getenv('TEST_PROXY_ADDRESS');
        $proxyAuth = getenv('TEST_PROXY_AUTH');
        $proxyIp = getenv('TEST_PROXY_IP');
        $proxy = ['ADDRESS' => $proxyAddress, 'AUTH' => $proxyAuth];
        $response = OauthApiHelper::post($getIpApi, [], [], $proxy);
        $this->assertSame($response, $proxyIp);
    }

    public function test_getApiByPath()
    {
        $api = OauthApiHelper::getApiByPath("/xxx");
        $expectApi = "https://oauth.yuanzhibang.com/xxx";
        $this->assertSame($api, $expectApi);
    }

    public function test_checkCode_valid()
    {
        $appId = getenv("TEST_APP_ID");
        $code = getenv("TEST_CODE");
        $response = OauthApiHelper::checkCode($appId, $code, "access_token");
        $this->assertArrayHasKey('expires_in', $response);
    }

    public function test_checkCode_invalid()
    {
        $appId = getenv("TEST_APP_ID");
        $code = "wrong code";
        $response = OauthApiHelper::checkCode($appId, $code, "access_token");
        $this->assertFalse($response);
    }

    public function test_getAppUserCount()
    {
        $appId = getenv("TEST_APP_ID");
        $code = getenv("TEST_CODE");
        $response = OauthApiHelper::getAppUserCount($appId, $code);
        $this->assertIsInt($response);
    }

    public function test_getAppUserList()
    {
        $appId = getenv("TEST_APP_ID");
        $code = getenv("TEST_CODE");
        $response = OauthApiHelper::getAppUserList($appId, $code);
        $this->assertIsArray($response);
        $this->assertArrayHasKey("id", $response[0]);
        $this->assertArrayHasKey("open_id", $response[0]);
    }

    public function test_getUserAppAccess()
    {
        $appId = getenv("TEST_APP_ID");
        $code = getenv("TEST_CODE");
        $openId = getenv("TEST_OPEN_ID");
        $response = OauthApiHelper::getUserAppAccess($appId, $openId, $code);
        $this->assertIsArray($response);
        $this->assertIsString($response[0]);
    }

    public function test_getUserIsAppAdded()
    {
        $appId = getenv("TEST_APP_ID");
        $code = getenv("TEST_CODE");
        $openId = getenv("TEST_OPEN_ID");
        $response = OauthApiHelper::getUserIsAppAdded($appId, $openId, $code);
        $this->assertIsBool($response);
    }

    public function test_getUserBaseInfo()
    {
        $appId = getenv("TEST_APP_ID");
        $code = getenv("TEST_CODE");
        $openId = getenv("TEST_OPEN_ID");
        $response = OauthApiHelper::getUserBaseInfo($appId, $openId, $code);
        $this->assertIsString($response['sex']);
        $this->assertIsString($response['avatar']);
        $this->assertIsString($response['nick']);
    }
}
