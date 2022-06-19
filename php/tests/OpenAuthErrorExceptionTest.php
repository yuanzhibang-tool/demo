<?php

namespace tests;

use PHPUnit\Framework\TestCase;
use tests\DotEnv;
use OpenAuthErrorException;

(new DotEnv(__DIR__ . '/.env'))->load();

final class OpenAuthErrorExceptionTest extends TestCase
{
    public function test_construct(): void
    {
        $instance = new OpenAuthErrorException("error message", "122345", "123421");
        $this->assertSame($instance->getCode(), 122345);
        $this->assertSame($instance->getMessage(), "error message");
        $this->assertSame($instance->extra, "123421");
    }
}
