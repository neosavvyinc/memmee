package com.memmee.domain.password.dto;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class PasswordMapper implements ResultSetMapper<Password> {

    @Override
    public Password map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        Password password = new Password();

        password.setId(resultSet.getLong("id"));
        password.setValue(resultSet.getString("value"));
        password.setTemp(resultSet.getBoolean("temp"));

        return password;
    }
}
