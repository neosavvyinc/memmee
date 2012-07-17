package com.memmee;

import base.ResourceIntegrationTest;
import com.memmee.attachment.dao.TransactionalAttachmentDAO;
import com.memmee.builder.MemmeeURLBuilder;
import com.memmee.memmees.dao.TransactionalMemmeeDAO;
import com.memmee.memmees.dto.Memmee;
import com.memmee.user.dao.UserDAO;
import com.memmee.util.DateUtil;
import com.yammer.dropwizard.bundles.DBIExceptionsBundle;
import org.junit.*;

import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.notNull;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

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

    private static Long userId;
    private static Long memmeeId;
    private static Long attachmentId;

    @Override
    protected void setUpResources() throws Exception {
        super.setUpResources();

        //setup Daos
        userDAO = database.open(UserDAO.class);
        txMemmeeDAO = database.open(TransactionalMemmeeDAO.class);
        txAttachmentDAO = database.open(TransactionalAttachmentDAO.class);

        userId = userDAO.insert("memmee_resource_test", "user", "memmee_resource_test", "password", "apiKey", new Date(), new Date());

        addResource(new MemmeeResource(userDAO, txMemmeeDAO, txAttachmentDAO));
    }

    @Test
    @Ignore
    public void testGetMemmees() {
        memmeeId = insertTestData();

        List<Memmee> memmees = client().resource("/memmeerest/getmemmees?apiKey=apiKey").get(List.class);
        assertNotNull(memmees);
        assertThat(memmees.size(), is(equalTo(1)));
        assertThat(memmees.get(0).getUserId(), is(equalTo(userId)));
        assertThat(memmees.get(0).getText(), is("This is a memmee"));
        assertThat(memmees.get(0).getLastUpdateDate(), is(notNull()));
        assertThat(memmees.get(0).getCreationDate(), is(notNull()));
        assertThat(memmees.get(0).getDisplayDate(), is(notNull()));
        assertThat(memmees.get(0).getShareKey(), is(equalTo("shareKey")));
        assertThat(memmees.get(0).getAttachment(), is(notNull()));
    }

    @Test
    public void testGetMemmee() {
        memmeeId = insertTestData();

        Memmee memmee = client().resource(new MemmeeURLBuilder().
                setMethodURL("getmemmee").
                setApiKeyParam("apiKey").
                setIdParam(memmeeId).
                build()).get(Memmee.class);
        assertThat(memmee.getUserId(), is(equalTo(userId)));
        assertThat(memmee.getText(), is("This is a memmee"));
        assertThat(memmee.getLastUpdateDate(), is(not(nullValue())));
        assertThat(memmee.getCreationDate(), is(not(nullValue())));
        assertThat(memmee.getDisplayDate(), is(not(nullValue())));
        assertThat(memmee.getShareKey(), is(equalTo("shareKey")));
        assertThat(memmee.getAttachment(), is(not(nullValue())));
    }

    @Test
    public void testGetMemmeeDefault() {
        memmeeId = insertTestData();
        txMemmeeDAO.insert(userId, "This is a later memmee", new Date(), new Date(), new Date(), "shareKey", attachmentId, Long.parseLong("1"));

        Memmee memmee = client().resource(new MemmeeURLBuilder().
                setMethodURL("getmemmee").
                setApiKeyParam("apiKey").
                build()).get(Memmee.class);
        assertThat(memmee.getUserId(), is(equalTo(userId)));
        assertThat(memmee.getText(), is("This is a later memmee"));
        assertThat(memmee.getLastUpdateDate(), is(not(nullValue())));
        assertThat(memmee.getCreationDate(), is(not(nullValue())));
        assertThat(memmee.getDisplayDate(), is(not(nullValue())));
        assertThat(memmee.getShareKey(), is(equalTo("shareKey")));
        assertThat(memmee.getAttachment(), is(not(nullValue())));
    }

    @Test
    public void testDelete() {
        memmeeId = insertTestData();

        client().resource(new MemmeeURLBuilder().setMethodURL("deletememmee").setApiKeyParam("apiKey").setIdParam(memmeeId).build()).delete();

        Memmee myMemmee = txMemmeeDAO.getMemmee(memmeeId);
        assertThat(myMemmee, is(nullValue()));
    }

    @Test
    public void testDeleteInvalidId() {
        memmeeId = insertTestData();

        client().resource(new MemmeeURLBuilder().setMethodURL("deletememmee").setApiKeyParam("apiKey").setIdParam(memmeeId + 75).build()).delete();

        Memmee myMemmee = txMemmeeDAO.getMemmee(memmeeId);
        assertThat(myMemmee, is(not(nullValue())));
    }

    protected Long insertTestData() {
        attachmentId = txAttachmentDAO.insert("this_is_a_file_path.path", "this_is_a_thumb_file_path.path", "image/jpeg");
        Date date = DateUtil.getDate(2011, 6, 12);
        return txMemmeeDAO.insert(userId, "This is a memmee", date, date, date, "shareKey", attachmentId, Long.parseLong("1"));
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        userDAO.close();
        txMemmeeDAO.close();
        txAttachmentDAO.close();

        ResourceIntegrationTest.tearDownClass();
    }
}

