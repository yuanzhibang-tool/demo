package com.yuanzhibang.demo;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvHelper {
    public static String getEnvValue(String key) {
        Dotenv dotenv = Dotenv.load();
        return dotenv.get(key);
    }
}
