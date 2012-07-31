package com.memmee.domain.attachment.dto;

import java.io.Serializable;


public class Attachment implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5360464163625623532L;

    private Long id;

    private String filePath;

    private String thumbFilePath;

    private String type;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getThumbFilePath() {
        return thumbFilePath;
    }

    public void setThumbFilePath(String thumbFilePath) {
        this.thumbFilePath = thumbFilePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attachment)) return false;

        Attachment that = (Attachment) o;

        if (filePath != null ? !filePath.equals(that.filePath) : that.filePath != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (thumbFilePath != null ? !thumbFilePath.equals(that.thumbFilePath) : that.thumbFilePath != null)
            return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (filePath != null ? filePath.hashCode() : 0);
        result = 31 * result + (thumbFilePath != null ? thumbFilePath.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
