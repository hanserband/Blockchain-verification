package com.zyw.blockchainverification.entity;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonProperty;

@Setter
@Getter
public class Transaction {
    @JsonProperty("status")
    private String Status;
    @JsonProperty("trx")
    private Trx trx;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        if (Status != null ? !Status.equals(that.Status) : that.Status != null) return false;
        if (trx != null ? !trx.equals(that.trx) : that.trx != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = Status != null ? Status.hashCode() : 0;
        result = 31 * result + (trx != null ? trx.hashCode() : 0);
        return result;
    }
}
