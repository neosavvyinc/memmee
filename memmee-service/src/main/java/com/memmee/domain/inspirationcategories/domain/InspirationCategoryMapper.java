package com.memmee.domain.inspirationcategories.domain;


import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InspirationCategoryMapper implements ResultSetMapper<InspirationCategory> {

    @Override
    public InspirationCategory map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        InspirationCategory inspirationCategory = new InspirationCategory();
        inspirationCategory.setId(resultSet.getLong("id"));
        inspirationCategory.setName(resultSet.getString("name"));

        return inspirationCategory;
    }
}
