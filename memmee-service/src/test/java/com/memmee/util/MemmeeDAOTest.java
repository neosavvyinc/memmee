package com.memmee.util;

import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.config.LoggingFactory;
import com.yammer.dropwizard.db.Database;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.dropwizard.db.DatabaseFactory;
import org.skife.jdbi.v2.Handle;

import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.Query;
import org.skife.jdbi.v2.util.StringMapper;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public abstract class MemmeeDAOTest {
	
    protected final DatabaseConfiguration mysqlConfig = new DatabaseConfiguration();
    {
        LoggingFactory.bootstrap();
        mysqlConfig.setUrl("jdbc:mysql://localhost:3306/commons");
        mysqlConfig.setUser("commons");
        mysqlConfig.setPassword("commons");
        mysqlConfig.setDriverClass("com.mysql.jdbc.Driver");
        mysqlConfig.setValidationQuery("SELECT 1 FROM commons.user");
    }
    protected final Environment environment = mock(Environment.class);
    protected final DatabaseFactory factory = new DatabaseFactory(environment);
    protected Database database;
	
    @Before
    public abstract void setUp() throws Exception;
    
    @After
    public abstract void tearDown() throws Exception;
    
    

}
