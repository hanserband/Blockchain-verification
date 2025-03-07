package com.zyw.blockchainverification.entity;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonProperty;

@Getter
@Setter
public class TrxData {
    @JsonProperty("payer")
    private String payer;
    @JsonProperty("receiver")
    private String receiver;
    @JsonProperty("bytes")
    private int bytes;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrxData that = (TrxData) o;
        if (bytes != that.bytes) return false;
        if (payer != null ? !payer.equals(that.payer) : that.payer != null) return false;
        if (receiver != null ? !receiver.equals(that.receiver) : that.receiver != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = payer != null ? payer.hashCode() : 0;
        result = 31 * result + (receiver != null ? receiver.hashCode() : 0);
        result = 31 * result + bytes;
        return result;
    }
}
