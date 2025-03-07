package com.zyw.blockchainverification.entity;

import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

@Getter
@Setter
public class Trx {
    @JsonProperty("id")
    private String trxId;
    @JsonProperty("signatures")
    private List<String> signatures;
    @JsonProperty("packed_trx")
    private String packed_trx;
    @JsonProperty("transaction")
    private ActionDetails actionDetails;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trx trx = (Trx) o;
        if (signatures != null ? !signatures.equals(trx.signatures) : trx.signatures != null) return false;
        if (trxId != null ? !trxId.equals(trx.trxId) : trx.trxId != null) return false;
        if (packed_trx != null ? !packed_trx.equals(trx.packed_trx) : trx.packed_trx != null) return false;
        return actionDetails != null ? actionDetails.equals(trx.actionDetails) : trx.actionDetails == null;
    }

    @Override
    public int hashCode() {
        int result = signatures != null ? signatures.hashCode() : 0;
        result = 31 * result + (trxId != null ? trxId.hashCode() : 0);
        result = 31 * result + (packed_trx != null ? packed_trx.hashCode() : 0);
        result = 31 * result + (actionDetails != null ? actionDetails.hashCode() : 0);
        return result;
    }
}
