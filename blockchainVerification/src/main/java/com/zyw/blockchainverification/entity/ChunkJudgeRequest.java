package com.zyw.blockchainverification.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChunkJudgeRequest {
    private String sourceApiUrl;
    private String targetApiUrl;
    private long targetBlock;
    private Integer chunkSize;

}
