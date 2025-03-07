package com.zyw.blockchainverification.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GetEosBlock {
    // 添加资源目录常量
//    private static final String RESOURCE_ROOT = "/src/main/resources/blockchain_data/";

    public static void main(String[] args) {
        // if (args.length < 4) {
        // System.out.println("Usage: java GetEosData <sourceApiUrl> <targetApiUrl>
        // <startBlock> <endBlock>");
        // return;
        // }

        // String sourceApiUrl = args[0];
        // String targetApiUrl = args[1];
        // int startBlock = Integer.parseInt(args[2]);
        // int endBlock = Integer.parseInt(args[3]);

        String sourceApiUrl = "https://eos.greymass.com";
        String targetApiUrl = "https://eos.greymass.com";
        int startBlock = 419197000;
        int endBlock = 419197000;

        // 拉取源链数据
        fetchAndSaveBlockchainData(sourceApiUrl, "source_chain", startBlock, endBlock);

        // 拉取目标链数据
        fetchAndSaveBlockchainData(targetApiUrl, "target_chain", startBlock, endBlock);
    }

    /**
     * 拉取链信息和区块数据并保存
     */
    public static void fetchAndSaveBlockchainData(String apiUrl, String chainLabel, int startBlock, int endBlock) {
        System.out.println("Fetching data from: " + apiUrl);
        // 获取链信息
        String chainInfo = getChainInfo(apiUrl);
        if (chainInfo.startsWith("Error")) {
            System.out.println("Failed to fetch chain info from: " + apiUrl);
            return;
        }
        // 修改保存路径
//        String chainInfoPath = RESOURCE_ROOT + "chain_info/" + chainLabel + "_info.json";
//        saveToFile(chainInfoPath, chainInfo);
        // 保存链信息
        saveToFile("blockchain_data/chain_info/" + chainLabel + "_info.json", chainInfo);

        // 拉取指定范围内的区块数据
        for (int blockNum = startBlock; blockNum <= endBlock; blockNum++) {
            String blockInfo = getBlockInfo(apiUrl, String.valueOf(blockNum));
            if (!blockInfo.startsWith("Error")) {
                String blockDir = "blockchain_data/block_" + blockNum;
                new File(blockDir).mkdirs(); // 创建目录
                saveToFile(blockDir + "/" + chainLabel + ".json", blockInfo);
//                String blockDirPath = RESOURCE_ROOT + "block_" + blockNum;
//                new File(blockDirPath).mkdirs();
//                String blockFilePath = blockDirPath + "/" + chainLabel + ".json";
//                saveToFile(blockFilePath, blockInfo);
                System.out.println("Block " + blockNum + " saved for " + chainLabel);
            } else {
                System.out.println("Failed to fetch block " + blockNum + " from " + apiUrl);
            }
        }
    }

    /**
     * 获取链的基本信息
     */
    public static String getChainInfo(String apiUrl) {
        try {
            String fullUrl = apiUrl + "/v1/chain/get_info";
            String response = HttpUtils.sendPostRequest(fullUrl, "");
            return HttpUtils.toPrettyJson(response);
        } catch (IOException e) {
            return "Error: " + e.getMessage();
        }
    }

    /**
     * 获取单个区块的数据
     */
    public static String getBlockInfo(String apiUrl, String blockNumOrId) {
        try {
            String fullUrl = apiUrl + "/v1/chain/get_block";
            String jsonInput = "{ \"block_num_or_id\": \"" + blockNumOrId + "\" }";
            String response = HttpUtils.sendPostRequest(fullUrl, jsonInput);
            return HttpUtils.toPrettyJson(response);
        } catch (IOException e) {
            return "Error: " + e.getMessage();
        }
    }

    /**
     * 保存数据到文件
     */
    public static void saveToFile(String filePath, String data) {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs(); // 确保父目录存在
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(data);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        try {
//            File file = new File(filePath);
//            File parentDir = file.getParentFile();
//            if (!parentDir.exists() && !parentDir.mkdirs()) {
//                System.err.println("Failed to create directory: " + parentDir.getAbsolutePath());
//                return;
//            }
//
//            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
//                writer.write(data);
//                System.out.println("Successfully saved to: " + file.getAbsolutePath()); // 添加路径输出
//            }
//        } catch (IOException e) {
//            System.err.println("Error saving file: " + e.getMessage());
//            e.printStackTrace();
//        }
    }
}