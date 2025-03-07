package com.zyw.blockchainverification.utils;

import com.zyw.blockchainverification.entity.Result;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MarkdownReportGenerator {
    // 📝 报告存储目录
    private static final String REPORT_DIRECTORY = "src/main/logs/";

    // 📌 生成带时间戳的文件名
    private static String generateTimestampedFileName() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        return REPORT_DIRECTORY + "blockchain_report_" + timestamp + ".md";
    }

    public static void generateMarkdownReport(Result result) {
        // 🏗 确保 logs 目录存在
        File directory = new File(REPORT_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs(); // 确保多级目录都创建
        }

        // 📌 生成带时间戳的 Markdown 文件名
        String reportFilePath = generateTimestampedFileName();

        try (FileWriter writer = new FileWriter(reportFilePath)) {
            // 1️⃣ 任务概览
            writer.append("# 区块链校验报告\n\n");
            writer.append("**任务类型:** 全量校验  \n");
            writer.append("**对比时间:** ")
                    .append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                    .append("  \n");
            writer.append("**校对结果:** ").append(result.getEqual() ? "✅ 一致" : "❌ 不一致").append("  \n");
            writer.append("**运行时间:** ").append(result.getRunningTime() + " ms  \n\n");

            // 2️⃣ 记录不一致的区块
            writer.append("## 🚨 不一致的区块\n\n");
            if (result.getBlockNumbers().isEmpty()) {
                writer.append("✅ **所有区块一致** 🎉\n\n");
            } else {
                writer.append("| 区块编号 |\n");
                writer.append("|---------|\n");
                for (Integer blockNumber : result.getBlockNumbers()) {
                    writer.append("| ").append(blockNumber.toString()).append(" |\n");
                }
                writer.append("\n");
            }

            // 3️⃣ 记录完整报告
            writer.append("## 📄 完整报告\n\n");
            writer.append("```\n");
            writer.append(result.toString().replace("\n", "\n")).append("\n");
            writer.append("```\n");

            System.out.println("Markdown 报告已生成: " + new File(reportFilePath).getAbsolutePath());
        } catch (IOException e) {
            System.out.println("生成 Markdown 失败: " + e.getMessage());
        }
    }
}
