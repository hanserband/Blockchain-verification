package com.zyw.blockchainverification.entity;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonProperty;

@Setter
@Getter
public class ChainInfo {
    @JsonProperty("chain_id")
    private String chainId;
}
