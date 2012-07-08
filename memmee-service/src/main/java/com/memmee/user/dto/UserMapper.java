package com.memmee.user.dto;


import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements ResultSetMapper<User> {

    @Override
    public User map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        User user = new User();

        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setFirstName(resultSet.getString("firstName"));
        user.setLastName(resultSet.getString("lastName"));
        user.setId(resultSet.getLong("id"));
        user.setApiKey(resultSet.getString("apiKey"));
        user.setApiDate(resultSet.getDate("apiDate"));
        user.setCreationDate(resultSet.getDate("creationDate"));

        return user;
    }
}
