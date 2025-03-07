package com.zyw.blockchainverification.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FetchDataRequest {
    private String sourceApiUrl;
    private String targetApiUrl;
    private int startBlock;
    private int endBlock;
}
