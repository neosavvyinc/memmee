package com.memmee.domain.memmees;

import base.AbstractMemmeeDAOTest;
import base.BaseMemmeeDAOTest;
import com.memmee.domain.memmees.dao.MemmeeDAO;
import com.memmee.domain.memmees.dao.TransactionalMemmeeDAO;
import com.memmee.domain.memmees.dto.Memmee;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.util.StringMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class MemmeeDAOTest extends BaseMemmeeDAOTest {

    @Test
    public void testGetMemmeeMin() throws Exception {
        final Handle handle = database.open();
        final TransactionalMemmeeDAO dao = database.open(TransactionalMemmeeDAO.class);

        List<Long> ids = insertMemmees(dao, 1);

        try {

            for (Long id : ids) {
                Memmee memmee = dao.getMemmeeMin(id);

                assertThat(memmee, is(not(nullValue())));
                assertThat(memmee.getId(), is(equalTo(id)));
            }

        } finally {
            dao.close();
            handle.close();
        }
    }

    @Test
    public void testGetMemmee() throws Exception {
        final Handle handle = database.open();
        final TransactionalMemmeeDAO dao = database.open(TransactionalMemmeeDAO.class);

        List<Long> ids = insertMemmees(dao, 1);

        try {

            for (Long id : ids) {
                Memmee memmee = dao.getMemmee(id);

                assertThat(memmee, is(not(nullValue())));
                assertThat(memmee.getId(), is(equalTo(id)));
                assertThat(memmee.getAttachment(), is(not(nullValue())));
                assertThat(memmee.getInspiration(), is(not(nullValue())));
            }

        } finally {
            dao.close();
            handle.close();
        }
    }

    @Test
    public void testGetMemmeesByUser() throws Exception {
        final Handle handle = database.open();
        final TransactionalMemmeeDAO dao = database.open(TransactionalMemmeeDAO.class);

        insertMemmees(dao, 7);

        try {

            List<Memmee> memmees = dao.getMemmeesbyUser((long) 7);

            assertThat(memmees, is(not(nullValue())));
            assertThat(memmees.size(), is(equalTo(3)));

            for (Memmee memmee : memmees) {
                assertThat(memmee.getAttachment(), is(not(nullValue())));
                assertThat(memmee.getInspiration(), is(not(nullValue())));
            }

        } finally {
            dao.close();
            handle.close();
        }
    }

    @Test
    public void testSave() throws Exception {

        final Handle handle = database.open();
        final MemmeeDAO dao = database.open(MemmeeDAO.class);

        try {

            dao.insert(new Long(1), "text", new Date(), new Date(), new Date(), "shareKey", new Long(1), new Long(1));
            final String result = handle.createQuery("SELECT COUNT(*) FROM memmee").map(StringMapper.FIRST).first();

            assertThat(Integer.parseInt(result), equalTo(1));

        } finally {
            dao.close();
            handle.close();
        }
    }

    @Test
    public void testUpdate() throws Exception {
        final MemmeeDAO dao = database.open(MemmeeDAO.class);

        try {

            dao.insert(new Long(1), "text", new Date(), new Date(), new Date(), "shareKey", new Long(1), new Long(1));
            final int result = dao.update(new Long(1), "text2", new Date(), new Date(), "shareKey2", new Long(2), new Long(2));

            assertThat(result, equalTo(1));
        } finally {
            dao.close();
        }
    }


    @Test
    public void testDelete() throws Exception {

        final Handle handle = database.open();
        final MemmeeDAO dao = database.open(MemmeeDAO.class);

        try {

            dao.delete(new Long(1));
            final String result = handle.createQuery("SELECT COUNT(*) FROM memmee").map(StringMapper.FIRST).first();

            assertThat(Integer.parseInt(result), equalTo(0));

        } finally {
            dao.close();
            handle.close();
        }

    }

    protected List<Long> insertMemmees(TransactionalMemmeeDAO dao, int userId) {
        List<Long> returnIds = new ArrayList<Long>();

        for (Integer i = 0; i < 3; i++)
            returnIds.add(dao.insert((long) userId,
                    String.format("Text %s", i),
                    new Date(),
                    new Date(),
                    new Date(),
                    String.format("Share Key %s", i),
                    (long) i, (long) i, (long) i));

        return returnIds;
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
}

