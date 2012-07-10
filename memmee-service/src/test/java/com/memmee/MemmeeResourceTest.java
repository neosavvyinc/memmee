package com.memmee;

import base.ResourceIntegrationTest;
import com.memmee.attachment.dao.AttachmentDAO;
import com.memmee.attachment.dao.TransactionalAttachmentDAO;
import com.memmee.memmees.dao.MemmeeDAO;
import com.memmee.memmees.dao.TransactionalMemmeeDAO;
import com.memmee.memmees.dto.Memmee;
import com.memmee.user.dao.UserDAO;
import com.yammer.dropwizard.testing.ResourceTest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.Handle;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: waparrish
 * Date: 7/6/12
 * Time: 9:12 PM
 */
public class MemmeeResourceTest extends ResourceIntegrationTest {

    private static Memmee memmee = new Memmee();

    static {
        memmee.setCreationDate(new Date());
        memmee.setDisplayDate(new Date());
        memmee.setId(-100L);
        memmee.setShareKey("testShareKey");
        memmee.setText("Memmee text");
        memmee.setUserId(-100L);
    }

    private static UserDAO userDAO;
    private static TransactionalMemmeeDAO txMemmeeDAO;
    private static TransactionalAttachmentDAO txAttachmentDAO;

    @Override
    protected void setUpResources() throws Exception {
        super.setUpResources();

        //setup Daos
        userDAO = database.open(UserDAO.class);
        txMemmeeDAO = database.open(TransactionalMemmeeDAO.class);
        txAttachmentDAO = database.open(TransactionalAttachmentDAO.class);

        userDAO.insert("Trevor", "Ewen", "me@trevorewen.com", "password", "apiKey", new Date(), new Date());

        addResource(new MemmeeResource(userDAO, txMemmeeDAO, txAttachmentDAO));
    }

    @Test
    public void testGetMemmees() {
        List<Memmee> memmees = client().resource("/getmemmees?apiKey=apiKey").get(List.class);
        assertNotNull(memmees);
    }

    @Test
    public void simpleResourceTest() throws Exception {
        assertTrue(true);
    }

    @AfterClass
    public static void tearDownClass() throws Exception{
        userDAO.close();
        txMemmeeDAO.close();
        txAttachmentDAO.close();

        ResourceIntegrationTest.tearDownClass();
    }
}

