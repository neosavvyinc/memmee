package com.memmee.theme;

import com.memmee.theme.dao.TransactionalThemeDAO;
import com.memmee.theme.dto.Theme;
import base.AbstractMemmeeDAOTest;
import com.yammer.dropwizard.db.Database;
import org.skife.jdbi.v2.Handle;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.util.StringMapper;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

public class ThemeDAOTest extends AbstractMemmeeDAOTest {
    public static final String DROP_TABLE_STATEMENT = "DROP TABLE IF EXISTS theme";
    public static final String TABLE_DEFINITION = "CREATE TABLE `theme` (\n" +
            "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
            "  `name` varchar(100) DEFAULT NULL,\n" +
            "  `listName` varchar(100) DEFAULT NULL,\n" +
            "  `stylePath` varchar(1024) DEFAULT NULL,\n" +
            "  PRIMARY KEY (`id`)\n" +
            ") ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2";

    @Before
    public void setUp() throws Exception {
        this.database = factory.build(mysqlConfig, "mysql");
        final Handle handle = database.open();
        try {

            handle.createCall(ThemeDAOTest.DROP_TABLE_STATEMENT).invoke();
            handle.createCall(ThemeDAOTest.TABLE_DEFINITION).invoke();

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
    public void testGetThemeByName() throws Exception {
        final Handle handle = database.open();
        final TransactionalThemeDAO dao = database.open(TransactionalThemeDAO.class);

        try {
            Long id = insertTestTheme(dao, "Best Cheeto Ever!");

            Theme theme = dao.getThemeByName("Best Cheeto Ever!");

            assertThat(theme, is(not(nullValue())));
            assertThat(theme.getName(), is(equalTo("Best Cheeto Ever!")));
            assertThat(theme.getListName(), is(equalTo("List Best Cheeto Ever!")));
            assertThat(theme.getId(), is(equalTo(id)));
            assertThat(theme.getStylePath(), is(not(nullValue())));
        } finally {
            dao.close();
            handle.close();
        }
    }

    @Test
    public void testSave() throws Exception {

        final Handle handle = database.open();
        final TransactionalThemeDAO dao = database.open(TransactionalThemeDAO.class);

        try {

            dao.insert("name", "listName", "stylePath");
            final String result = handle.createQuery("SELECT COUNT(*) FROM theme").map(StringMapper.FIRST).first();

            assertThat(Integer.parseInt(result), equalTo(1));

        } finally {
            dao.close();
            handle.close();
        }
    }


    @Test
    public void testRead() throws Exception {
        final Handle handle = database.open();
        final TransactionalThemeDAO dao = database.open(TransactionalThemeDAO.class);

        try {

            Long id = dao.insert("name", "listName", "stylePath");
            final Theme theme = dao.getTheme(id);


            assertThat(theme.getId(), equalTo(id));

        } finally {
            dao.close();
            handle.close();
        }

    }


    @Test
    public void testUpdate() throws Exception {
        final TransactionalThemeDAO dao = database.open(TransactionalThemeDAO.class);

        try {

            Long id = dao.insert("name", "listName", "stylePath");

            Theme theme = dao.getTheme(id);
            assertThat(theme.getName(), is(equalTo("name")));
            assertThat(theme.getListName(), is(equalTo("listName")));
            assertThat(theme.getStylePath(), is(equalTo("stylePath")));

            final int result = dao.update(id, "name2", "listName2", "stylePath2");
            theme = dao.getTheme(id);
            assertThat(result, equalTo(1));
            assertThat(theme.getName(), is(equalTo("name2")));
            assertThat(theme.getListName(), is(equalTo("listName2")));
            assertThat(theme.getStylePath(), is(equalTo("stylePath2")));

        } finally {
            dao.close();
        }
    }


    @Test
    public void testDelete() throws Exception {

        final Handle handle = database.open();
        final TransactionalThemeDAO dao = database.open(TransactionalThemeDAO.class);

        try {

            dao.delete(new Long(1));
            final String result = handle.createQuery("SELECT COUNT(*) FROM theme").map(StringMapper.FIRST).first();

            assertThat(Integer.parseInt(result), equalTo(0));

        } finally {
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
        mysqlConfig.setValidationQuery("SELECT 1 FROM memmeetest.attachment");
        try {
            database.ping();
        } catch (SQLException e) {
            e.printStackTrace();
            fail("shouldn't have thrown an exception but did");
        }
    }

    protected Long insertTestTheme(TransactionalThemeDAO dao, String name) {
        return dao.insert(name, String.format("List %s", name), "Style Path");
    }
}

