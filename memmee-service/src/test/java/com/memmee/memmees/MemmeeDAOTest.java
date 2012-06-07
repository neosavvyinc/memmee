package com.memmee.memmees;

import com.memmee.attachment.dao.AttachmentDAO;
import com.memmee.memmees.AbstractMemmeeDAOTest;
import com.memmee.memmees.dao.MemmeeDAO;
import com.memmee.memmees.dto.Memmee;
import com.memmee.theme.dao.ThemeDAO;
import com.memmee.user.dao.UserDAO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.util.StringMapper;

import java.sql.SQLException;
import java.util.Date;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class MemmeeDAOTest extends AbstractMemmeeDAOTest {


    @Before
    public void setUp() throws Exception {
        this.database = factory.build(mysqlConfig, "mysql");
        final Handle handle = database.open();
        try {

            handle.createCall("DROP TABLE IF EXISTS memmee").invoke();
            
            
            handle.createCall("CREATE TABLE `memmee` (\n" +
            		  " `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
            		  " `userId` int(11) NOT NULL,\n" + 
            		  " `attachmentId` int(11) DEFAULT NULL,\n" +
            		  " `lastUpdateDate` date NOT NULL,\n" + 
            		  " `creationDate` date NOT NULL,\n" +
            		  " `displayDate` date NOT NULL,\n" +
            		  " `text` varchar(4096) DEFAULT NULL,\n" +
            		  " `title` varchar(1024) NOT NULL,\n" +
            		  " `shareKey` varchar(1024) DEFAULT NULL,\n" +
            		  " `themeId` int(11) DEFAULT NULL,\n" +
            		  " PRIMARY KEY (`id`)\n" +
            		  " ) ENGINE=InnoDB DEFAULT CHARSET=latin1").invoke();

        } catch (Exception e) {
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
        final MemmeeDAO dao = database.open(MemmeeDAO.class);

        try {

            dao.insert(new Long(1), "title", "text", new Date(), new Date(), new Date(), "shareKey", new Long(1), new Long(1));
            final String result = handle.createQuery("SELECT COUNT(*) FROM memmee").map(StringMapper.FIRST).first();

            assertThat(Integer.parseInt(result), equalTo(1));

        } finally {
            dao.close();
            handle.close();
        }
    }

    @Test
    public void testRead() throws Exception {
        final Handle handle = database.open();
        final MemmeeDAO dao = database.open(MemmeeDAO.class);
        
        try{
        	
        	
        	Long id = dao.insert(new Long(1), "title", "text", new Date(), new Date(), new Date(), "shareKey", new Long(1), new Long(1));
        	
            final Memmee memmee = dao.getMemmeeNoAttachment(id);
            assertThat(memmee.getId(), equalTo(id));

        } finally {
            dao.close();
            handle.close();
        }

    }


    @Test
    public void testUpdate() throws Exception {
        final MemmeeDAO dao = database.open(MemmeeDAO.class);

        try {

        	dao.insert(new Long(1), "title", "text", new Date(), new Date(), new Date(), "shareKey", new Long(1), new Long(1));
            final int result = dao.update(new Long(1), "title2", "text2", new Date(), new Date(), "shareKey2", new Long(2), new Long(2));

            assertThat(result, equalTo(1));
        } finally {
            dao.close();
        }
    }


    @Test
    public void testDelete() throws Exception {

        final Handle handle = database.open();
        final MemmeeDAO dao = database.open(MemmeeDAO.class);

        try {

            dao.delete(new Long(1));
            final String result = handle.createQuery("SELECT COUNT(*) FROM memmee").map(StringMapper.FIRST).first();

            assertThat(Integer.parseInt(result), equalTo(0));

        } finally {
            dao.close();
            handle.close();
        }

    }


    @Test
    public void pingWorks() throws Exception {
        try {
            database.ping();
        } catch (SQLException e) {
            e.printStackTrace();
            fail("shouldn't have thrown an exception but did");
        }
    }
}

