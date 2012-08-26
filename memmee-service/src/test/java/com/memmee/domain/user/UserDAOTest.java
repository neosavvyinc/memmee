package com.memmee.domain.user;

import base.AbstractMemmeeDAOTest;
import com.memmee.domain.password.PasswordDAOTest;
import com.memmee.domain.password.dao.TransactionalPasswordDAO;
import com.memmee.domain.user.dao.TransactionalUserDAO;
import com.memmee.domain.user.dto.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.util.StringMapper;
import sun.util.resources.LocaleNames_ga;

import java.sql.SQLException;
import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class UserDAOTest extends AbstractMemmeeDAOTest {
    public static final String DROP_TABLE_STATEMENT = "DROP TABLE IF EXISTS user";
    public static final String TABLE_DEFINITION = "CREATE TABLE `user` (\n" +
            "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
            "  `firstName` varchar(1024) DEFAULT NULL,\n" +
            "  `email` varchar(4096) NOT NULL,\n" +
            "  `passwordId` int(11) DEFAULT NULL,\n" +
            "  `apiKey` varchar(1024) DEFAULT NULL,\n" +
            "  `apiDate` datetime DEFAULT NULL,\n" +
            "  `creationDate` datetime NOT NULL,\n" +
            "  `loginCount` int(11) DEFAULT NULL,\n" +
            "  PRIMARY KEY (`id`)\n" +
            ") ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3";

    @Before
    public void setUp() throws Exception {
        this.database = factory.build(mysqlConfig, "mysql");
        final Handle handle = database.open();
        try {

            handle.createCall(UserDAOTest.DROP_TABLE_STATEMENT).invoke();
            handle.createCall("DROP TABLE IF EXISTS password").invoke();

            handle.createCall(UserDAOTest.TABLE_DEFINITION).invoke();
            handle.createCall(PasswordDAOTest.TABLE_DEFINITION).invoke();

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
        final TransactionalUserDAO dao = database.open(TransactionalUserDAO.class);
        final TransactionalPasswordDAO passwordDAO = database.open(TransactionalPasswordDAO.class);

        try {

            insertTestData(dao, passwordDAO);
            final String result = handle.createQuery("SELECT COUNT(*) FROM user").map(StringMapper.FIRST).first();

            assertThat(Integer.parseInt(result), equalTo(1));

        } finally {
            dao.close();
            handle.close();
        }
    }

    @Test
    public void testRead() throws Exception {
        final Handle handle = database.open();
        final TransactionalUserDAO dao = database.open(TransactionalUserDAO.class);
        final TransactionalPasswordDAO passwordDAO = database.open(TransactionalPasswordDAO.class);

        try {

            Long id = insertTestData(dao, passwordDAO);
            final User user = dao.getUser(id);
            assertThat(user.getId(), equalTo(id));

        } finally {
            dao.close();
            handle.close();
        }

    }


    @Test
    public void testUpdate() throws Exception {
        final TransactionalUserDAO dao = database.open(TransactionalUserDAO.class);

        try {

            Long id = dao.insert("Adam", "aparrish@neosavvy.com", Long.parseLong("2"), "apiKey", new Date(), new Date(), Long.parseLong("1"));
            final int result = dao.update(id, "Luke", "lukelappin@gmail.com", Long.parseLong("1"), "apiKey", new Date(), Long.parseLong("2"));

            assertThat(result, equalTo(1));
        } finally {
            dao.close();
        }
    }


    @Test
    public void testDelete() throws Exception {

        final Handle handle = database.open();
        final TransactionalUserDAO dao = database.open(TransactionalUserDAO.class);

        try {

            dao.delete(new Long(1));
            final String result = handle.createQuery("SELECT COUNT(*) FROM user").map(StringMapper.FIRST).first();

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

    @Test
    public void testGetUserByEmail() throws Exception {
        final Handle handle = database.open();
        final TransactionalUserDAO dao = database.open(TransactionalUserDAO.class);
        final TransactionalPasswordDAO passwordDAO = database.open(TransactionalPasswordDAO.class);
        insertTestData(dao, passwordDAO);

        User user = dao.getUserByEmail("aparrish@neosavvy.com");

        assertThat(user, is(not(nullValue())));
        assertThat(user.getFirstName(), is(equalTo("Adam")));
        assertThat(user.getEmail(), is(equalTo("aparrish@neosavvy.com")));
        assertThat(user.getPassword().getValue(), is(equalTo("password")));
        assertThat(user.getPassword().isTemp(), is(false));
        assertThat(user.getApiKey(), is(equalTo("apiKey")));
    }

    @Test
    public void testGetUserByEmailNotFound() throws Exception {
        final Handle handle = database.open();
        final TransactionalUserDAO dao = database.open(TransactionalUserDAO.class);
        final TransactionalPasswordDAO passwordDAO = database.open(TransactionalPasswordDAO.class);
        insertTestData(dao, passwordDAO);

        User user = dao.getUserByEmail("aparrish@neosavvy_5.com");

        assertThat(user, is(nullValue()));
    }

    @Test
    public void testLoginUser() throws Exception {
        final Handle handle = database.open();
        final TransactionalUserDAO dao = database.open(TransactionalUserDAO.class);
        final TransactionalPasswordDAO passwordDAO = database.open(TransactionalPasswordDAO.class);
        insertTestData(dao, passwordDAO);

        User user = dao.loginUser("aparrish@neosavvy.com", "password");

        assertThat(user, is(not(nullValue())));
        assertThat(user.getFirstName(), is(equalTo("Adam")));
        assertThat(user.getEmail(), is(equalTo("aparrish@neosavvy.com")));
        assertThat(user.getPassword(), is(not(nullValue())));
        assertThat(user.getPassword().getValue(), is(equalTo("password")));
        assertThat(user.getPassword().isTemp(), is(false));
        assertThat(user.getApiKey(), is(equalTo("apiKey")));
    }

    @Test
    public void testLoginUserInvalid() throws Exception {
        final Handle handle = database.open();
        final TransactionalUserDAO dao = database.open(TransactionalUserDAO.class);
        final TransactionalPasswordDAO passwordDAO = database.open(TransactionalPasswordDAO.class);
        insertTestData(dao, passwordDAO);

        User user = dao.loginUser("aparrish@neosavvy.com", "passw0rd");

        assertThat(user, is(nullValue()));
    }

    @Test
    public void testGetUserCount() throws Exception {
        final Handle handle = database.open();
        final TransactionalUserDAO dao = database.open(TransactionalUserDAO.class);
        final TransactionalPasswordDAO passwordDAO = database.open(TransactionalPasswordDAO.class);
        insertTestData(dao, passwordDAO);

        assertThat(dao.getUserCount("aparrish@neosavvy.com"), is(equalTo(1)));
    }

    @Test
    public void testIncrementLoginCount() throws Exception {
        final Handle handle = database.open();
        final TransactionalUserDAO dao = database.open(TransactionalUserDAO.class);
        final TransactionalPasswordDAO passwordDAO = database.open(TransactionalPasswordDAO.class);
        Long id = insertTestData(dao, passwordDAO);

        dao.incrementLoginCount(id);
        assertThat(dao.getUser(id).getLoginCount(), is(equalTo(Long.parseLong("2"))));
        dao.incrementLoginCount(id);
        assertThat(dao.getUser(id).getLoginCount(), is(equalTo(Long.parseLong("3"))));
        dao.incrementLoginCount(id);
        assertThat(dao.getUser(id).getLoginCount(), is(equalTo(Long.parseLong("4"))));
        assertThat(dao.getUser(id).getLoginCount(), is(equalTo(Long.parseLong("4"))));
    }

    protected Long insertTestData(TransactionalUserDAO dao, TransactionalPasswordDAO passwordDAO) {
        Long passwordId = passwordDAO.insert("password", 0);
        return dao.insert("Adam", "aparrish@neosavvy.com", passwordId, "apiKey", new Date(), new Date(), Long.parseLong("1"));
    }
}

