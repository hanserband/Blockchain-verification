package com.zyw.blockchainverification.entity;

import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

@Setter
@Getter
public class ActionDetails {
    @JsonProperty("expiration")
    private String expiration;
    @JsonProperty("ref_block_num")
    private long refBlockNum;
    @JsonProperty("ref_block_prefix")
    private long refBlockPrefix;
    @JsonProperty("actions")
    private List<Action> actions;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionDetails that = (ActionDetails) o;
        if (refBlockNum != that.refBlockNum) return false;
        if (refBlockPrefix != that.refBlockPrefix) return false;
        if (expiration != null ? !expiration.equals(that.expiration) : that.expiration != null) return false;
        return actions != null ? actions.equals(that.actions) : that.actions == null;
    }

    @Override
    public int hashCode() {
        int result = expiration != null ? expiration.hashCode() : 0;
        result = 31 * result + (int) (refBlockNum ^ (refBlockNum >>> 32));
        result = 31 * result + (int) (refBlockPrefix ^ (refBlockPrefix >>> 32));
        result = 31 * result + (actions != null ? actions.hashCode() : 0);
        return result;
    }
}
