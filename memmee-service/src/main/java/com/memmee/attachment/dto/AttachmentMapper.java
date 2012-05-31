package com.memmee.attachment.dto;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

	public class AttachmentMapper implements ResultSetMapper<Attachment> {

	    @Override
	    public Attachment map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
	    	
	    	Attachment attachment = new Attachment();

	    	attachment.setId(resultSet.getLong("id"));
	    	attachment.setFilePath(resultSet.getString("filePath"));
	    	attachment.setMemmeeId(resultSet.getLong("memmeeId"));
	    	attachment.setType(resultSet.getString("type"));
	    	
	        return attachment;
	    }
	}

	
