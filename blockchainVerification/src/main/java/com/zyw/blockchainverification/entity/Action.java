package com.zyw.blockchainverification.entity;

import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

@Getter
@Setter
public class Action {
    @JsonProperty("account")
    private String account;
    @JsonProperty("name")
    private String name;
    @JsonProperty("hex_data")
    private String hexData;
    @JsonProperty("authorization")
    private List<Authorization> authorizations;
    @JsonProperty("data")
    private Object data;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Action action = (Action) o;
        if (account != null ? !account.equals(action.account) : action.account != null) return false;
        if (name != null ? !name.equals(action.name) : action.name != null) return false;
        if (hexData != null ? !hexData.equals(action.hexData) : action.hexData != null) return false;
        if (data != null ? !data.equals(action.data) : action.data != null) return false;
        return authorizations != null ? authorizations.equals(action.authorizations) : action.authorizations == null;
    }

    @Override
    public int hashCode() {
        int result = account != null ? account.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (hexData != null ? hexData.hashCode() : 0);
        result = 31 * result + (authorizations != null ? authorizations.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }
}
