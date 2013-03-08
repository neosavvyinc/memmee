package com.memmee;

import base.ResourceIntegrationTest;
import com.memmee.auth.PasswordGenerator;
import com.memmee.auth.PasswordGeneratorImpl;
import com.memmee.builder.MemmeeURLBuilder;
import com.memmee.domain.password.dao.TransactionalPasswordDAO;
import com.memmee.domain.password.dto.Password;
import com.memmee.error.UserResourceException;
import com.memmee.domain.user.dao.TransactionalUserDAO;
import com.memmee.domain.user.dto.User;
import com.memmee.util.MemmeeMailSender;
import com.memmee.util.MemmeeMailSenderImpl;
import com.sun.jersey.api.client.ClientResponse;

import org.junit.Test;

import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;

public class UserResourceTest extends ResourceIntegrationTest {

    private static TransactionalUserDAO userDAO;
    private static TransactionalPasswordDAO passwordDAO;
    private static MemmeeMailSender memmeeMailSender;
    private static PasswordGenerator passwordGenerator;

    @Override
    protected void setUpResources() throws Exception {
        super.setUpResources();

        //setup Daos
        userDAO = database.open(TransactionalUserDAO.class);
        passwordDAO = database.open(TransactionalPasswordDAO.class);
        memmeeMailSender = new MemmeeMailSenderImpl();
        passwordGenerator = new PasswordGeneratorImpl();

        //add resources
        MemmeeUrlConfiguration mockMemmeeConfiguration = new MemmeeUrlConfiguration();
        mockMemmeeConfiguration.setActiveEmailAddress("test-cases@nowhere.com");
        memmeeMailSender.setUrlConfiguration(mockMemmeeConfiguration);
        addResource(new UserResource(userDAO, passwordDAO, passwordGenerator, memmeeMailSender, mockMemmeeConfiguration));
    }

    @Test
    public void testLoginUserByApiKey() {
        insertTestData();

        User user = client().resource(new MemmeeURLBuilder().
                setBaseURL(UserResource.BASE_URL).
                setMethodURL("user/login").
                setApiKeyParam("apiKey500").
                build()).get(User.class);

        assertThat(user, is(not(nullValue())));
        assertThat(user.getFirstName(), is(equalTo("Adam")));
        assertThat(user.getLoginCount(), is(equalTo(Long.parseLong("2"))));
    }

    @Test
    public void testLoginUser() {
//        insertTestData();

        User testUser = new User();
        testUser.setEmail("waparrish@gmail.com");
        testUser.setPassword(new Password("testPass"));
        testUser.setFirstName("Adam");

        //Add the user as if they are signing up
        testUser = client().resource(new MemmeeURLBuilder()
                .setBaseURL(UserResource.BASE_URL)
                .setMethodURL("user")
                .build())
                .type(MediaType.APPLICATION_JSON)
                .post(User.class, testUser);



        //Attempt to sign them in
        User user = client().resource(new MemmeeURLBuilder().
                setBaseURL(UserResource.BASE_URL).
                setMethodURL("user/login").
                build())
                .type(MediaType.APPLICATION_JSON)
                .post(User.class, new User("Adam", "waparrish@gmail.com", new Password("testPass")));

        assertThat(user, is(not(nullValue())));
        assertThat(user.getPassword(), is(not(nullValue())));
        assertThat(user.getPassword().getValue(), is(nullValue()));
        assertThat(user.getLoginCount(), is(equalTo(Long.parseLong("1"))));
    }

    @Test
    public void testLoginUnauthorizedUser() {
        insertTestData();

        RuntimeException caseException = null;

        try {
            User user = client().resource(new MemmeeURLBuilder().
                    setBaseURL(UserResource.BASE_URL).
                    setMethodURL("user/login").
                    build()).post(User.class, new User("Adam", "trevorewen@gmail.com", new Password("abc124")));
        } catch (RuntimeException e) {
            caseException = e;
        }

        assertThat(caseException, is(not(nullValue())));
    }

    @Test
    public void testAdd() {
        User user = new User();
        user.setEmail("newemail@newemail.com");
        user.setFirstName("Adam");
        user.setPassword(new Password("testPassword"));

        client().resource(new MemmeeURLBuilder().setBaseURL(UserResource.BASE_URL).setMethodURL("user").build())
                .type(MediaType.APPLICATION_JSON)
                .post(user);
        List<User> users = userDAO.findAll();

        assertThat(users.size(), is(equalTo(1)));
        assertThat(users.get(0).getEmail(), is(equalTo("newemail@newemail.com")));

        assertThat(users.get(0).getPassword(), is(not(nullValue())));
        assertThat(users.get(0).getPassword().getValue(), is(not(nullValue())));
    }

    @Test
    public void testAddNonUniqueEmail() {
        insertTestData();

        User user = new User("Different Name", "trevorewen@gmail.com", new Password("pw555666"));

        ClientResponse response = client().resource(new MemmeeURLBuilder().setBaseURL(UserResource.BASE_URL).setMethodURL("user").build())
                .type(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, user);
        assertEquals(ClientResponse.Status.INTERNAL_SERVER_ERROR, response.getClientResponseStatus());
    }

    @Test
    public void testForgotPassword() {
        insertTestData();

        client().resource(new MemmeeURLBuilder().
                setBaseURL(UserResource.BASE_URL).
                setMethodURL("user/forgotpassword").
                setParam("email", "trevorewen@gmail.com").
                build()).post();
    }

    @Test
    public void testForgotPasswordInvalidEmail() {
        insertTestData();

        UserResourceException caseException = null;

        try {
            client().resource(new MemmeeURLBuilder().
                    setBaseURL(UserResource.BASE_URL).
                    setMethodURL("user/forgotpassword").
                    setParam("email", "mike@rocklobster.com").
                    build()).post();
        } catch (UserResourceException e) {
            caseException = e;
        }

        assertThat(caseException, is(not(nullValue())));
    }

    protected Long insertTestData() {
        Long passwordId = passwordDAO.insert(passwordGenerator.encrypt("abc123"), 0);
        return userDAO.insert("Adam", "trevorewen@gmail.com", passwordId, "apiKey500", new Date(), new Date(), Long.parseLong("1"), "9195551212");
    }
}
