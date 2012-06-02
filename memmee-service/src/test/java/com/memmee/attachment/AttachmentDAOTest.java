package com.memmee.attachment;

import com.memmee.attachment.dao.AttachmentDAO;
import com.memmee.attachment.dto.Attachment;
import com.memmee.memmees.dao.MemmeeDAO;
import com.memmee.attachment.dao.AttachmentDAO;
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

public class AttachmentDAOTest extends MemmeeDAOTest{


    @Before
    public void setUp() throws Exception {
        this.database = factory.build(mysqlConfig, "mysql");
        final Handle handle = database.open();
        try {
        	
            handle.createCall("DROP TABLE IF EXISTS attachment").invoke();

            handle.createCall(
            		"CREATE TABLE `attachment` (\n" +
            				  " `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
            				  " `memmeeId` int(11) NOT NULL,\n" + 
            				  " `filePath` varchar(1024) DEFAULT NULL,\n" + 
            				  " `type` varchar(20) DEFAULT NULL,\n" +
            				  " PRIMARY KEY (`id`)\n" +
            				  ") ENGINE=InnoDB DEFAULT CHARSET=latin1").invoke();
            		
         
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

    
    @Test
    public void testSave() throws Exception {
    	
    	final Handle handle = database.open();
    	final AttachmentDAO dao = database.open(AttachmentDAO.class);
    	
    	try {
	      
	        dao.insert(new Long(1), new Long(1), "filePath", "Image");
	        final String result = handle.createQuery("SELECT COUNT(*) FROM attachment").map(StringMapper.FIRST).first();
	
	        assertThat(Integer.parseInt(result), equalTo(1));
        
    	}finally{
    		dao.close();
    		handle.close();
    	}
        
        

    }
    
    
    @Test
    public void testRead() throws Exception {
        final Handle handle = database.open();
        final AttachmentDAO dao = database.open(AttachmentDAO.class);
        
    try{

    	dao.insert(new Long(1), new Long(1), "filePath", "Image");
        final Attachment attachment = dao.getAttachment(new Long(1));
       

        assertThat(attachment.getId(), equalTo(new Long(1)));
        
    }finally{
    	dao.close();
		handle.close();
	}

    }
    
    
    @Test
    public void testUpdate() throws Exception {
        final AttachmentDAO dao = database.open(AttachmentDAO.class);
        
        try{
        	
         dao.insert(new Long(1), new Long(1), "filePath", "Image");
         final int result = dao.update(new Long(1), "filePath2", "Image2");

         assertThat(result,equalTo(1));
        }finally{
        	dao.close();
        }
    }
    
    
    @Test
    public void testDelete() throws Exception {
    
    final Handle handle = database.open();	    
    final AttachmentDAO dao = database.open(AttachmentDAO.class);
    	
    try{

        dao.delete(new Long(1));
        final String result = handle.createQuery("SELECT COUNT(*) FROM attachment").map(StringMapper.FIRST).first();

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

