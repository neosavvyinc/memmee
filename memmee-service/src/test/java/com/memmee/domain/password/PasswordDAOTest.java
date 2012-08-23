package com.memmee.domain.password;


import base.AbstractMemmeeDAOTest;
import com.memmee.domain.password.dao.TransactionalPasswordDAO;
import com.memmee.domain.password.dto.Password;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.Handle;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class PasswordDAOTest extends AbstractMemmeeDAOTest {

    public static final String TABLE_DEFINITION = "CREATE TABLE `password` (\n" +
            "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
            "  `value` varchar(1000) DEFAULT NULL,\n" +
            "  `temp` tinyint(4) DEFAULT NULL,\n" +
            "  PRIMARY KEY (`id`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1";

    @Before
    public void setUp() throws Exception {
        this.database = factory.build(mysqlConfig, "mysql");
        final Handle handle = database.open();
        try {
            handle.createCall("DROP TABLE IF EXISTS password").invoke();
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
    public void testGetPassword() {
        final Handle handle = database.open();
        final TransactionalPasswordDAO dao = database.open(TransactionalPasswordDAO.class);

        try {
            Password password = dao.getPassword(insertTestData(dao));

            assertThat(password, is(not(nullValue())));
            assertThat(password.getValue(), is(equalTo("my_password_53")));
            assertThat(password.isTemp(), is(false));
        } finally {
            dao.close();
            handle.close();
        }
    }

    @Test
    public void testGePasswordInvalid() {
        final Handle handle = database.open();
        final TransactionalPasswordDAO dao = database.open(TransactionalPasswordDAO.class);

        try {
            assertThat(dao.getPassword(insertTestData(dao) + Long.parseLong("1")), is(nullValue()));
        } finally {
            dao.close();
            handle.close();
        }
    }

    @Test
    public void testInsert() {
        final Handle handle = database.open();
        final TransactionalPasswordDAO dao = database.open(TransactionalPasswordDAO.class);

        try {
            Password password = dao.getPassword(dao.insert("newpassword-87-", 1));

            assertThat(password, is(not(nullValue())));
            assertThat(password.getValue(), is(equalTo("newpassword-87-")));
            assertThat(password.isTemp(), is(true));
        } finally {
            dao.close();
            handle.close();
        }
    }

    @Test
    public void testUpdate() {
        final Handle handle = database.open();
        final TransactionalPasswordDAO dao = database.open(TransactionalPasswordDAO.class);

        try {
            Password password = dao.getPassword(insertTestData(dao));

            assertThat(password, is(not(nullValue())));
            assertThat(password.getValue(), is(equalTo("my_password_53")));
            assertThat(password.isTemp(), is(false));

            password = dao.getPassword(Long.parseLong(Integer.toString(dao.update(password.getId(), "0077JEFF", 1))));

            assertThat(password, is(not(nullValue())));
            assertThat(password.getValue(), is(equalTo("0077JEFF")));
            assertThat(password.isTemp(), is(true));
        } finally {
            dao.close();
            handle.close();
        }
    }

    @Test
    public void testUpdateInvalid() {
        final Handle handle = database.open();
        final TransactionalPasswordDAO dao = database.open(TransactionalPasswordDAO.class);

        RuntimeException caseException = null;

        try {
            Password password = dao.getPassword(insertTestData(dao));

            assertThat(password, is(not(nullValue())));
            assertThat(password.getValue(), is(equalTo("my_password_53")));
            assertThat(password.isTemp(), is(false));

            int value = dao.update(password.getId() + Long.parseLong("1"), "0077JEFF", 1);
            assertThat(value, is(equalTo(0)));
        } catch (RuntimeException e) {
            caseException = e;
        } finally {
            dao.close();
            handle.close();
        }
    }

    protected Long insertTestData(TransactionalPasswordDAO dao) {
        return dao.insert("my_password_53", 0);
    }

}
