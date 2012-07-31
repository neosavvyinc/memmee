package com.memmee.domain.memmees.dto;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;


import java.sql.ResultSet;
import java.sql.SQLException;

public class MemmeeMapper implements ResultSetMapper<Memmee> {

    @Override
    public Memmee map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {

        Memmee memmee = new Memmee();
        memmee.setId(resultSet.getLong("id"));
        memmee.setText(resultSet.getString("text"));
        memmee.setUserId(resultSet.getLong("userId"));
        memmee.setCreationDate(resultSet.getDate("creationDate"));
        memmee.setLastUpdateDate(resultSet.getDate("lastUpdateDate"));
        memmee.setDisplayDate(resultSet.getDate("displayDate"));
        memmee.setShareKey(resultSet.getString("shareKey"));

        return memmee;
    }
}