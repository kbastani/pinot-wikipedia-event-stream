
package io.example.schema.recentchange;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * mediawiki/recentchange
 * <p>
 * Represents a MW RecentChange event. https://www.mediawiki.org/wiki/Manual:RCFeed
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "$schema",
        "meta",
        "id",
        "type",
        "title",
        "namespace",
        "comment",
        "parsedcomment",
        "timestamp",
        "user",
        "bot",
        "server_url",
        "server_name",
        "server_script_path",
        "wiki",
        "minor",
        "patrolled",
        "length",
        "revision",
        "log_id",
        "log_type",
        "log_action",
        "log_params",
        "log_action_comment"
})
public class RecentChange {

    /**
     * A URI identifying the JSONSchema for this event. This should match an schema's $id in a schema repository. E.g. /schema_name/1.0.0
     * <p>
     * (Required)
     */
    @JsonProperty("$schema")
    @JsonPropertyDescription("A URI identifying the JSONSchema for this event. This should match an schema's $id in a schema repository. E.g. /schema_name/1.0.0\n")
    private String $schema;
    /**
     * (Required)
     */
    @JsonProperty("meta")
    private Meta meta;
    /**
     * ID of the recentchange event (rcid).
     */
    @JsonProperty("id")
    @JsonPropertyDescription("ID of the recentchange event (rcid).")
    private Integer id;
    /**
     * Type of recentchange event (rc_type). One of "edit", "new", "log", "categorize", or "external". (See Manual:Recentchanges table#rc_type)
     */
    @JsonProperty("type")
    @JsonPropertyDescription("Type of recentchange event (rc_type). One of \"edit\", \"new\", \"log\", \"categorize\", or \"external\". (See Manual:Recentchanges table#rc_type)\n")
    private String type;
    /**
     * Full page name, from Title::getPrefixedText.
     */
    @JsonProperty("title")
    @JsonPropertyDescription("Full page name, from Title::getPrefixedText.")
    private String title;
    /**
     * ID of relevant namespace of affected page (rc_namespace, page_namespace). This is -1 ("Special") for log events.
     */
    @JsonProperty("namespace")
    @JsonPropertyDescription("ID of relevant namespace of affected page (rc_namespace, page_namespace). This is -1 (\"Special\") for log events.\n")
    private Integer namespace;
    /**
     * (rc_comment)
     */
    @JsonProperty("comment")
    @JsonPropertyDescription("(rc_comment)")
    private String comment;
    /**
     * The rc_comment parsed into simple HTML. Optional
     */
    @JsonProperty("parsedcomment")
    @JsonPropertyDescription("The rc_comment parsed into simple HTML. Optional")
    private String parsedcomment;
    /**
     * The categories parsed into a comma separated list of values
     */
    @JsonProperty("category")
    @JsonPropertyDescription("The rc_comment parsed into simple HTML. Optional")
    private String category;
    /**
     * Unix timestamp (derived from rc_timestamp).
     */
    @JsonProperty("timestamp")
    @JsonPropertyDescription("Unix timestamp (derived from rc_timestamp).")
    private Integer timestamp;
    /**
     * (rc_user_text)
     */
    @JsonProperty("user")
    @JsonPropertyDescription("(rc_user_text)")
    private String user;
    /**
     * (rc_bot)
     */
    @JsonProperty("bot")
    @JsonPropertyDescription("(rc_bot)")
    private Boolean bot;
    /**
     * $wgCanonicalServer
     */
    @JsonProperty("server_url")
    @JsonPropertyDescription("$wgCanonicalServer")
    private String serverUrl;
    /**
     * $wgServerName
     */
    @JsonProperty("server_name")
    @JsonPropertyDescription("$wgServerName")
    private String serverName;
    /**
     * $wgScriptPath
     */
    @JsonProperty("server_script_path")
    @JsonPropertyDescription("$wgScriptPath")
    private String serverScriptPath;
    /**
     * wfWikiID ($wgDBprefix, $wgDBname)
     */
    @JsonProperty("wiki")
    @JsonPropertyDescription("wfWikiID ($wgDBprefix, $wgDBname)")
    private String wiki;
    /**
     * (rc_minor).
     */
    @JsonProperty("minor")
    @JsonPropertyDescription("(rc_minor).")
    private Boolean minor;
    /**
     * (rc_patrolled). This property only exists if patrolling is supported for this event (based on $wgUseRCPatrol, $wgUseNPPatrol).
     */
    @JsonProperty("patrolled")
    @JsonPropertyDescription("(rc_patrolled). This property only exists if patrolling is supported for this event (based on $wgUseRCPatrol, $wgUseNPPatrol).\n")
    private Boolean patrolled;
    /**
     * Length of old and new change
     */
    @JsonProperty("length")
    @JsonPropertyDescription("Length of old and new change")
    private Length length;
    /**
     * Old and new revision IDs
     */
    @JsonProperty("revision")
    @JsonPropertyDescription("Old and new revision IDs")
    private Revision revision;
    /**
     * (rc_log_id)
     */
    @JsonProperty("log_id")
    @JsonPropertyDescription("(rc_log_id)")
    private Integer logId;
    /**
     * (rc_log_type)
     */
    @JsonProperty("log_type")
    @JsonPropertyDescription("(rc_log_type)")
    private String logType;
    /**
     * (rc_log_action)
     */
    @JsonProperty("log_action")
    @JsonPropertyDescription("(rc_log_action)")
    private String logAction;
    /**
     * Property only exists if event has rc_params.
     */
    @JsonProperty("log_params")
    @JsonPropertyDescription("Property only exists if event has rc_params.")
    private List<Object> logParams = null;
    @JsonProperty("log_action_comment")
    private String logActionComment;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * A URI identifying the JSONSchema for this event. This should match an schema's $id in a schema repository. E.g. /schema_name/1.0.0
     * <p>
     * (Required)
     */
    @JsonProperty("$schema")
    public String get$schema() {
        return $schema;
    }

