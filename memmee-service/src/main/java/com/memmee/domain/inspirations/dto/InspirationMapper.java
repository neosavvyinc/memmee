package com.memmee.domain.inspirations.dto;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InspirationMapper implements ResultSetMapper<Inspiration> {

    @Override
    public Inspiration map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {

        Inspiration inspiration = new Inspiration();
        inspiration.setId(resultSet.getLong("id"));
        inspiration.setText(resultSet.getString("text"));
        inspiration.setCreationDate(resultSet.getDate("creationDate"));
        inspiration.setLastUpdateDate(resultSet.getDate("lastUpdateDate"));

        return inspiration;
    }
}
