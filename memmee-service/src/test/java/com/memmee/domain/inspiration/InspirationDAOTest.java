package com.memmee.domain.inspiration;

import base.AbstractMemmeeDAOTest;
import com.memmee.domain.inspirations.dao.TransactionalInspirationDAO;
import com.memmee.domain.inspirations.dto.Inspiration;
import org.junit.*;
import org.skife.jdbi.v2.Handle;

import java.util.*;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class InspirationDAOTest extends AbstractMemmeeDAOTest {

    @Before
    public void setUp() throws Exception {
        this.database = factory.build(mysqlConfig, "mysql");
        final Handle handle = database.open();
        try {

            handle.createCall("DROP TABLE IF EXISTS inspiration").invoke();

            handle.createCall(
                    "CREATE TABLE `inspiration` (\n" +
                            "`id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                            "`text` varchar(1000) NOT NULL,\n" +
                            "`creationDate` datetime NOT NULL,\n" +
                            "`lastUpdateDate` datetime NOT NULL,\n" +
                            "PRIMARY KEY (`id`)\n" +
                            ") ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1"
            ).invoke();

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

        List<Long> ids = insertTestInspirations(dao);

        try {
            for (Long id : ids) {
                Inspiration inspiration = dao.getInspiration(id);

                assertThat(inspiration, is(not(nullValue())));
                assertThat(inspiration.getId(), is(equalTo(id)));
            }
        } finally {
            dao.close();
            handle.close();
        }
    }

    @Test
    public void testGetRandomInspiration() throws Exception {
        final Handle handle = database.open();
        final TransactionalInspirationDAO dao = database.open(TransactionalInspirationDAO.class);

        insertTestInspirations(dao);

        try {
            Set<Inspiration> uniqueItemSet = new HashSet<Inspiration>();

            for (int i = 0; i < 30; i++)
                uniqueItemSet.add(dao.getRandomInspiration());

            assertThat(uniqueItemSet.size(), is(greaterThan(1)));

        } finally {
            dao.close();
            handle.close();
        }
    }

    @Test
    public void testGetRandomInspirationWithExcludeId() throws Exception {
        final Handle handle = database.open();
        final TransactionalInspirationDAO dao = database.open(TransactionalInspirationDAO.class);

        List<Long> ids = insertTestInspirations(dao);

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
            handle.close();
        }
    }

    @Test
    public void testInsert() throws Exception {
        final Handle handle = database.open();
        final TransactionalInspirationDAO dao = database.open(TransactionalInspirationDAO.class);

        try {
            Long id = dao.insert("This is my text", new Date(), new Date());

            Inspiration inspiration = dao.getInspiration(id);

            assertThat(inspiration.getText(), is(equalTo("This is my text")));
            assertThat(inspiration.getCreationDate(), is(not(nullValue())));
            assertThat(inspiration.getLastUpdateDate(), is(not(nullValue())));

        } finally {
            dao.close();
            handle.close();
        }
    }

    protected List<Long> insertTestInspirations(TransactionalInspirationDAO dao) {
        List<Long> inspirationIds = new ArrayList<Long>();

        for (int i = 0; i < 3; i++)
            inspirationIds.add(dao.insert(String.format("Inspiration %s", i), new Date(), new Date()));

        return inspirationIds;
    }
}
