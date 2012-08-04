package com.memmee;

import base.ResourceIntegrationTest;
import com.memmee.auth.PasswordGenerator;
import com.memmee.auth.PasswordGeneratorImpl;
import com.memmee.builder.MemmeeURLBuilder;
import com.memmee.domain.inspirations.dao.TransactionalInspirationDAO;
import com.memmee.domain.inspirations.dto.Inspiration;
import com.memmee.domain.password.dao.TransactionalPasswordDAO;
import com.memmee.domain.user.dao.TransactionalUserDAO;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InspirationResourceTest extends ResourceIntegrationTest {

    private static TransactionalUserDAO userDAO;
    private static TransactionalPasswordDAO passwordDAO;
    private static TransactionalInspirationDAO inspirationDAO;
    private static PasswordGenerator passwordGenerator;

    @Override
    protected void setUpResources() throws Exception {
        super.setUpResources();

        //setup Daos
        inspirationDAO = database.open(TransactionalInspirationDAO.class);
        passwordDAO = database.open(TransactionalPasswordDAO.class);
        userDAO = database.open(TransactionalUserDAO.class);
        passwordGenerator = new PasswordGeneratorImpl();

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
        Long passwordId = passwordDAO.insert(passwordGenerator.encrypt("abc123"), 0);
        return userDAO.insert("Adam", "trevorewen@gmail.com", passwordId, "apiKey500", new Date(), new Date());
    }

}
