package com.zyw.blockchainverification.entity;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonProperty;

@Getter
@Setter
public class Authorization {
    @JsonProperty("actor")
    private String actor;
    @JsonProperty("permission")
    private String permission;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Authorization that = (Authorization) o;
        return this.actor.equals(that.actor) && this.permission.equals(that.permission);
    }

    @Override
    public int hashCode() {
        int result = actor.hashCode();
        result = 31 * result + permission.hashCode();
        return result;
    }
}
