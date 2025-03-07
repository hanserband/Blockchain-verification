package com.zyw.blockchainverification.utils;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

public class OkHttpPool {
    // 全局单例 OkHttpClient（复用连接池）
    private static final OkHttpClient client = new OkHttpClient.Builder()
            .connectionPool(new ConnectionPool(20, 5, TimeUnit.MINUTES)) // 最大空闲连接数20，存活时间5分钟
            .build();

    public static OkHttpClient getClient() {
        return client;
    }

}
