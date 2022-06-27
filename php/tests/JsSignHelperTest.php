<?php

namespace tests;

use JsSignHelper;
use PHPUnit\Framework\TestCase;
use tests\DotEnv;

(new DotEnv(__DIR__ . '/.env'))->load();

final class JsSignHelperTest extends TestCase
{
    public function test_GetPureUrl(): void
    {
        $url = "https://yuanzhibang.com/a/b/?x=1&v=x#12";
        $pureUrl = JsSignHelper::getPureUrl($url);
        $expectUrl = "https://yuanzhibang.com/a/b";
        $this->assertSame($pureUrl, $expectUrl);
        $url = "https://yuanzhibang.com:80/a/b/?x=1&v=x#12";
        $pureUrl = JsSignHelper::getPureUrl($url);
        $expectUrl = "https://yuanzhibang.com:80/a/b";
        $this->assertSame($pureUrl, $expectUrl);
        $url = "https://yuanzhibang.com:80/a/b/";
        $pureUrl = JsSignHelper::getPureUrl($url);
        $expectUrl = "https://yuanzhibang.com:80/a/b";
        $this->assertSame($pureUrl, $expectUrl);
    }

    public function test_getSign(): void
    {
        $sign = JsSignHelper::getSign(
            "a4dcdk",
            "1234",
            1654850924,
            "https://yuanzhibang.com/a/b/?x=1&v=x#12"
        );
        echo $sign;
        $this->assertSame($sign, "a8cb02e00c2759372954bf5516d110066b911aa4");
    }

    public function test_getJsSignInfo(): void
    {
        $appId = getenv("TEST_APP_ID");
        $url = "https://yuanzhibang.com/a/b/?x=1&v=x#12";
        $testJsTickert = "123";
        $signInfo = JsSignHelper::getJsSignInfo(
            $appId,
            $url,
            $testJsTickert
        );
        $this->assertSame($signInfo["app_id"], $appId);
        $this->assertSame($signInfo["url"], $url);
        $timeDiff = time() - $signInfo['timestamp'];
        $this->assertSame($timeDiff < 10, true);
        $expectSign = JsSignHelper::getSign(
            $testJsTickert,
            $signInfo["nonce_str"],
            $signInfo['timestamp'],
            $url
        );
        $this->assertSame($signInfo['signature'], $expectSign);
    }
}
