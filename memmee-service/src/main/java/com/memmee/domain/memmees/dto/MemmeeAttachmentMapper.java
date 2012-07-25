package com.memmee.domain.memmees.dto;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.memmee.domain.attachment.dto.Attachment;


import java.sql.ResultSet;
import java.sql.SQLException;

public class MemmeeAttachmentMapper extends MemmeeMapper implements ResultSetMapper<Memmee> {

    @Override
    public Memmee map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {

        Memmee memmee = super.map(i, resultSet, statementContext);

        Attachment attachment = new Attachment();
        attachment.setId(resultSet.getLong("attachmentId"));
        attachment.setFilePath(resultSet.getString("filePath"));
        attachment.setThumbFilePath(resultSet.getString("thumbFilePath"));
        attachment.setType(resultSet.getString("type"));
        memmee.setAttachment(attachment);

        return memmee;
    }
}

	
