package com.zyw.blockchainverification.utils;

import com.google.gson.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class getInfo {

    public static String getBlockInfo(String apiUrl, String blockNumOrId) {
        try {
            String fullUrl = apiUrl + "/v1/chain/get_block";
            String jsonInput = "{ \"block_num_or_id\": \"" + blockNumOrId + "\" }";

            String response = HttpUtils.sendPostRequest(fullUrl, jsonInput);
            return HttpUtils.toPrettyJson(response);

        } catch (IOException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    public static String getChainInfo(String apiUrl) {
        try {
            // 构建完整 API 地址
            String fullUrl = apiUrl + "/v1/chain/get_info";

            // 使用 OkHttp 发送 POST 请求（无需请求体）
            String response = HttpUtils.sendPostRequest(fullUrl, ""); // 空请求体

            // 返回格式化后的 JSON
            return HttpUtils.toPrettyJson(response);

        } catch (IOException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    public static void writeBlockchainDataToFile(String fileName, String chainInfoJson, List<String> blocksJson) {
        try {
            // 构建最终的JSON对象
            JsonObject blockchainData = new JsonObject();

            // 添加链的信息
            JsonElement chainInfo = JsonParser.parseString(chainInfoJson);
            blockchainData.add("chain_info", chainInfo);

            // 添加所有区块的信息
            JsonArray blocksArray = new JsonArray();
            for (String blockJson : blocksJson) {
                JsonElement blockElement = JsonParser.parseString(blockJson);
                blocksArray.add(blockElement);
            }
            blockchainData.add("blocks", blocksArray);

            // 将JSON写入文件
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(blockchainData, writer); // 格式化并写入
            }

            System.out.println("Blockchain data written to: " + fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fetchBlockchainData(String apiUrl) {
        // 获取链的信息
        String chainInfo = getChainInfo(apiUrl);
        if (!chainInfo.startsWith("Error")) {
            // 获取多个区块的信息
            List<String> blocksInfo = new ArrayList<>();
            // 起始区块号          终止区块号
            for (int blockNumOrId = 1; blockNumOrId < 10; blockNumOrId++) {

                String blockInfo = getBlockInfo(apiUrl, String.valueOf(blockNumOrId));
                if (!blockInfo.startsWith("Error")) {
                    blocksInfo.add(blockInfo);
                }
            }
            // 将链信息和块信息写入文件
            writeBlockchainDataToFile("blockchain_data.json", chainInfo, blocksInfo);
        } else {
            System.out.println("Failed to fetch chain info: " + chainInfo);
        }
    }
}
