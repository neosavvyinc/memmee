package com.memmee;

import com.Ostermiller.util.RandPass;
import com.memmee.auth.PasswordGenerator;
import com.memmee.domain.password.dao.TransactionalPasswordDAO;
import com.memmee.error.UserResourceException;
import com.memmee.domain.user.dao.TransactionalUserDAO;
import com.memmee.domain.user.dto.User;
import com.memmee.util.MemmeeMailSender;
import com.memmee.util.StringUtil;
import com.yammer.dropwizard.logging.Log;
import org.apache.commons.validator.EmailValidator;
import org.skife.jdbi.v2.exceptions.DBIException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.util.*;
import javax.ws.rs.core.Response.Status;
import javax.validation.Valid;

@Path("/memmeeuserrest")
public class UserResource {

    public static final String BASE_URL = "memmeeuserrest";

    private TransactionalUserDAO userDao;
    private TransactionalPasswordDAO passwordDao;
    private static final Log LOG = Log.forClass(UserResource.class);
    private PasswordGenerator passwordGenerator;
    private MemmeeMailSender memmeeMailSender;
    private MemmeeUrlConfiguration memmeeUrlConfiguration;

    public UserResource(
            TransactionalUserDAO dao
            , TransactionalPasswordDAO passwordDao
            , PasswordGenerator passwordGenerator
            , MemmeeMailSender mailSender
            , MemmeeUrlConfiguration memmeeUrlConfiguration) {
        super();
        this.userDao = dao;
        this.passwordDao = passwordDao;
        this.passwordGenerator = passwordGenerator;
        this.memmeeMailSender = mailSender;
        this.memmeeUrlConfiguration = memmeeUrlConfiguration;

        this.memmeeMailSender.setUrlConfiguration(memmeeUrlConfiguration);
    }

    @GET
    @Path("/user/login")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public User loginUserByApiKey(@QueryParam("apiKey") String apiKey) {
        final User userLookup = userDao.getUserByApiKey(apiKey);

        //Keeps track of the number of times the user has logged in
        userDao.incrementLoginCount(userLookup.getId());
        userLookup.setLoginCount(userLookup.getLoginCount() + 1);

        userLookup.hidePassword();

        return userLookup;
    }


    @POST
    @Path("/user/login")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public User loginUser(User user) {
        User returnValue = userDao.getUserByEmail(user.getEmail());

        if (returnValue == null || invalidPassword(user, returnValue)) {
            throw new WebApplicationException(Status.UNAUTHORIZED);
        }

        //Keeps track of the number of times the user has logged in
        userDao.incrementLoginCount(returnValue.getId());
        returnValue.setLoginCount(returnValue.getLoginCount() + 1);

        returnValue.hidePassword();

        return returnValue;
    }


    @PUT
    @Path("/user/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public User update(
            @QueryParam("apiKey") String apiKey,
            @PathParam("id") Long id, @Valid User user) {

        User userLookup = userDao.getUserByApiKey(apiKey);
        if (userLookup == null) {
            LOG.error("USER NOT FOUND FOR API KEY:" + apiKey);
            throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
        }

        try {
            passwordDao.update(
                    user.getPassword().getId(),
                    passwordGenerator.encrypt(user.getPassword().getValue()),
                    0);
            userDao.update(
                    id,
                    user.getFirstName(),
                    user.getEmail(),
                    user.getPassword().getId(),
                    user.getApiKey(),
                    new Date(),
                    user.getLoginCount()
            );
        } catch (DBIException dbException) {
            LOG.error("DB EXCEPTION", dbException);
            throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
        }

        userLookup = userDao.getUserByApiKey(user.getApiKey());

        userLookup.hidePassword();

        return userLookup;
    }


    /**
     * Please note that a @Valid user can not be required
     * to add a new user since we don't accept a Password argument
     * when creating the user. Please don't use @Valid here.
     *
     * @param user
     * @return
     * @throws UserResourceException
     */
    @POST
    @Path("/user")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public User add(User user) throws UserResourceException {
        try {
            if (!EmailValidator.getInstance().isValid(user.getEmail()))
                throw new WebApplicationException(new UserResourceException(UserResourceException.INVALID_EMAIL));
            else if (userDao.getUserCount(user.getEmail()) >= 1) {
                throw new WebApplicationException(new UserResourceException(UserResourceException.IN_USE_EMAIL));
            } else {
                user.setApiKey(UUID.randomUUID().toString());
                memmeeMailSender.sendConfirmationEmail(user);
                Long userId = userDao.insert(user.getFirstName(),
                        user.getEmail(),
                        null,
                        user.getApiKey(),
                        new Date(),
                        new Date(),
                        Long.parseLong("1")
                );
                user = userDao.getUser(userId);
            }


        } catch (DBIException dbException) {
            LOG.error("DB EXCEPTION", dbException);
            throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
        }

        user.hidePassword();

        return user;
    }

    @POST
    @Path("/user/forgotpassword")
    public void forgotPassword(@QueryParam("email") String email) {
        User user = userDao.getUserByEmail(email);

        if (user == null)
            throw new UserResourceException("There is no user that exists with that email");

        String temporaryPassword = new RandPass().getPass(10);
        passwordDao.update(user.getPassword().getId(), passwordGenerator.encrypt(temporaryPassword), 1);
        memmeeMailSender.sendForgotPasswordEmail(user, temporaryPassword);
    }

    @DELETE
    @Path("/user/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public void delete(
            @QueryParam("apiKey") String apiKey,
            @PathParam("id") Long id) {
        final User user = userDao.getUserByApiKey(apiKey);

        if (user == null) {
            LOG.error("USER NOT FOUND FOR API KEY:" + apiKey);
            throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
        }

        try {
            userDao.delete(id);
        } catch (DBIException dbException) {
            LOG.error("DB EXCEPTION", dbException);
            throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
        }
    }

    protected boolean invalidPassword(User given, User stored) {
        return !passwordGenerator.encrypt(given.getPassword().getValue()).equals(stored.getPassword().getValue());
    }
}