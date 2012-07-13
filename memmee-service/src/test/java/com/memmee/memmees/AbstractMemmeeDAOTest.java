package com.memmee.memmees;

import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.config.LoggingFactory;
import com.yammer.dropwizard.db.Database;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.dropwizard.db.DatabaseFactory;
import org.junit.After;
import org.junit.Before;
import static org.mockito.Mockito.mock;



public abstract class AbstractMemmeeDAOTest {
	
    protected final DatabaseConfiguration mysqlConfig = new DatabaseConfiguration();
    {
        LoggingFactory.bootstrap();
        mysqlConfig.setUrl("jdbc:mysql://localhost:3306/memmeetest");
        mysqlConfig.setUser("memmeetest");      //PLEASE CREATE THIS USER - DO NOT SWITCH BACK TO commons
        mysqlConfig.setPassword("memmeetest");
        mysqlConfig.setDriverClass("com.mysql.jdbc.Driver");
    }
    protected final Environment environment = mock(Environment.class);
    protected final DatabaseFactory factory = new DatabaseFactory(environment);
    protected Database database;
	
    @Before
    public abstract void setUp() throws Exception;
    
    @After
    public abstract void tearDown() throws Exception;
    
    

}