    /**
     * A URI identifying the JSONSchema for this event. This should match an schema's $id in a schema repository. E.g. /schema_name/1.0.0
     * <p>
     * (Required)
     */
    @JsonProperty("$schema")
    public void set$schema(String $schema) {
        this.$schema = $schema;
    }

    /**
     * (Required)
     */
    @JsonProperty("meta")
    public Meta getMeta() {
        return meta;
    }

    /**
     * (Required)
     */
    @JsonProperty("meta")
    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    /**
     * ID of the recentchange event (rcid).
     */
    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    /**
     * ID of the recentchange event (rcid).
     */
    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Type of recentchange event (rc_type). One of "edit", "new", "log", "categorize", or "external". (See Manual:Recentchanges table#rc_type)
     */
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    /**
     * Type of recentchange event (rc_type). One of "edit", "new", "log", "categorize", or "external". (See Manual:Recentchanges table#rc_type)
     */
    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Full page name, from Title::getPrefixedText.
     */
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    /**
     * Full page name, from Title::getPrefixedText.
     */
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * ID of relevant namespace of affected page (rc_namespace, page_namespace). This is -1 ("Special") for log events.
     */
    @JsonProperty("namespace")
    public Integer getNamespace() {
        return namespace;
    }

    /**
     * ID of relevant namespace of affected page (rc_namespace, page_namespace). This is -1 ("Special") for log events.
     */
    @JsonProperty("namespace")
    public void setNamespace(Integer namespace) {
        this.namespace = namespace;
    }

    /**
     * (rc_comment)
     */
    @JsonProperty("comment")
    public String getComment() {
        return comment;
    }

    /**
     * (rc_comment)
     */
    @JsonProperty("comment")
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * The rc_comment parsed into simple HTML. Optional
     */
    @JsonProperty("parsedcomment")
    public String getParsedcomment() {
        return parsedcomment;
    }

    /**
     * The rc_comment parsed into simple HTML. Optional
     */
    @JsonProperty("parsedcomment")
    public void setParsedcomment(String parsedcomment) {
        this.parsedcomment = parsedcomment;
    }

    /**
     * Unix timestamp (derived from rc_timestamp).
     */
    @JsonProperty("timestamp")
    public Integer getTimestamp() {
        return timestamp;
    }

    /**
     * Unix timestamp (derived from rc_timestamp).
     */
    @JsonProperty("timestamp")
    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * (rc_user_text)
     */
    @JsonProperty("user")
    public String getUser() {
        return user;
    }

    /**
     * (rc_user_text)
     */
    @JsonProperty("user")
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * (rc_bot)
     */
    @JsonProperty("bot")
    public Boolean getBot() {
        return bot;
    }

    /**
     * (rc_bot)
     */
    @JsonProperty("bot")
    public void setBot(Boolean bot) {
        this.bot = bot;
    }

    /**
     * $wgCanonicalServer
     */
    @JsonProperty("server_url")
    public String getServerUrl() {
        return serverUrl;
    }

    /**
     * $wgCanonicalServer
     */
    @JsonProperty("server_url")
    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    /**
     * $wgServerName
     */
    @JsonProperty("server_name")
    public String getServerName() {
        return serverName;
    }

    /**
     * $wgServerName
     */
    @JsonProperty("server_name")
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    /**
     * $wgScriptPath
     */
    @JsonProperty("server_script_path")
    public String getServerScriptPath() {
        return serverScriptPath;
    }

    /**
     * $wgScriptPath
     */
    @JsonProperty("server_script_path")
    public void setServerScriptPath(String serverScriptPath) {
        this.serverScriptPath = serverScriptPath;
    }

    @JsonProperty("category")
    public String getCategory() {
        return category;
    }

    @JsonProperty("category")
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * wfWikiID ($wgDBprefix, $wgDBname)
     */
    @JsonProperty("wiki")
    public String getWiki() {
        return wiki;
    }

    /**
     * wfWikiID ($wgDBprefix, $wgDBname)
     */
    @JsonProperty("wiki")
    public void setWiki(String wiki) {
        this.wiki = wiki;
    }

    /**
     * (rc_minor).
     */
    @JsonProperty("minor")
    public Boolean getMinor() {
        return minor;
    }

    /**
     * (rc_minor).
     */
    @JsonProperty("minor")
    public void setMinor(Boolean minor) {
        this.minor = minor;
    }

    /**
     * (rc_patrolled). This property only exists if patrolling is supported for this event (based on $wgUseRCPatrol, $wgUseNPPatrol).
     */
    @JsonProperty("patrolled")
    public Boolean getPatrolled() {
        return patrolled;
    }

    /**
     * (rc_patrolled). This property only exists if patrolling is supported for this event (based on $wgUseRCPatrol, $wgUseNPPatrol).
     */
    @JsonProperty("patrolled")
    public void setPatrolled(Boolean patrolled) {
        this.patrolled = patrolled;
    }

    /**
     * Length of old and new change
     */
    @JsonProperty("length")
    public Length getLength() {
        return length;
    }

    /**
     * Length of old and new change
     */
    @JsonProperty("length")
    public void setLength(Length length) {
        this.length = length;
    }

    /**
     * Old and new revision IDs
     */
    @JsonProperty("revision")
    public Revision getRevision() {
        return revision;
    }

    /**
     * Old and new revision IDs
     */
    @JsonProperty("revision")
    public void setRevision(Revision revision) {
        this.revision = revision;
    }

    /**
     * (rc_log_id)
     */
    @JsonProperty("log_id")
    public Integer getLogId() {
        return logId;
    }

    /**
     * (rc_log_id)
     */
    @JsonProperty("log_id")
    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    /**
     * (rc_log_type)
     */
    @JsonProperty("log_type")
    public String getLogType() {
        return logType;
    }

    /**
     * (rc_log_type)
     */
    @JsonProperty("log_type")
    public void setLogType(String logType) {
        this.logType = logType;
    }

    /**
     * (rc_log_action)
     */
    @JsonProperty("log_action")
    public String getLogAction() {
        return logAction;
    }

    /**
     * (rc_log_action)
     */
    @JsonProperty("log_action")
    public void setLogAction(String logAction) {
        this.logAction = logAction;
    }

    /**
     * Property only exists if event has rc_params.
     */
    @JsonProperty("log_params")
    public List<Object> getLogParams() {
        return logParams;
    }

    /**
     * Property only exists if event has rc_params.
     */
    @JsonProperty("log_params")
    public void setLogParams(List<Object> logParams) {
        this.logParams = logParams;
    }

    @JsonProperty("log_action_comment")
    public String getLogActionComment() {
        return logActionComment;
    }

    @JsonProperty("log_action_comment")
    public void setLogActionComment(String logActionComment) {
        this.logActionComment = logActionComment;
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
        return "RecentChange{" +
                "$schema='" + $schema + '\'' +
                ", meta=" + meta +
                ", id=" + id +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", namespace=" + namespace +
                ", comment='" + comment + '\'' +
                ", parsedcomment='" + parsedcomment + '\'' +
                ", category='" + category + '\'' +
                ", timestamp=" + timestamp +
                ", user='" + user + '\'' +
                ", bot=" + bot +
                ", serverUrl='" + serverUrl + '\'' +
                ", serverName='" + serverName + '\'' +
                ", serverScriptPath='" + serverScriptPath + '\'' +
                ", wiki='" + wiki + '\'' +
                ", minor=" + minor +
                ", patrolled=" + patrolled +
                ", length=" + length +
                ", revision=" + revision +
                ", logId=" + logId +
                ", logType='" + logType + '\'' +
                ", logAction='" + logAction + '\'' +
                ", logParams=" + logParams +
                ", logActionComment='" + logActionComment + '\'' +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
