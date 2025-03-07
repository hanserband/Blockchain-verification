package com.zyw.blockchainverification.controller;

import com.zyw.blockchainverification.entity.DownloadResult;
import com.zyw.blockchainverification.entity.FetchDataRequest;
import com.zyw.blockchainverification.utils.GetEosBlock;
import com.zyw.blockchainverification.utils.GetEosBlocks;
import kotlin.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blockchain")
@Slf4j
public class getEosBlockController {
    @PostMapping("/fetch")
    public DownloadResult<String> getEosBlock(@RequestBody FetchDataRequest request) {
        try {
            String sourceApiUrl = request.getSourceApiUrl();
            String targetApiUrl = request.getTargetApiUrl();
            int startBlock = request.getStartBlock();
            int endBlock = request.getEndBlock();

            GetEosBlocks.fetchAndSaveBlockchainData(sourceApiUrl, "source_chain", startBlock, endBlock);
            GetEosBlocks.fetchAndSaveBlockchainData(targetApiUrl, "target_chain", startBlock, endBlock);

            return DownloadResult.success("下载成功");
        } catch (Exception e) {
//            return DownloadResult.error(500, "操作失败: " + e.getMessage());
            return (DownloadResult<String>) DownloadResult.error(500, "下载失败:"+ e.getMessage());
        }
    }
}
