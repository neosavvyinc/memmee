package com.memmee.memmees.dto;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.memmee.attachment.dto.Attachment;


import java.sql.ResultSet;
import java.sql.SQLException;

	public class MemmeeAttachmentMapper implements ResultSetMapper<Memmee> {

	    @Override
	    public Memmee map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
	    	
	    	Memmee memmee = new Memmee();

	    	memmee.setId(resultSet.getLong("id"));
	    	memmee.setTitle(resultSet.getString("title"));
	    	memmee.setText(resultSet.getString("text"));
	    	memmee.setUserId(resultSet.getLong("userId"));
	    	memmee.setDate(resultSet.getDate("date"));
	    	
	    	Attachment attachment  = new Attachment();
	    	
	    	attachment.setId(resultSet.getLong("attachmentId"));
	    	attachment.setMediaUrl(resultSet.getString("mediaUrl"));
	    	attachment.setMemmeeId(resultSet.getLong("id"));
	    	attachment.setType(resultSet.getString("type"));
	    	
	    	memmee.setAttachment(attachment);
	    	
	        return memmee;
	    }
	}

	
