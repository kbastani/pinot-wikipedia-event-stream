
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
 * Old and new revision IDs
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "new",
        "old"
})
public class Revision {

    /**
     * (rc_last_oldid)
     */
    @JsonProperty("new")
    @JsonPropertyDescription("(rc_last_oldid)")
    private Integer _new;
    /**
     * (rc_this_oldid)
     */
    @JsonProperty("old")
    @JsonPropertyDescription("(rc_this_oldid)")
    private Integer old;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * (rc_last_oldid)
     */
    @JsonProperty("new")
    public Integer getNew() {
        return _new;
    }

    /**
     * (rc_last_oldid)
     */
    @JsonProperty("new")
    public void setNew(Integer _new) {
        this._new = _new;
    }

    /**
     * (rc_this_oldid)
     */
    @JsonProperty("old")
    public Integer getOld() {
        return old;
    }

    /**
     * (rc_this_oldid)
     */
    @JsonProperty("old")
    public void setOld(Integer old) {
        this.old = old;
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
        return "Revision{" +
                "_new=" + _new +
                ", old=" + old +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
