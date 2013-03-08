package com.memmee.domain.user.dto;


import com.memmee.domain.password.dto.Password;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements ResultSetMapper<User> {

    @Override
    public User map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        User user = new User();

        user.setEmail(resultSet.getString("email"));
        user.setFirstName(resultSet.getString("firstName"));
        user.setId(resultSet.getLong("id"));
        user.setApiKey(resultSet.getString("apiKey"));
        user.setApiDate(resultSet.getDate("apiDate"));
        user.setCreationDate(resultSet.getDate("creationDate"));
        user.setLoginCount(resultSet.getLong("loginCount"));
        user.setPhone(resultSet.getString("phone"));

        Password password = new Password();
        password.setId(resultSet.getLong("passwordId"));
        password.setValue(resultSet.getString("passwordValue"));
        password.setTemp(resultSet.getInt("passwordTemp") != 0);
        user.setPassword(password);

        return user;
    }
}
