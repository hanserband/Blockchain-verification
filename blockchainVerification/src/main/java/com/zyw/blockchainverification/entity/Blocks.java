package com.zyw.blockchainverification.entity;

import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

@Setter
@Getter
public class Blocks {
    @JsonProperty("chain_info")
    private ChainInfo chainInfo;
    @JsonProperty("blocks")
    private List<Block> blocks;
}
