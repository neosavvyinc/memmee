package com.memmee.memmees.dto;

import java.io.Serializable;
import java.util.Date;

import com.memmee.attachment.dto.Attachment;
import com.memmee.theme.dto.Theme;

import javax.validation.constraints.NotNull;

public class Memmee implements Serializable {

    private static final long serialVersionUID = -2311129046798681911L;

    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    private String text;

    private Attachment attachment;

    private Date creationDate;

    private Date lastUpdateDate;

    private Date displayDate;

    private String shareKey;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Date getDisplayDate() {
        return displayDate;
    }

    public void setDisplayDate(Date displayDate) {
        this.displayDate = displayDate;
    }

    public String getShareKey() {
        return shareKey;
    }

    public void setShareKey(String shareKey) {
        this.shareKey = shareKey;
    }

    /*
    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }
    */

}
