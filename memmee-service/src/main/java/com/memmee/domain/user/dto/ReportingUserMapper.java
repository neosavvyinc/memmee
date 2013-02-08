package com.memmee.domain.user.dto;

import com.memmee.domain.password.dto.Password;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportingUserMapper implements ResultSetMapper<User> {

    @Override
    public User map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        User user = new User();

        user.setId(resultSet.getLong("id"));
        user.setEmail(resultSet.getString("email"));
        user.setFirstName(resultSet.getString("firstName"));
        user.setCreationDate(resultSet.getDate("creationDate"));

        try {
            user.setMemmeeCount(resultSet.getLong("memmeeCount"));
        } catch (SQLException e )
        {
            //swallow as this only matters for some of the queries
        }

        try {
            user.setShareCount(resultSet.getLong("shareCount"));
        } catch (SQLException e )
        {
            //swallow as this only matters for some of the queries
        }


        return user;
    }
}

