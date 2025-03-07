package com.zyw.blockchainverification.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.zyw.blockchainverification.entity.Block;
import com.zyw.blockchainverification.entity.Result;
import org.apache.commons.io.IOUtils;

public class Utils {
    public static String ReadJson(File file) throws IOException {
        String JsonStr = new String(Files.readAllBytes(Paths.get(file.getPath())));
        return JsonStr;
    }

    public static String fileToString(String fileName) {
        //使用任意路径
        String JsonStr = null;
//        try {
//            // 检查文件路径是否存在
//            Path filePath = Paths.get(fileName);
//            if (Files.exists(filePath)) {
//                // 使用 Files.newInputStream 代替 InputStream
//                try (InputStream is = Files.newInputStream(filePath)) {
//                    JsonStr = IOUtils.toString(is, "utf-8");
//                }
//            } else {
//                throw new RuntimeException("File not found: " + fileName);
//            }
//        } catch (Exception e) {
//            System.out.println("文件读取异常: " + e.getMessage());
//            e.printStackTrace();
//        }
        //使用resource下的路径
        try (
                InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        ) {
            JsonStr = IOUtils.toString(is, "utf-8");
        } catch (Exception e) {
            System.out.println(fileName + "文件读取异常" + e);
        }
        return JsonStr;
    }

    public static Result judgeAllBlocks(List<Block> blocks1, List<Block> blocks2) {
        Result result = new Result("全量检验");
        long stime = System.currentTimeMillis();
        if (blocks1.size() != blocks2.size()) {
            result.setEqual(false);
            result.refreshReports("两条链长度不一致\n");
            return result;
        }
        else {
            for (int i = 0; i < blocks1.size(); i++) {
                Block block1 = blocks1.get(i);
                Block block2 = blocks2.get(i);
                if (!block1.equals(block2)) {
                    result.setEqual(false);
                    result.addResult(block1.getBlockNumber());
                }
            }
        }
        long etime = System.currentTimeMillis();
        result.setRunningTime(etime - stime);
        return result;
    }

    public static Result judgeSingleAllBlocks(Block block1, Block block2) {
        Result result = new Result("全量检验");
        long stime = System.currentTimeMillis();
        if (!block1.equals(block2)) {
            result.setEqual(false);
            result.addResult(block1.getBlockNumber());
        }
        long etime = System.currentTimeMillis();
        result.setRunningTime(etime - stime);
        return result;
    }

    public static Result judgeRandomBlocks(List<Block> blocks1, List<Block> blocks2, int number) {
        Result result = new Result("抽样检验");
        long stime = System.currentTimeMillis();
        if (blocks1.size() != blocks2.size()) {
            result.setEqual(false);
            result.refreshReports("两条链长度不一致\n");
            return result;
        }
        else {
            int[] arr = RandomNumberGenerator.generateRandomNumbers(number, blocks1.size());
            for (int i : arr) {
                Block block1 = blocks1.get(i);
                Block block2 = blocks2.get(i);
                if (!block1.equals(block2)) {
                    result.setEqual(false);
                    result.addResult(block1.getBlockNumber());
                }
            }
        }
        long etime = System.currentTimeMillis();
        result.setRunningTime(etime - stime);
        return result;
    }

    // 将区块链数据按指定大小切分为多个分块
    public static List<List<Block>> splitIntoChunks(List<Block> blocks, int chunkSize) {
        List<List<Block>> chunks = new ArrayList<>();
        for (int i = 0; i < blocks.size(); i += chunkSize) {
            int end = Math.min(i + chunkSize, blocks.size());
            chunks.add(blocks.subList(i, end));
        }
        return chunks;
    }

    // 计算分块的哈希值
    public static String calculateChunkHash(List<Block> chunk) {
        StringBuilder sb = new StringBuilder();
        for (Block block : chunk) {
            sb.append(block.hashCode()); // 或者根据实际的字段计算哈希
        }
        return Integer.toHexString(sb.toString().hashCode());
    }


    public static Result judgeChunks(List<Block> blocks1, List<Block> blocks2, int chunkSize) {
        long stime = System.currentTimeMillis();
        List<List<Block>> chunks1 = Utils.splitIntoChunks(blocks1, chunkSize);
        List<List<Block>> chunks2 = Utils.splitIntoChunks(blocks2, chunkSize);
        Result result = new Result("分块检验");
        System.out.println("分块校验是否一致：");
        boolean hasMismatch = false;

        for (int i = 0; i < chunks1.size(); i++) {
            String hash1 = Utils.calculateChunkHash(chunks1.get(i));
            String hash2 = Utils.calculateChunkHash(chunks2.get(i));

            if (!hash1.equals(hash2)) {
                System.out.println("分块 " + i + " 不一致，开始进行块内校验");
                hasMismatch = true;
                result.setEqual(false);
                for (int j = 0; j < chunks1.get(i).size(); j++) {
                    Block block1 = chunks1.get(i).get(j);
                    Block block2 = chunks2.get(i).get(j);
                    if (!block1.equals(block2)) {
                        System.out.println("区块 " + block1.getBlockNumber() + " 不一致");
                        result.addResult(block1.getBlockNumber());
                    }
                }
            }
        }

        if (!hasMismatch) {
            System.out.println("所有分块哈希一致！");
        }
        long etime = System.currentTimeMillis();
        result.setRunningTime(etime - stime);
        return result;

    }
}
