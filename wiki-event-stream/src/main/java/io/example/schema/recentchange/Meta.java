
package io.example.schema.recentchange;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "uri",
        "request_id",
        "id",
        "dt",
        "domain",
        "stream"
})
public class Meta {

    /**
     * Unique URI identifying the event or entity
     */
    @JsonProperty("uri")
    @JsonPropertyDescription("Unique URI identifying the event or entity")
    private String uri;
    /**
     * Unique ID of the request that caused the event
     */
    @JsonProperty("request_id")
    @JsonPropertyDescription("Unique ID of the request that caused the event")
    private String requestId;
    /**
     * Unique ID of this event
     * (Required)
     */
    @JsonProperty("id")
    @JsonPropertyDescription("Unique ID of this event")
    private String id;
    /**
     * Event datetime, in ISO-8601 format
     * (Required)
     */
    @JsonProperty("dt")
    @JsonPropertyDescription("Event datetime, in ISO-8601 format")
    private Date dt;
    /**
     * Domain the event or entity pertains to
     */
    @JsonProperty("domain")
    @JsonPropertyDescription("Domain the event or entity pertains to")
    private String domain;
    /**
     * Name of the stream/queue/dataset that this event belongs in
     * (Required)
     */
    @JsonProperty("stream")
    @JsonPropertyDescription("Name of the stream/queue/dataset that this event belongs in")
    private String stream;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * Unique URI identifying the event or entity
     */
    @JsonProperty("uri")
    public String getUri() {
        return uri;
    }

    /**
     * Unique URI identifying the event or entity
     */
    @JsonProperty("uri")
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * Unique ID of the request that caused the event
     */
    @JsonProperty("request_id")
    public String getRequestId() {
        return requestId;
    }

    /**
     * Unique ID of the request that caused the event
     */
    @JsonProperty("request_id")
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    /**
     * Unique ID of this event
     * (Required)
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * Unique ID of this event
     * (Required)
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Event datetime, in ISO-8601 format
     * (Required)
     */
    @JsonProperty("dt")
    public Date getDt() {
        return dt;
    }

    /**
     * Event datetime, in ISO-8601 format
     * (Required)
     */
    @JsonProperty("dt")
    public void setDt(Date dt) {
        this.dt = dt;
    }

    /**
     * Domain the event or entity pertains to
     */
    @JsonProperty("domain")
    public String getDomain() {
        return domain;
    }

    /**
     * Domain the event or entity pertains to
     */
    @JsonProperty("domain")
    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     * Name of the stream/queue/dataset that this event belongs in
     * (Required)
     */
    @JsonProperty("stream")
    public String getStream() {
        return stream;
    }

    /**
     * Name of the stream/queue/dataset that this event belongs in
     * (Required)
     */
    @JsonProperty("stream")
    public void setStream(String stream) {
        this.stream = stream;
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
        return "Meta{" +
                "uri='" + uri + '\'' +
                ", requestId='" + requestId + '\'' +
                ", id='" + id + '\'' +
                ", dt=" + dt +
                ", domain='" + domain + '\'' +
                ", stream='" + stream + '\'' +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
