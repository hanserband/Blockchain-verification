package com.zyw.blockchainverification.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import okhttp3.*;

import java.io.IOException;

public class HttpUtils {
    private static final Gson gson = new Gson();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    // 发送 POST 请求（复用连接池）
    public static String sendPostRequest(String apiUrl, String jsonBody) throws IOException {
        OkHttpClient client = OkHttpPool.getClient();
        RequestBody body = RequestBody.create(jsonBody, JSON);

        Request request = new Request.Builder()
                .url(apiUrl)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("HTTP Error: " + response.code());
            }
            return response.body().string();
        }
    }

    public static String toPrettyJson(String json) {
        JsonElement jsonElement = JsonParser.parseString(json);
        return gson.toJson(jsonElement);
    }
}
