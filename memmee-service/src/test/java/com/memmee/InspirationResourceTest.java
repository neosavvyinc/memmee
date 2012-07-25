package com.memmee;

import base.ResourceIntegrationTest;
import com.memmee.builder.MemmeeURLBuilder;
import com.memmee.domain.inspirations.dao.TransactionalInspirationDAO;
import com.memmee.domain.inspirations.dto.Inspiration;
import com.memmee.domain.user.dao.UserDAO;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InspirationResourceTest extends ResourceIntegrationTest {

    private static UserDAO userDAO;
    private static TransactionalInspirationDAO inspirationDAO;

    @Override
    protected void setUpResources() throws Exception {
        super.setUpResources();

        //setup Daos
        inspirationDAO = database.open(TransactionalInspirationDAO.class);
        userDAO = database.open(UserDAO.class);

        //add resources
        addResource(new InspirationResource(userDAO, inspirationDAO));
    }

    @Test
    public void testGetRandomInspiration() {
        insertTestData();

        Inspiration inspiration = client().resource(new MemmeeURLBuilder().
                setBaseURL(InspirationResource.BASE_URL).
                setMethodURL("getrandominspiration").
                setApiKeyParam("apiKey500").
                build()).get(Inspiration.class);

        assertThat(inspiration, is(not(nullValue())));
    }

    protected void insertTestData() {
        insertTestUser();
        insertTestInspirations(inspirationDAO);
    }

    protected List<Long> insertTestInspirations(TransactionalInspirationDAO dao) {
        List<Long> inspirationIds = new ArrayList<Long>();

        for (int i = 0; i < 3; i++)
            inspirationIds.add(dao.insert(String.format("Inspiration %s", i), new Date(), new Date()));

        return inspirationIds;
    }

    protected Long insertTestUser() {
        return userDAO.insert("Adam", "West", "trevorewen@gmail.com", "abc123", "apiKey500", new Date(), new Date());
    }

}
