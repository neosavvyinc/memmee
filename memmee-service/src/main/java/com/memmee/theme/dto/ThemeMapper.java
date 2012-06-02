package com.memmee.theme.dto;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

	public class ThemeMapper implements ResultSetMapper<Theme> {

	    @Override
	    public Theme map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
	    	
	    	Theme theme = new Theme();

	    	theme.setId(resultSet.getLong("id"));
	    	theme.setStylePath(resultSet.getString("stylePath"));
	    	theme.setName(resultSet.getString("name"));
	    	
	        return theme;
	    }
	}

	
