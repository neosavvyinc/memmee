package com.memmee.domain.inspirations.dto;

import com.memmee.domain.inspirationcategories.domain.InspirationCategory;
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
        inspiration.setInspirationCategoryIndex(resultSet.getLong("inspirationCategoryIndex"));
        inspiration.setCreationDate(resultSet.getDate("creationDate"));
        inspiration.setLastUpdateDate(resultSet.getDate("lastUpdateDate"));

        InspirationCategory inspirationCategory = new InspirationCategory();
        inspirationCategory.setId(resultSet.getLong("inspirationCategoryId"));
        inspirationCategory.setName(resultSet.getString("inspirationCategoryName"));
        inspiration.setInspirationCategory(inspirationCategory);

        return inspiration;
    }
}
