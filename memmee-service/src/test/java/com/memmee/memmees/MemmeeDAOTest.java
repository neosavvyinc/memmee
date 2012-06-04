package com.memmee.memmees;

import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.config.LoggingFactory;
import com.yammer.dropwizard.db.Database;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.dropwizard.db.DatabaseFactory;
import org.junit.After;
import org.junit.Before;
import static org.mockito.Mockito.mock;



public abstract class MemmeeDAOTest {
	
    protected final DatabaseConfiguration mysqlConfig = new DatabaseConfiguration();
    {
        LoggingFactory.bootstrap();
        mysqlConfig.setUrl("jdbc:mysql://localhost:3306/memmeetest");
        mysqlConfig.setUser("memmee");
        mysqlConfig.setPassword("memmee");
        mysqlConfig.setDriverClass("com.mysql.jdbc.Driver");
        mysqlConfig.setValidationQuery("SELECT 1 FROM memmeetest.user");
    }
    protected final Environment environment = mock(Environment.class);
    protected final DatabaseFactory factory = new DatabaseFactory(environment);
    protected Database database;
	
    @Before
    public abstract void setUp() throws Exception;
    
    @After
    public abstract void tearDown() throws Exception;
    
    

}
