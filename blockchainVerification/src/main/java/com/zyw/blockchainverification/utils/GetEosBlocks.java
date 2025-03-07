package com.zyw.blockchainverification.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GetEosBlocks {
    // 资源目录路径
//    private static final String RESOURCE_PATH = "src/main/resources/blockchain_data/";
    //直接下载文件，不创建目录
    private static final String RESOURCE_PATH = "src/main/resources/";

    public static void main(String[] args) {
        String sourceApiUrl = "https://eos.greymass.com";
        String targetApiUrl = "https://eos.greymass.com";
        int startBlock = 419197000;
        int endBlock = 419197005;

        fetchAndSaveBlockchainData(sourceApiUrl, "source", startBlock, endBlock);
        fetchAndSaveBlockchainData(targetApiUrl, "target", startBlock, endBlock);
    }

    /**
     * 获取区块链数据并保存到单个文件
     */
    public static void fetchAndSaveBlockchainData(String apiUrl, String chainLabel, int startBlock, int endBlock) {
        System.out.println("Fetching data from: " + apiUrl);

        // 1. 获取链信息
        String chainInfo = getChainInfo(apiUrl);
        if (chainInfo.startsWith("Error")) {
            System.out.println("Failed to fetch chain info: " + chainInfo);
            return;
        }

        // 2. 获取区块数据
        JsonArray blocks = new JsonArray();
        Gson gson = new Gson();
        for (int blockNum = startBlock; blockNum <= endBlock; blockNum++) {
            String blockData = getBlockInfo(apiUrl, String.valueOf(blockNum));
            if (!blockData.startsWith("Error")) {
                blocks.add(gson.fromJson(blockData, JsonObject.class));
                System.out.println("Block " + blockNum + " fetched successfully");
            }
        }

        // 3. 构建完整JSON结构
        JsonObject output = new JsonObject();
        output.add("chain_info", gson.fromJson(chainInfo, JsonObject.class));
        output.add("blocks", blocks);

        // 4. 保存到文件
//        String fileName = chainLabel + "_chain.json";
        String fileName = chainLabel + ".json";
        saveToFile(RESOURCE_PATH + fileName, gson.toJson(output));
    }

    /**
     * 保存文件到指定路径（自动创建目录）
     */
    private static void saveToFile(String filePath, String content) {
        try {
            File file = new File(filePath);
            if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
                throw new IOException("Failed to create directories");
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(content);
                System.out.println("Data saved to: " + file.getAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println("Error saving file: " + e.getMessage());
        }
    }

    // 以下方法保持原有实现不变
    public static String getChainInfo(String apiUrl) {
        try {
            String response = HttpUtils.sendPostRequest(apiUrl + "/v1/chain/get_info", "");
            return HttpUtils.toPrettyJson(response);
        } catch (IOException e) {
            return "Error: " + e.getMessage();
        }
    }

    public static String getBlockInfo(String apiUrl, String blockNumOrId) {
        try {
            String jsonInput = "{ \"block_num_or_id\": \"" + blockNumOrId + "\" }";
            String response = HttpUtils.sendPostRequest(apiUrl + "/v1/chain/get_block", jsonInput);
            return HttpUtils.toPrettyJson(response);
        } catch (IOException e) {
            return "Error: " + e.getMessage();
        }
    }

}
