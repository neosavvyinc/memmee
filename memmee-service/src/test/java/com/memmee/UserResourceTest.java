package com.memmee;

import base.ResourceIntegrationTest;
import com.memmee.auth.PasswordGenerator;
import com.memmee.auth.PasswordGeneratorImpl;
import com.memmee.builder.MemmeeURLBuilder;
import com.memmee.error.UserResourceException;
import com.memmee.domain.user.dao.UserDAO;
import com.memmee.domain.user.dto.User;
import com.memmee.util.MemmeeMailSender;
import com.memmee.util.MemmeeMailSenderImpl;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class UserResourceTest extends ResourceIntegrationTest {

    private static UserDAO userDAO;
    private static MemmeeMailSender memmeeMailSender;
    private static PasswordGenerator passwordGenerator;

    @Override
    protected void setUpResources() throws Exception {
        super.setUpResources();

        //setup Daos
        userDAO = database.open(UserDAO.class);
        memmeeMailSender = new MemmeeMailSenderImpl();
        passwordGenerator = new PasswordGeneratorImpl();

        //add resources
        addResource(new UserResource(userDAO, passwordGenerator, memmeeMailSender));
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
    }

    @Test
    public void testLoginUser() {
        insertTestData();

        User user = client().resource(new MemmeeURLBuilder().
                setBaseURL(UserResource.BASE_URL).
                setMethodURL("user/login").
                build()).post(User.class, new User("Adam", "trevorewen@gmail.com", "abc123"));

        assertThat(user, is(not(nullValue())));
        assertThat(user.getPassword(), is(nullValue()));
    }

    @Test
    public void testLoginUnauthorizedUser() {
        insertTestData();

        RuntimeException caseException = null;

        try {
            User user = client().resource(new MemmeeURLBuilder().
                    setBaseURL(UserResource.BASE_URL).
                    setMethodURL("user/login").
                    build()).post(User.class, new User("Adam", "trevorewen@gmail.com", "abc124"));
        } catch (RuntimeException e) {
            caseException = e;
        }

        assertThat(caseException, is(not(nullValue())));
    }

    @Test
    public void testAdd() {
        User user = new User("Ed", "newemail@newemail.com", "myPas64400");
        client().resource(new MemmeeURLBuilder().setBaseURL(UserResource.BASE_URL).setMethodURL("user").build()).post(user);
        List<User> users = userDAO.findAll();

        assertThat(users.size(), is(equalTo(1)));
        assertThat(users.get(0).getEmail(), is(equalTo("newemail@newemail.com")));
        assertThat(users.get(0).getPassword(), is(equalTo(passwordGenerator.encrypt("myPas64400"))));
        assertThat(users.get(0).getPassword(), is(not(equalTo("myPas64400"))));
    }

    @Test
    public void testAddNonUniqueEmail() {
        insertTestData();

        UserResourceException caseException = null;

        User user = new User("Different Name", "trevorewen@gmail.com", "pw555666");

        try {
            client().resource(new MemmeeURLBuilder().setBaseURL(UserResource.BASE_URL).setMethodURL("user").build()).post(user);
        } catch (UserResourceException e) {
            caseException = e;
        }

        assertThat(caseException, is(not(nullValue())));
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
        return userDAO.insert("Adam", "trevorewen@gmail.com", passwordGenerator.encrypt("abc123"), "apiKey500", new Date(), new Date());
    }
}
