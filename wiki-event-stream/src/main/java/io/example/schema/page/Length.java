
package io.example.schema.page;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Length of old and new change
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "old",
        "new"
})
public class Length {

    /**
     * (rc_old_len)
     */
    @JsonProperty("old")
    @JsonPropertyDescription("(rc_old_len)")
    private Integer old;
    /**
     * (rc_new_len)
     */
    @JsonProperty("new")
    @JsonPropertyDescription("(rc_new_len)")
    private Integer _new;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * (rc_old_len)
     */
    @JsonProperty("old")
    public Integer getOld() {
        return old;
    }

    /**
     * (rc_old_len)
     */
    @JsonProperty("old")
    public void setOld(Integer old) {
        this.old = old;
    }

    /**
     * (rc_new_len)
     */
    @JsonProperty("new")
    public Integer getNew() {
        return _new;
    }

    /**
     * (rc_new_len)
     */
    @JsonProperty("new")
    public void setNew(Integer _new) {
        this._new = _new;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "Length{" +
                "old=" + old +
                ", _new=" + _new +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
