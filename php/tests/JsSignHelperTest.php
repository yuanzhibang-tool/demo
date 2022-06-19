<?php

namespace tests;

use JsSignHelper;
use PHPUnit\Framework\TestCase;


final class JsSignHelperTest extends TestCase
{
    public function testGetPureUrl(): void
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

    public function testGetJsSignInfo(): void
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
}
