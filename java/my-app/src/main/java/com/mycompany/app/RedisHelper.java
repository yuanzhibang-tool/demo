package com.mycompany.app;

import com.lambdaworks.redis.*;

public class RedisHelper {
    public static void test() {
        RedisClient redisClient = new RedisClient(
                RedisURI.create("redis://password@host:port"));
        RedisConnection<String, String> connection = redisClient.connect();

        System.out.println("Connected to Redis");

        connection.close();
        redisClient.shutdown();
    }
}
