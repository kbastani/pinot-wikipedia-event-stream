package io.example.schema.category;

import io.example.schema.page.RecentChange;

import java.util.Optional;

public class CategoryChange {

    private Integer id;
    private String uri;
    private String title;
    private String category;
    private String comment;
    private String parsedComment;
    private String user;
    private String domain;
    private String wiki;
    private String type;
    private String dateTime;
    private Integer changeLength;
    private String uuid;
    private Long changedTime;

    public CategoryChange() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getParsedComment() {
        return parsedComment;
    }

    public void setParsedComment(String parsedComment) {
        this.parsedComment = parsedComment;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getWiki() {
        return wiki;
    }

    public void setWiki(String wiki) {
        this.wiki = wiki;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getChangeLength() {
        return changeLength;
    }

    public void setChangeLength(Integer changeLength) {
        this.changeLength = changeLength;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getChangedTime() {
        return changedTime;
    }

    public void setChangedTime(Long changedTime) {
        this.changedTime = changedTime;
    }

    public static CategoryChange create(RecentChange recentChange, String category) {
        CategoryChange categoryChange = new CategoryChange();
        categoryChange.setId(recentChange.getId());
        categoryChange.setTitle(recentChange.getTitle());
        categoryChange.setCategory(category);
        categoryChange.setChangedTime(recentChange.getTimestamp());
        if (recentChange.getLength() != null)
            categoryChange.setChangeLength(Optional.ofNullable(recentChange.getLength().getOld()).orElse(0) -
                    Optional.ofNullable(recentChange.getLength().getNew()).orElse(0));
        categoryChange.setComment(recentChange.getComment());
        categoryChange.setDateTime(recentChange.getMeta().getDt().toString());
        categoryChange.setDomain(recentChange.getMeta().getDomain());
        categoryChange.setParsedComment(recentChange.getParsedcomment());
        categoryChange.setType(recentChange.getType());
        categoryChange.setUuid(recentChange.getMeta().getId());
        categoryChange.setWiki(recentChange.getWiki());
        categoryChange.setUser(recentChange.getUser());
        categoryChange.setUri(recentChange.getMeta().getUri());

        return categoryChange;
    }

    @Override
    public String toString() {
        return "CategoryChange{" +
                "id=" + id +
                ", uri='" + uri + '\'' +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", comment='" + comment + '\'' +
                ", parsedComment='" + parsedComment + '\'' +
                ", user='" + user + '\'' +
                ", domain='" + domain + '\'' +
                ", wiki='" + wiki + '\'' +
                ", type='" + type + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", changeLength=" + changeLength +
                ", uuid='" + uuid + '\'' +
                ", changedTime=" + changedTime +
                '}';
    }
}
