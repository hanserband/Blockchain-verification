package com.zyw.blockchainverification.entity;

import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

@Setter
@Getter
public class Block {
    @JsonProperty("previous")
    private String previous;
    @JsonProperty("block_num")
    private int blockNumber;
    @JsonProperty("id")
    private String id;
    @JsonProperty("producer_signature")
    private String producerSignature;
    @JsonProperty("transactions")
    private List<Transaction> transaction;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Block block = (Block) o;
        if (blockNumber != block.blockNumber) return false;
        if (id != null ? !id.equals(block.id) : block.id != null) return false;
        if (previous != null ? !previous.equals(block.previous) : block.previous != null) return false;
        if (producerSignature != null ? !producerSignature.equals(block.producerSignature) : block.producerSignature != null) return false;
        return transaction != null ? transaction.equals(block.transaction) : block.transaction == null;
    }

    @Override
    public int hashCode() {
        int result = previous != null ? previous.hashCode() : 0;
        result = 31 * result + blockNumber;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (producerSignature != null ? producerSignature.hashCode() : 0);
        result = 31 * result + (transaction != null ? transaction.hashCode() : 0);
        return result;
    }
}
