package com.memmee.domain.memmees;

import base.BaseMemmeeDAOTest;
import com.memmee.domain.memmees.dao.TransactionalMemmeeDAO;
import com.memmee.domain.memmees.dto.Memmee;

import com.memmee.theme.dao.TransactionalThemeDAO;
import com.memmee.theme.dto.Theme;
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
        final TransactionalThemeDAO themeDao = database.open(TransactionalThemeDAO.class);

        List<Long> ids = insertMemmees(dao, themeDao, 1);

        try {

            for (Long id : ids) {
                Memmee memmee = dao.getMemmeeMin(id);

                assertThat(memmee, is(not(nullValue())));
                assertThat(memmee.getId(), is(equalTo(id)));
                assertThat(memmee.getTheme(), is(nullValue()));
                assertThat(memmee.getInspiration(), is(nullValue()));
                assertThat(memmee.getAttachment(), is(nullValue()));
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
        final TransactionalThemeDAO themeDao = database.open(TransactionalThemeDAO.class);

        List<Long> ids = insertMemmees(dao, themeDao, 1);

        try {

            for (Long id : ids) {
                Memmee memmee = dao.getMemmee(id);

                assertThat(memmee, is(not(nullValue())));
                assertThat(memmee.getId(), is(equalTo(id)));
                assertThat(memmee.getAttachment(), is(not(nullValue())));
                assertThat(memmee.getInspiration(), is(not(nullValue())));
                themeTests(memmee.getTheme());
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
        final TransactionalThemeDAO themeDao = database.open(TransactionalThemeDAO.class);

        insertMemmees(dao, themeDao, 7);

        try {

            List<Memmee> memmees = dao.getMemmeesbyUser((long) 7);

            assertThat(memmees, is(not(nullValue())));
            assertThat(memmees.size(), is(equalTo(3)));

            for (Memmee memmee : memmees) {
                assertThat(memmee.getAttachment(), is(not(nullValue())));
                assertThat(memmee.getInspiration(), is(not(nullValue())));
                themeTests(memmee.getTheme());
            }

        } finally {
            dao.close();
            handle.close();
        }
    }

    @Test
    public void testSave() throws Exception {

        final Handle handle = database.open();
        final TransactionalMemmeeDAO dao = database.open(TransactionalMemmeeDAO.class);

        try {

            dao.insert(new Long(1), "text", new Date(), new Date(), new Date(), "shareKey", new Long(1), new Long(1), new Long(1));
            final String result = handle.createQuery("SELECT COUNT(*) FROM memmee").map(StringMapper.FIRST).first();

            assertThat(Integer.parseInt(result), equalTo(1));

        } finally {
            dao.close();
            handle.close();
        }
    }

    @Test
    public void testUpdate() throws Exception {
        final TransactionalMemmeeDAO dao = database.open(TransactionalMemmeeDAO.class);

        try {

            Long id = dao.insert(new Long(1), "text", new Date(), new Date(), new Date(), "shareKey", new Long(1), new Long(1), new Long(1));
            dao.update(id, "text2", new Date(), new Date(), "shareKey2", new Long(2), new Long(2));

            Memmee myMemmee = dao.getMemmee(id);

            assertThat(myMemmee, is(not(nullValue())));
            assertThat(myMemmee.getText(), is(equalTo("text2")));
            assertThat(myMemmee.getShareKey(), is(equalTo("shareKey2")));
        } finally {
            dao.close();
        }
    }

    @Test
    public void testUpdateShareKey() throws Exception {
        final TransactionalMemmeeDAO dao = database.open(TransactionalMemmeeDAO.class);

        try {

            Long id = dao.insert(new Long(1), "text", new Date(), new Date(), new Date(), "shareKey", new Long(1), new Long(1), new Long(1));
            dao.updateShareKey(id, "this is a sharekey");

            assertThat(dao.getMemmee(id).getShareKey(), is(equalTo("this is a sharekey")));
        } finally {
            dao.close();
        }
    }

    @Test
    public void testUpdateShortenedUrl() throws Exception {
        final TransactionalMemmeeDAO dao = database.open(TransactionalMemmeeDAO.class);

        try {
            Long id = dao.insert(new Long(1), "text", new Date(), new Date(), new Date(), "shareKey", new Long(1), new Long(1), new Long(1));
            dao.updateShortenedUrl(id, "http://www.shortened.com", "http://www.shortened.com");

            assertThat(dao.getMemmee(id).getShortenedUrl(), is(equalTo("http://www.shortened.com")));
        } finally {
            dao.close();
        }
    }


    @Test
    public void testDelete() throws Exception {

        final Handle handle = database.open();
        final TransactionalMemmeeDAO dao = database.open(TransactionalMemmeeDAO.class);

        try {

            dao.delete(new Long(1));
            final String result = handle.createQuery("SELECT COUNT(*) FROM memmee").map(StringMapper.FIRST).first();

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

    protected List<Long> insertMemmees(TransactionalMemmeeDAO dao, TransactionalThemeDAO themeDao, int userId) {
        List<Long> returnIds = new ArrayList<Long>();

        for (Integer i = 0; i < 3; i++) {
            Long themeId = themeDao.insert(String.format("Theme %s", i), String.format("Theme list %s", i), String.format("Style path %s", i));
            returnIds.add(dao.insert((long) userId,
                    String.format("Text %s", i),
                    new Date(),
                    new Date(),
                    new Date(),
                    String.format("Share Key %s", i),
                    (long) i, themeId, (long) i));
        }

        return returnIds;
    }

    protected void themeTests(Theme theme) {
        assertThat(theme, is(not(nullValue())));
        assertThat(theme.getName(), is(not(nullValue())));
        assertThat(theme.getStylePath(), is(not(nullValue())));
    }
}

