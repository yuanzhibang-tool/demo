package com.mycompany.app;

import java.util.Map;

import com.google.gson.Gson;
import com.lambdaworks.redis.*;

public class RedisHelper {
    public String redisUrl;

    RedisHelper(String redisUrl) {
        this.redisUrl = redisUrl;
    }

    public String getJsTicket(String appId) {
        String key = RedisHelper.getJsTicketCacheKey(appId);
        return this.getRedisJsonValueByKey(key, "js_ticket");
    }

    public String getServerAccessToken(String appId) {
        String key = RedisHelper.getServerAccessTokenCacheKey(appId);
        return this.getRedisJsonValueByKey(key, "server_access_token");
    }

    public static String getServerAccessTokenCacheKey(String appId) {
        return String.format("type/server_access_token_info/app_id/%s", appId);
    }

    public static String getJsTicketCacheKey(String appId) {
        return String.format("type/js_ticket_info/app_id/%s", appId);
    }

    @SuppressWarnings("unchecked")
    public String getRedisJsonValueByKey(String key, String codeKey) {
        RedisClient redisClient = new RedisClient(
                RedisURI.create(this.redisUrl));
        RedisConnection<String, String> connection = redisClient.connect();
        String jsonInfo = connection.get(key);
        Gson gson = new Gson();
        Map<String, String> info = gson.fromJson(jsonInfo, Map.class);
        String value = info.get(codeKey);
        connection.close();
        redisClient.shutdown();
        return value;
    }

    public static void test() {
        RedisHelper helper = new RedisHelper("redis://p8WOmXgzZg@demo-dev-cache-redis:6379");
        String jsTicket = helper.getJsTicket("100027");
        String serverAccessToken = helper.getServerAccessToken("100027");
    }
}
