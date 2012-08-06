package com.memmee.domain.inspiration;

import base.AbstractMemmeeDAOTest;
import com.memmee.domain.inspirationcategories.InspirationCategoryDAOTest;
import com.memmee.domain.inspirationcategories.dao.TransactionalInspirationCategoryDAO;
import com.memmee.domain.inspirations.dao.TransactionalInspirationDAO;
import com.memmee.domain.inspirations.dto.Inspiration;
import org.junit.*;
import org.skife.jdbi.v2.Handle;

import java.util.*;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class InspirationDAOTest extends AbstractMemmeeDAOTest {

    public static final String DROP_TABLE_STATEMENT = "DROP TABLE IF EXISTS inspiration";
    public static final String TABLE_DEFINITION = "CREATE TABLE `inspiration` (\n" +
            "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
            "  `inspirationCategoryId` int(11) NOT NULL,\n" +
            "  `text` varchar(1000) NOT NULL,\n" +
            "  `inspirationCategoryIndex` int(11) DEFAULT NULL,\n" +
            "  `creationDate` datetime DEFAULT NULL,\n" +
            "  `lastUpdateDate` datetime DEFAULT NULL,\n" +
            "  PRIMARY KEY (`id`)\n" +
            ") ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6";

    @Before
    public void setUp() throws Exception {
        this.database = factory.build(mysqlConfig, "mysql");
        final Handle handle = database.open();
        try {

            handle.createCall(DROP_TABLE_STATEMENT).invoke();
            handle.createCall(InspirationCategoryDAOTest.DROP_TABLE_STATEMENT).invoke();

            handle.createCall(TABLE_DEFINITION).invoke();
            handle.createCall(InspirationCategoryDAOTest.TABLE_DEFINITION).invoke();

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
    public void testGetInspiration() throws Exception {
        final Handle handle = database.open();
        final TransactionalInspirationDAO dao = database.open(TransactionalInspirationDAO.class);
        final TransactionalInspirationCategoryDAO inspirationCategoryDAO = database.open(TransactionalInspirationCategoryDAO.class);

        List<Long> ids = insertTestInspirations(dao, inspirationCategoryDAO);

        try {
            for (Long id : ids) {
                Inspiration inspiration = dao.getInspiration(id);

                assertThat(inspiration, is(not(nullValue())));
                assertThat(inspiration.getId(), is(equalTo(id)));
            }
        } finally {
            dao.close();
            inspirationCategoryDAO.close();
            handle.close();
        }
    }

    @Test
    public void testGetRandomInspiration() throws Exception {
        final Handle handle = database.open();
        final TransactionalInspirationDAO dao = database.open(TransactionalInspirationDAO.class);
        final TransactionalInspirationCategoryDAO inspirationCategoryDAO = database.open(TransactionalInspirationCategoryDAO.class);

        insertTestInspirations(dao, inspirationCategoryDAO);

        try {
            Set<Inspiration> uniqueItemSet = new HashSet<Inspiration>();

            for (int i = 0; i < 30; i++)
                uniqueItemSet.add(dao.getRandomInspiration());

            assertThat(uniqueItemSet.size(), is(greaterThan(1)));

        } finally {
            dao.close();
            inspirationCategoryDAO.close();
            handle.close();
        }
    }

    @Test
    public void testGetRandomInspirationWithExcludeId() throws Exception {
        final Handle handle = database.open();
        final TransactionalInspirationDAO dao = database.open(TransactionalInspirationDAO.class);
        final TransactionalInspirationCategoryDAO inspirationCategoryDAO = database.open(TransactionalInspirationCategoryDAO.class);

        List<Long> ids = insertTestInspirations(dao, inspirationCategoryDAO);

        try {
            Set<Inspiration> uniqueItemSet = new HashSet<Inspiration>();
            Long excludedId = ids.get(0);

            for (int i = 0; i < 30; i++)
                uniqueItemSet.add(dao.getRandomInspiration(excludedId));

            assertThat(uniqueItemSet.size(), is(greaterThan(1)));

            for (Inspiration inspiration : uniqueItemSet)
                assertThat(inspiration.getId(), is(not(equalTo(excludedId))));

        } finally {
            dao.close();
            inspirationCategoryDAO.close();
            handle.close();
        }
    }

    @Test
    public void testInsert() throws Exception {
        final Handle handle = database.open();
        final TransactionalInspirationDAO dao = database.open(TransactionalInspirationDAO.class);
        final TransactionalInspirationCategoryDAO inspirationCategoryDAO = database.open(TransactionalInspirationCategoryDAO.class);

        try {
            Long categoryId = insertTestCategory(inspirationCategoryDAO);
            Long id = dao.insert("This is my text", categoryId, Long.parseLong("18"), new Date(), new Date());

            Inspiration inspiration = dao.getInspiration(id);

            assertThat(inspiration.getId(), is(equalTo(id)));
            assertThat(inspiration.getText(), is(equalTo("This is my text")));
            assertThat(inspiration.getCreationDate(), is(not(nullValue())));
            assertThat(inspiration.getLastUpdateDate(), is(not(nullValue())));

            assertThat(inspiration.getInspirationCategory(), is(not(nullValue())));
            assertThat(inspiration.getInspirationCategory().getId(), is(equalTo(categoryId)));
            assertThat(inspiration.getInspirationCategory().getName(), is(equalTo("My test category")));

        } finally {
            dao.close();
            inspirationCategoryDAO.close();
            handle.close();
        }
    }

    @Test
    public void testGetInspirationsForInspirationCategory() {
        final Handle handle = database.open();
        final TransactionalInspirationDAO dao = database.open(TransactionalInspirationDAO.class);
        final TransactionalInspirationCategoryDAO inspirationCategoryDAO = database.open(TransactionalInspirationCategoryDAO.class);

        try{
            Long categoryId = insertTestCategory(inspirationCategoryDAO);
            Long otherCategoryId = insertTestCategory(inspirationCategoryDAO);
            dao.insert("Cookies are awesome", categoryId, Long.parseLong("1"), new Date(), new Date());
            dao.insert("Wookies are awesome", categoryId, Long.parseLong("7"), new Date(), new Date());
            dao.insert("Bookies are awesome 57", categoryId, Long.parseLong("3"), new Date(), new Date());

            List<Inspiration> inspirations = dao.getInspirationsForInspirationCategory(categoryId);

            assertThat(inspirations, is(not(nullValue())));
            assertThat(inspirations.size(), is(equalTo(3)));
            assertThat(inspirations.get(0).getText(), is(equalTo("Cookies are awesome")));
            assertThat(inspirations.get(1).getText(), is(equalTo("Bookies are awesome 57")));
            assertThat(inspirations.get(2).getText(), is(equalTo("Wookies are awesome")));

            List<Inspiration> otherInspirations = dao.getInspirationsForInspirationCategory(otherCategoryId);

            assertThat(otherInspirations, is(not(nullValue())));
            assertThat(otherInspirations.size(), is(equalTo(0)));
        } finally {
            dao.close();
            inspirationCategoryDAO.close();
            handle.close();
        }

    }

    protected Long insertTestCategory(TransactionalInspirationCategoryDAO dao) {
        return dao.insert("My test category");
    }

    protected List<Long> insertTestInspirations(TransactionalInspirationDAO dao, TransactionalInspirationCategoryDAO inspirationCategoryDAO) {
        List<Long> inspirationIds = new ArrayList<Long>();

        for (int k = 0; k < 3; k++) {
            Long categoryId = inspirationCategoryDAO.insert(String.format("Inspiration Category %s", k));
            for (int i = 0; i < 3; i++)
                inspirationIds.add(dao.insert(String.format("Inspiration %s, Category %s", i, k), categoryId, Long.parseLong(Integer.toString(i)), new Date(), new Date()));
        }

        return inspirationIds;
    }
}
