package com.memmee;

import com.memmee.attachment.dao.AttachmentDAO;
import com.memmee.attachment.dao.TransactionalAttachmentDAO;
import com.memmee.memmees.dao.MemmeeDAO;
import com.memmee.memmees.dao.TransactionalMemmeeDAO;
import com.memmee.memmees.dto.Memmee;
import com.memmee.user.dao.UserDAO;
import com.yammer.dropwizard.testing.ResourceTest;
import org.junit.Test;

import java.util.Date;

import static org.mockito.Mockito.mock;

/**
 * Created with IntelliJ IDEA.
 * User: waparrish
 * Date: 7/6/12
 * Time: 9:12 PM
 */
public class MemmeeResourceTest extends ResourceTest {

    private static Memmee memmee = new Memmee();
    static {
        memmee.setCreationDate(new Date());
        memmee.setDisplayDate(new Date());
        memmee.setId(-100L);
        memmee.setShareKey("testShareKey");
        memmee.setText("Memmee text");
        memmee.setTitle("Memmee title");
        memmee.setUserId(-100L);
    }

    private UserDAO userDAO = mock(UserDAO.class);
    private TransactionalMemmeeDAO txMemmeeDAO = mock(TransactionalMemmeeDAO.class);
    private TransactionalAttachmentDAO txAttachmentDAO = mock(TransactionalAttachmentDAO.class);

    @Override
    protected void setUpResources() throws Exception {
        addResource(new MemmeeResource(userDAO, txMemmeeDAO, txAttachmentDAO));
    }

    @Test
    public void simpleResourceTest() throws Exception {


    }
}

