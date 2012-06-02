package com.memmee.user;

import com.memmee.user.dao.UserDAO;
import com.memmee.user.dto.User;
import com.memmee.util.MemmeeDAOTest;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.config.LoggingFactory;
import com.yammer.dropwizard.db.Database;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.dropwizard.db.DatabaseFactory;
import org.skife.jdbi.v2.Handle;

import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;

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

public class UserDAOTest extends MemmeeDAOTest{


    @Before
    public void setUp() throws Exception {
        this.database = factory.build(mysqlConfig, "mysql");
        final Handle handle = database.open();
        try {
        	
            handle.createCall("DROP TABLE IF EXISTS user").invoke();

            handle.createCall(
                    "CREATE TABLE `user` (\n" +
                            "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                            "  `firstName` varchar(1024) DEFAULT NULL,\n" +
                            "  `lastName` varchar(1024) DEFAULT NULL,\n" +
                            "  `email` varchar(4096) NOT NULL,\n" +
                            "  `password` varchar(4096) NOT NULL,\n" +
                            "  `apiKey` varchar(1024) DEFAULT NULL,\n" +
                            "  `apiDate` date DEFAULT NULL,\n" +
                            "  `creationDate` date NOT NULL,\n" +
                            "  PRIMARY KEY (`id`)\n" +
                            ") ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1"
            ).invoke();

        } catch (Exception e)
        {
            System.err.println(e);

        } finally {
            handle.close();
        }
    }

    @After
    public void tearDown() throws Exception {
        database.stop();
        this.database = null;
    }

//    @Test
//    public void createsAValidDBI() throws Exception {
//        final Handle handle = database.open();
//        try {
//            final Query<String> names = handle.createQuery("SELECT name FROM people WHERE age < ?")
//                    .bind(0, 50)
//                    .map(StringMapper.FIRST);
//            assertThat(ImmutableList.copyOf(names),
//                    is(ImmutableList.of("Coda Hale", "Kris Gale")));
//        } finally {
//            handle.close();
//        }
//    }

    
    
    
    
    @Test
    public void testSave() throws Exception {
    	
    	final Handle handle = database.open();
    	final UserDAO dao = database.open(UserDAO.class);
    	
    	try {
	      
	        dao.insert(new Long(1), "Adam", "Parrish", "aparrish@neosavvy.com", "password", "apiKey", new Date(), new Date());
	        final String result = handle.createQuery("SELECT COUNT(*) FROM user").map(StringMapper.FIRST).first();
	
	        assertThat(Integer.parseInt(result), equalTo(1));
        
    	}finally{
    		dao.close();
    		handle.close();
    	}
        
        

    }
    
    
    @Test
    public void testRead() throws Exception {
        final Handle handle = database.open();
        final UserDAO dao = database.open(UserDAO.class);
        
    try{

        dao.insert(new Long(1), "Adam", "Parrish", "aparrish@neosavvy.com", "password", "apiKey", new Date(), new Date());
        final User user = dao.getUser(new Long(1));
        assertThat(user.getId(), equalTo(new Long(1)));
        
    }finally{
    	dao.close();
		handle.close();
	}

    }
    
    
    @Test
    public void testUpdate() throws Exception {
        final UserDAO dao = database.open(UserDAO.class);
        
        try{
        	
         dao.insert(new Long(1), "Adam", "Parrish", "aparrish@neosavvy.com", "password", "apiKey", new Date(), new Date());
         final int result = dao.update(new Long(1), "Luke", "Lappin", "lukelappin@gmail.com", "password", "apiKey", new Date());

        assertThat(result,equalTo(1));
        }finally{
        	dao.close();
        }
    }
    
    
    @Test
    public void testDelete() throws Exception {
    
    final Handle handle = database.open();	    
    final UserDAO dao = database.open(UserDAO.class);
    	
    try{

        dao.delete(new Long(1));
        final String result = handle.createQuery("SELECT COUNT(*) FROM user").map(StringMapper.FIRST).first();

        assertThat(Integer.parseInt(result),equalTo(0));
        
    }finally{
    	dao.close();
		handle.close();
	}

    }
   
    
    @Test
    public void managesTheDatabaseWithTheEnvironment() throws Exception {
        final Database db = factory.build(mysqlConfig, "hsql");

        verify(environment).manage(db);
    }

//    @Test
//    public void sqlObjectsCanAcceptOptionalParams() throws Exception {
//        final PersonDAO dao = database.open(PersonDAO.class);
//        try {
//            assertThat(dao.findByName(Optional.of("Coda Hale")),
//                    is("Coda Hale"));
//        } finally {
//            database.close(dao);
//        }
//    }

//    @Test
//    public void sqlObjectsCanReturnImmutableLists() throws Exception {
//        final PersonDAO dao = database.open(PersonDAO.class);
//        try {
//            assertThat(dao.findAllNames(),
//                    is(ImmutableList.of("Coda Hale", "Kris Gale", "Old Guy")));
//        } finally {
//            database.close(dao);
//        }
//    }

    @Test
    @SuppressWarnings("CallToPrintStackTrace")
    public void pingWorks() throws Exception {
        try {
            database.ping();
        } catch (SQLException e) {
            e.printStackTrace();
            fail("shouldn't have thrown an exception but did");
        }
    }
}

