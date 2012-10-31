package com.memmee.reporting;

import base.AbstractMemmeeDAOTest;
import base.BaseMemmeeDAOTest;
import com.memmee.domain.memmees.dao.TransactionalMemmeeDAO;
import com.memmee.domain.memmees.dto.Memmee;
import com.memmee.domain.user.UserDAOTest;
import com.memmee.domain.user.dto.User;
import com.memmee.reporting.dao.MemmeeReportingDAO;
import com.memmee.theme.dao.TransactionalThemeDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.Handle;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: waparrish
 * Date: 10/31/12
 * Time: 2:07 PM
 */
public class MemmeeReportingDAOTest extends AbstractMemmeeDAOTest {

    @Before
    public void setUp() throws Exception {
        this.database = factory.build(mysqlConfig, "mysql");
        final Handle handle = database.open();
        try {

            handle.createCall(BaseMemmeeDAOTest.DROP_TABLE_STATEMENT).invoke();
            handle.createCall(BaseMemmeeDAOTest.TABLE_DEFINITION).invoke();
            handle.createCall(UserDAOTest.DROP_TABLE_STATEMENT).invoke();
            handle.createCall(UserDAOTest.TABLE_DEFINITION).invoke();

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
    public void getUsersWithCompletedProfiles() {
        final Handle handle = database.open();
        final MemmeeReportingDAO dao = database.open(MemmeeReportingDAO.class);

        try {

            dao.getUsersWithCompletedProfiles();


        } finally {
            dao.close();
            handle.close();
        }
    }

    @Test
    public void getUsersWithNoCompletedProfiles() {
        final Handle handle = database.open();
        final MemmeeReportingDAO dao = database.open(MemmeeReportingDAO.class);

        try {

            dao.getUsersWithNoCompletedProfiles();


        } finally {
            dao.close();
            handle.close();
        }
    }

    @Test
    public void getUsers() {
        final Handle handle = database.open();
        final MemmeeReportingDAO dao = database.open(MemmeeReportingDAO.class);

        try {

            dao.getUsers();


        } finally {
            dao.close();
            handle.close();
        }
    }

    @Test
    public void getUsersWithMemmeeCount() {
        final Handle handle = database.open();
        final MemmeeReportingDAO dao = database.open(MemmeeReportingDAO.class);

        try {

            dao.getUsersWithMemmeeCount();


        } finally {
            dao.close();
            handle.close();
        }
    }

    @Test
    public void getUsersWithNoMemmees() {
        final Handle handle = database.open();
        final MemmeeReportingDAO dao = database.open(MemmeeReportingDAO.class);

        try {

            dao.getUsersWithNoMemmees();


        } finally {
            dao.close();
            handle.close();
        }
    }
}
