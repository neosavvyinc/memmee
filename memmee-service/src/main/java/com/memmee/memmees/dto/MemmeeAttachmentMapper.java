package com.memmee.memmees.dto;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.memmee.attachment.dto.Attachment;
import com.memmee.theme.dto.Theme;


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
        memmee.setCreationDate(resultSet.getDate("creationDate"));
        memmee.setLastUpdateDate(resultSet.getDate("lastUpdateDate"));
        memmee.setDisplayDate(resultSet.getDate("creationDate"));
        memmee.setShareKey(resultSet.getString("shareKey"));

        Attachment attachment  = new Attachment();
        attachment.setId(resultSet.getLong("attachmentId"));
        attachment.setFilePath(resultSet.getString("filePath"));
        attachment.setMemmeeId(resultSet.getLong("id"));
        attachment.setType(resultSet.getString("type"));
        memmee.setAttachment(attachment);

        /*
        Theme theme = new Theme();
        theme.setId(resultSet.getLong("themeId"));
        theme.setName(resultSet.getString("name"));
        theme.setStylePath(resultSet.getString("stylePath"));
        memmee.setTheme(theme);
        */

        return memmee;
    }
}

	
