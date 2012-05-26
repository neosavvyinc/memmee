package com.memmee.memmees.dto;

import java.io.Serializable;
import java.util.Date;

import com.memmee.attachment.dto.Attachment;

public class Memmee implements Serializable{
	

	    private Long id;

	    private Long userId;
	    
	    private String title;

	    private Attachment attachment;
	    
	    private Date date;
	    
	    
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

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		public Attachment getAttachment() {
			return attachment;
		}

		public void setAttachment(Attachment attachment) {
			this.attachment = attachment;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}


}
