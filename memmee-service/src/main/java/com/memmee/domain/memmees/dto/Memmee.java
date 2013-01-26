package com.memmee.domain.memmees.dto;

import java.io.Serializable;
import java.util.Date;

import com.memmee.domain.attachment.dto.Attachment;
import com.memmee.domain.inspirations.dto.Inspiration;
import com.memmee.theme.dto.Theme;

import javax.validation.constraints.NotNull;

public class Memmee implements Serializable {

    public static String NO_MEMMEES_TEXT = "You have no memmees.";

    private static final long serialVersionUID = -2311129046798681911L;

    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    private String text;

    private Attachment attachment;

    private Theme theme;

    private Inspiration inspiration;

    private Date creationDate;

    private Date lastUpdateDate;

    private Date displayDate;

    private String shareKey;

    private String shortenedUrl;

    private String shortenedFacebookUrl;

    public Memmee() {}

    public Memmee(Long userId, String text) {
        this.text = text;
        this.userId = userId;
    }

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

    public Inspiration getInspiration() {
        return inspiration;
    }

    public void setInspiration(Inspiration inspiration) {
        this.inspiration = inspiration;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public String getShortenedUrl() {
        return shortenedUrl;
    }

    public void setShortenedUrl(String shortenedUrl) {
        this.shortenedUrl = shortenedUrl;
    }

    public String getShortenedFacebookUrl() {
        return shortenedFacebookUrl;
    }

    public void setShortenedFacebookUrl(String shortenedFacebookUrl) {
        this.shortenedFacebookUrl = shortenedFacebookUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Memmee)) return false;

        Memmee memmee = (Memmee) o;

        if (attachment != null ? !attachment.equals(memmee.attachment) : memmee.attachment != null) return false;
        if (creationDate != null ? !creationDate.equals(memmee.creationDate) : memmee.creationDate != null)
            return false;
        if (displayDate != null ? !displayDate.equals(memmee.displayDate) : memmee.displayDate != null) return false;
        if (id != null ? !id.equals(memmee.id) : memmee.id != null) return false;
        if (inspiration != null ? !inspiration.equals(memmee.inspiration) : memmee.inspiration != null) return false;
        if (lastUpdateDate != null ? !lastUpdateDate.equals(memmee.lastUpdateDate) : memmee.lastUpdateDate != null)
            return false;
        if (shareKey != null ? !shareKey.equals(memmee.shareKey) : memmee.shareKey != null) return false;
        if (text != null ? !text.equals(memmee.text) : memmee.text != null) return false;
        if (userId != null ? !userId.equals(memmee.userId) : memmee.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (attachment != null ? attachment.hashCode() : 0);
        result = 31 * result + (inspiration != null ? inspiration.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (lastUpdateDate != null ? lastUpdateDate.hashCode() : 0);
        result = 31 * result + (displayDate != null ? displayDate.hashCode() : 0);
        result = 31 * result + (shareKey != null ? shareKey.hashCode() : 0);
        return result;
    }

}
