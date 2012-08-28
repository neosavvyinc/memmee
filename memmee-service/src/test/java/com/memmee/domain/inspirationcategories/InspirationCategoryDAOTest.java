package com.memmee.domain.inspirationcategories;

import base.AbstractMemmeeDAOTest;
import com.memmee.domain.inspirationcategories.dao.TransactionalInspirationCategoryDAO;
import com.memmee.domain.inspirationcategories.domain.InspirationCategory;
import com.memmee.domain.inspirations.dao.TransactionalInspirationDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.Handle;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class InspirationCategoryDAOTest extends AbstractMemmeeDAOTest {

    public static final String DROP_TABLE_STATEMENT = "DROP TABLE IF EXISTS inspirationcategory";
    public static final String TABLE_DEFINITION = "CREATE TABLE `inspirationcategory` (\n" +
            "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
            "  `idx` int(11) NOT NULL,\n" +
            "  `name` varchar(200) NOT NULL,\n" +
            "  PRIMARY KEY (`id`)\n" +
            ") ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3";

    @Before
    public void setUp() throws Exception {
        this.database = factory.build(mysqlConfig, "mysql");
        final Handle handle = database.open();
        try {

            handle.createCall(DROP_TABLE_STATEMENT).invoke();
            handle.createCall(TABLE_DEFINITION).invoke();

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
    public void testGetInspirationCategory() {
        final Handle handle = database.open();
        final TransactionalInspirationCategoryDAO dao = database.open(TransactionalInspirationCategoryDAO.class);

        try {
            Long id = insertTestData(dao);
            InspirationCategory inspirationCategory = dao.getInspirationCategory(id);

            assertThat(inspirationCategory, is(not(nullValue())));
            assertThat(inspirationCategory.getId(), is(equalTo(id)));
            assertThat(inspirationCategory.getName(), is(equalTo("This is my inspiration category")));
            assertThat(inspirationCategory.getIndex(), is(equalTo(Long.parseLong("3"))));

        } finally {
            dao.close();
            handle.close();
        }
    }

    @Test
    public void testGetInspirationCategoryByIndex() {
        final Handle handle = database.open();
        final TransactionalInspirationCategoryDAO dao = database.open(TransactionalInspirationCategoryDAO.class);

        try {
            Long id = insertTestData(dao);

            InspirationCategory inspirationCategory = dao.getInspirationCategoryByIndex(Long.parseLong("3"));

            assertThat(inspirationCategory, is(not(nullValue())));
            assertThat(inspirationCategory.getId(), is(equalTo(id)));
            assertThat(inspirationCategory.getName(), is(equalTo("This is my inspiration category")));
            assertThat(inspirationCategory.getIndex(), is(equalTo(Long.parseLong("3"))));

        } finally {
            dao.close();
            handle.close();
        }
    }

    @Test
    public void testGetHighestInspirationCategory() {
        final Handle handle = database.open();
        final TransactionalInspirationCategoryDAO dao = database.open(TransactionalInspirationCategoryDAO.class);

        try {
            Long firstId = dao.insert(Long.parseLong("4"), "This is the first category");
            Long secondId = dao.insert(Long.parseLong("5"), "This is the second category");

            InspirationCategory inspirationCategory = dao.getHighestInspirationCategory();

            assertThat(inspirationCategory, is(not(nullValue())));
            assertThat(inspirationCategory.getIndex(), is(equalTo(Long.parseLong("5"))));
            assertThat(inspirationCategory.getName(), is(equalTo("This is the second category")));

        } finally {
            dao.close();
            handle.close();
        }
    }

    @Test
    public void testInsert() {
        final Handle handle = database.open();
        final TransactionalInspirationCategoryDAO dao = database.open(TransactionalInspirationCategoryDAO.class);

        try {
            Long id = dao.insert(Long.parseLong("17"), "New Inspiration Category 7");
            InspirationCategory inspirationCategory = dao.getInspirationCategory(id);

            assertThat(inspirationCategory, is(not(nullValue())));
            assertThat(inspirationCategory.getId(), is(equalTo(id)));
            assertThat(inspirationCategory.getName(), is(equalTo("New Inspiration Category 7")));
            assertThat(inspirationCategory.getIndex(), is(equalTo(Long.parseLong("17"))));

        } finally {
            dao.close();
            handle.close();
        }
    }

    protected Long insertTestData(TransactionalInspirationCategoryDAO dao) {
        return dao.insert(Long.parseLong("3"), "This is my inspiration category");
    }
}
