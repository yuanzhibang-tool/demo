package com.yuanzhibang.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisConnection;
import com.lambdaworks.redis.RedisURI;

/**
 * Unit test for simple App.
 */
public class RedisHelperTest {
    @Before
    public void setUp() {
        String appId = EnvHelper.getEnvValue("TEST_APP_ID");
        RedisClient redisClient = new RedisClient(
                RedisURI.create("redis://p8WOmXgzZg@demo-dev-cache-redis:6379"));
        RedisConnection<String, String> connection = redisClient.connect();
        String key = String.format("type/server_access_token_info/app_id/%s", appId);
        String value = "{\"server_access_token\": \"server_access_token:082675bb1bcbdc3b824fb040abdfd4e4b5e36e422af60365949e17e372cbcd4c\", \"expires_in\": 1654575742}";
        connection.set(key, value);
        key = String.format("type/js_ticket_info/app_id/%s", appId);
        value = "{\"js_ticket\":\"js_ticket:082675bb1bcbdc3b824fb040abdfd4e4b5e36e422af60365949e17e372cbcd4c\", \"expires_in\": 1654575742}";
        connection.set(key, value);
    }

    @Test
    public void test_getServerAccessTokenCacheKey() {
        String appId = EnvHelper.getEnvValue("TEST_APP_ID");
        String key = RedisHelper.getServerAccessTokenCacheKey(appId);
        String expectKey = String.format("type/server_access_token_info/app_id/%s", appId);
        assertEquals(key, expectKey);
    }

    @Test
    public void test_getJsTicketCacheKey() {
        String appId = EnvHelper.getEnvValue("TEST_APP_ID");
        String key = RedisHelper.getJsTicketCacheKey(appId);
        String expectKey = String.format("type/js_ticket_info/app_id/%s", appId);
        assertEquals(key, expectKey);
    }

    @Test
    public void test_getJsTicket() {
        String appId = EnvHelper.getEnvValue("TEST_APP_ID");
        RedisHelper helper = new RedisHelper("redis://p8WOmXgzZg@demo-dev-cache-redis:6379");
        String code = helper.getJsTicket(appId);
        assertEquals(code, "js_ticket:082675bb1bcbdc3b824fb040abdfd4e4b5e36e422af60365949e17e372cbcd4c");
    }

    @Test
    public void test_getServerAccessToken() {
        String appId = EnvHelper.getEnvValue("TEST_APP_ID");
        RedisHelper helper = new RedisHelper("redis://p8WOmXgzZg@demo-dev-cache-redis:6379");
        String code = helper.getServerAccessToken(appId);
        assertEquals(code, "server_access_token:082675bb1bcbdc3b824fb040abdfd4e4b5e36e422af60365949e17e372cbcd4c");
    }
}
