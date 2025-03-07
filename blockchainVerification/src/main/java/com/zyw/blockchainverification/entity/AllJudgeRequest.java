package com.zyw.blockchainverification.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllJudgeRequest {
    private String sourceApiUrl;
    private String targetApiUrl;
    private Integer startBlock;
    private Integer endBlock;


}
