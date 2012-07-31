package com.memmee;

import base.ResourceIntegrationTest;
import com.memmee.domain.attachment.dao.TransactionalAttachmentDAO;
import com.memmee.builder.MemmeeURLBuilder;
import com.memmee.domain.attachment.dto.Attachment;
import com.memmee.domain.inspirations.dao.TransactionalInspirationDAO;
import com.memmee.domain.inspirations.dto.Inspiration;
import com.memmee.domain.memmees.dao.TransactionalMemmeeDAO;
import com.memmee.domain.memmees.dto.Memmee;
import com.memmee.domain.user.dao.UserDAO;
import com.memmee.util.DateUtil;
import org.junit.*;

import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.notNull;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

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
    private static TransactionalInspirationDAO txInspirationDAO;

    private static Long userId;
    private static Long memmeeId;
    private static Long attachmentId;
    private static Long inspirationId;

    @Override
    protected void setUpResources() throws Exception {
        super.setUpResources();

        //setup Daos
        userDAO = database.open(UserDAO.class);
        txMemmeeDAO = database.open(TransactionalMemmeeDAO.class);
        txAttachmentDAO = database.open(TransactionalAttachmentDAO.class);
        txInspirationDAO = database.open(TransactionalInspirationDAO.class);

        userId = userDAO.insert("memmee_resource_test", "user", "memmee_resource_test", "password", "apiKey", new Date(), new Date());

        //add resources
        addResource(new MemmeeResource(userDAO, txMemmeeDAO, txAttachmentDAO, txInspirationDAO));
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
        txMemmeeDAO.insert(userId, "This is a later memmee", new Date(), new Date(), new Date(), "shareKey", attachmentId, Long.parseLong("1"), inspirationId);

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

    @Ignore
    @Test
    public void testInsertMemmeeNoAttachment() {
        insertTestData();

        Inspiration inspiration = txInspirationDAO.getInspiration(inspirationId);

        Memmee memmee = new Memmee();
        memmee.setText("This is the text of my memory");
        memmee.setDisplayDate(DateUtil.getDate(2009, 2, 17));
        memmee.setInspiration(inspiration);
        memmee.setShareKey("myShareKey");
        memmee.setUserId(userId - 4);

        Memmee myMemmee = client().resource(new MemmeeURLBuilder().setMethodURL("insertmemmee").setApiKeyParam("apiKey").build()).post(Memmee.class, memmee);

        assertThat(myMemmee.getUserId(), is(equalTo(userId)));
        assertThat(myMemmee.getText(), is(equalTo("This is the text of my memory")));
        assertThat(myMemmee.getDisplayDate(), is(equalTo(DateUtil.getDate(2009, 2, 17))));
        assertThat(myMemmee.getInspiration(), is(equalTo(inspiration)));
        assertThat(DateUtil.hourPrecision(myMemmee.getCreationDate()), is(equalTo(DateUtil.hourPrecision(new Date()))));
        assertThat(DateUtil.hourPrecision(myMemmee.getLastUpdateDate()), is(equalTo(DateUtil.hourPrecision(new Date()))));
        assertThat(myMemmee.getAttachment(), is(nullValue()));
        assertThat(myMemmee.getShareKey(), is(equalTo("myShareKey")));
    }

    @Ignore
    @Test
    public void testInsertMemmeeWithAttachment() {
        insertTestData();

        Attachment attachment = txAttachmentDAO.getAttachment(attachmentId);
        Inspiration inspiration = txInspirationDAO.getInspiration(inspirationId);

        Memmee memmee = new Memmee();
        memmee.setText("Text for another memory");
        memmee.setDisplayDate(DateUtil.getDate(2006, 8, 2));
        memmee.setInspiration(inspiration);
        memmee.setShareKey("myShareKey78");
        memmee.setAttachment(attachment);
        memmee.setUserId(userId - 4);

        Memmee myMemmee = client().resource(new MemmeeURLBuilder().setMethodURL("insertmemmee").setApiKeyParam("apiKey").build()).post(Memmee.class, memmee);

        assertThat(myMemmee.getUserId(), is(equalTo(userId)));
        assertThat(myMemmee.getText(), is(equalTo("This is the text of my memory")));
        assertThat(myMemmee.getDisplayDate(), is(equalTo(DateUtil.getDate(2006, 8, 2))));
        assertThat(myMemmee.getInspiration(), is(equalTo(inspiration)));
        assertThat(DateUtil.hourPrecision(myMemmee.getCreationDate()), is(equalTo(DateUtil.hourPrecision(new Date()))));
        assertThat(DateUtil.hourPrecision(myMemmee.getLastUpdateDate()), is(equalTo(DateUtil.hourPrecision(new Date()))));
        assertThat(myMemmee.getShareKey(), is(equalTo("myShareKey78")));
        assertThat(myMemmee.getAttachment(), is(equalTo(attachment)));
    }

    @Test
    public void testInsertWithInvalidApiKey() {
        RuntimeException caseException = null;
        Memmee memmee = new Memmee();

        try {
            client().resource(new MemmeeURLBuilder().setMethodURL("insertmemmee").setApiKeyParam("apiKeyMISS").build()).post(Memmee.class, memmee);
        } catch (RuntimeException e) {
            caseException = e;
        }

        assertThat(caseException, is(not(nullValue())));
    }

    protected Long insertTestData() {
        attachmentId = txAttachmentDAO.insert("this_is_a_file_path.path", "this_is_a_thumb_file_path.path", "image/jpeg");
        inspirationId = txInspirationDAO.insert("this is the inspiration text", new Date(), new Date());
        Date date = DateUtil.getDate(2011, 6, 12);
        return txMemmeeDAO.insert(userId, "This is a memmee", date, date, date, "shareKey", attachmentId, Long.parseLong("1"), inspirationId);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        userDAO.close();
        txMemmeeDAO.close();
        txAttachmentDAO.close();

        ResourceIntegrationTest.tearDownClass();
    }
}

