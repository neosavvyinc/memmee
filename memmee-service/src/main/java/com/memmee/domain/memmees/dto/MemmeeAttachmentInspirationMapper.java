package com.memmee.domain.memmees.dto;

import com.memmee.domain.inspirations.dto.Inspiration;
import org.skife.jdbi.v2.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MemmeeAttachmentInspirationMapper extends MemmeeAttachmentMapper {

    @Override
    public Memmee map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        Memmee memmee = super.map(i, resultSet, statementContext);

        Inspiration inspiration = new Inspiration();
        inspiration.setId(resultSet.getLong("inspirationId"));
        inspiration.setText(resultSet.getString("inspirationText"));
        inspiration.setCreationDate(resultSet.getDate("inspirationCreationDate"));
        inspiration.setLastUpdateDate(resultSet.getDate("inspirationLastUpdateDate"));
        memmee.setInspiration(inspiration);

        return memmee;
    }
}
