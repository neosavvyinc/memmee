package com.memmee.domain.inspirations.dto;

import com.memmee.domain.inspirationcategories.domain.InspirationCategory;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class Inspiration implements Serializable {

    private Long id;

    @NotNull
    private String text;

    private InspirationCategory inspirationCategory;

    private Long inspirationCategoryIndex;

    private Date creationDate;

    private Date lastUpdateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public InspirationCategory getInspirationCategory() {
        return inspirationCategory;
    }

    public void setInspirationCategory(InspirationCategory inspirationCategory) {
        this.inspirationCategory = inspirationCategory;
    }

    public Long getInspirationCategoryIndex() {
        return inspirationCategoryIndex;
    }

    public void setInspirationCategoryIndex(Long inspirationCategoryIndex) {
        this.inspirationCategoryIndex = inspirationCategoryIndex;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Inspiration)) return false;

        Inspiration that = (Inspiration) o;

        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (lastUpdateDate != null ? !lastUpdateDate.equals(that.lastUpdateDate) : that.lastUpdateDate != null)
            return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (lastUpdateDate != null ? lastUpdateDate.hashCode() : 0);
        return result;
    }
}
