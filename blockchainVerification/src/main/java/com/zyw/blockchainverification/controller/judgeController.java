package com.zyw.blockchainverification.controller;

import com.zyw.blockchainverification.entity.*;
import com.zyw.blockchainverification.utils.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.IOException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.*;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/judge")
@Slf4j
public class judgeController {

    private static final String LOGS_DIR = "src/main/logs/"; // 确保路径正确

    @GetMapping("/downloadReport")
    public ResponseEntity<FileSystemResource> downloadReport() throws IOException {
        // 创建临时ZIP文件
        File zipFile = createZipFile();

        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "report.zip");

        // 发送ZIP文件并删除临时文件及原日志文件
        FileSystemResource resource = new FileSystemResource(zipFile);
        log.info("报告已发送至前端！");
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(zipFile.length())
                .body(resource);
    }

    private File createZipFile() throws IOException {
        File logsDir = new File(LOGS_DIR);
        File[] files = logsDir.listFiles();
        if (files == null || files.length == 0) {
            throw new FileNotFoundException("No files to download");
        }

        File zipFile = File.createTempFile("report", ".zip");
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile))) {
            for (File file : files) {
                ZipEntry entry = new ZipEntry(file.getName());
                zos.putNextEntry(entry);
                Files.copy(file.toPath(), zos);
                zos.closeEntry();
            }
        }

        // 删除原日志文件
        cleanupLogsDirectory(logsDir);
        log.info("原报告下载完毕已自动删除，如有需要请重新校验");
        return zipFile;
    }

    private void cleanupLogsDirectory(File logsDir) throws IOException {
        for (File file : logsDir.listFiles()) {
            if (!file.isDirectory()) {
                Files.delete(file.toPath());
            }
        }
    }

    // 全量校验接口
    @PostMapping("/all")
    public Result getAllJudge(){
        String FileName1 = "source_chain.json";
        String FileName2 = "target_chain.json";
        String JsonStr1 = Utils.fileToString(FileName1);
        String JsonStr2 = Utils.fileToString(FileName2);
        Blocks BlocksChain1 = JsonUtil.jsonToObject(JsonStr1, Blocks.class);
        Blocks BlocksChain2 = JsonUtil.jsonToObject(JsonStr2, Blocks.class);
        Result result = Utils.judgeAllBlocks(BlocksChain1.getBlocks(), BlocksChain2.getBlocks());
        CsvReportGenerator.generateCsvReport(result);
        MarkdownReportGenerator.generateMarkdownReport(result);
        System.out.println(result.toString());
        return result;


    }


    // 抽样校验接口
    @PostMapping("/random")
    public Result getRandomJudge(@RequestBody RandomJudgeRequest request){
        Integer number = request.getNumber();
        String FileName1 = "source_chain.json";
        String FileName2 = "target_chain.json";
        String JsonStr1 = Utils.fileToString(FileName1);
        String JsonStr2 = Utils.fileToString(FileName2);
        Blocks BlocksChain1 = JsonUtil.jsonToObject(JsonStr1, Blocks.class);
        Blocks BlocksChain2 = JsonUtil.jsonToObject(JsonStr2, Blocks.class);
        Result result = Utils.judgeRandomBlocks(BlocksChain1.getBlocks(), BlocksChain2.getBlocks(), number);
        CsvReportGenerator.generateCsvReport(result);
        MarkdownReportGenerator.generateMarkdownReport(result);
        System.out.println(result.toString());
        return result;

    }
    // 分块校验接口
    @PostMapping("/chunk")
    public Result getChunkJudge(@RequestBody ChunkJudgeRequest request){
        Integer chunkSize = request.getChunkSize();
        String FileName1 = "source_chain.json";
        String FileName2 = "target_chain.json";
        String JsonStr1 = Utils.fileToString(FileName1);
        String JsonStr2 = Utils.fileToString(FileName2);
        Blocks BlocksChain1 = JsonUtil.jsonToObject(JsonStr1, Blocks.class);
        Blocks BlocksChain2 = JsonUtil.jsonToObject(JsonStr2, Blocks.class);
        Result result = Utils.judgeChunks(BlocksChain1.getBlocks(),BlocksChain2.getBlocks(),chunkSize);
        CsvReportGenerator.generateCsvReport(result);
        MarkdownReportGenerator.generateMarkdownReport(result);
        return result;
    }



}
