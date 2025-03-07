package com.zyw.blockchainverification.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RandomJudgeRequest {
    private String sourceApiUrl;
    private String targetApiUrl;
    private long targetBlock;
    private Integer number;//抽样校验中为抽样检验数量，分块校验为分块大小
}
