package com.memmee;

import com.memmee.error.UserResourceException;
import com.memmee.domain.user.dao.UserDAO;
import com.memmee.domain.user.dto.User;
import com.memmee.util.MemmeeMailSender;
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

    private UserDAO userDao;
    private static final Log LOG = Log.forClass(UserResource.class);

    private MemmeeMailSender memmeeMailSender;

    public UserResource(UserDAO dao, MemmeeMailSender mailSender) {
        super();
        this.userDao = dao;
        this.memmeeMailSender = mailSender;
    }

    @GET
    @Path("/user")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public List<User> fetch() {

        return userDao.findAll();
    }

    @GET
    @Path("/user/login")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public User loginUserByApiKey(@QueryParam("apiKey") String apiKey) {
        final User userLookup = userDao.getUserByApiKey(apiKey);
        return userLookup;
    }


    @POST
    @Path("/user/login")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public User loginUser(User user) {
        User returnValue = userDao.loginUser(user.getEmail(), user.getPassword());

        if (returnValue == null) {
            throw new WebApplicationException(Status.UNAUTHORIZED);
        }

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
            userDao.update(
                    id,
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getApiKey(),
                    new Date()
            );
        } catch (DBIException dbException) {
            LOG.error("DB EXCEPTION", dbException);
            throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
        }

        userLookup = userDao.getUserByApiKey(user.getApiKey());
        return userLookup;
    }


    @POST
    @Path("/user")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public User add(@Valid User user) throws UserResourceException {
        user.setApiKey(UUID.randomUUID().toString());
        memmeeMailSender.sendConfirmationEmail(user);

        try {
            if (!EmailValidator.getInstance().isValid(user.getEmail()))
                throw new UserResourceException(UserResourceException.INVALID_EMAIL);
            else if (userDao.getUserCount(user.getEmail()) >= 1) {
                throw new UserResourceException(UserResourceException.IN_USE_EMAIL);
            } else {
                Long userId = userDao.insert(user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getApiKey(),
                        new Date(),
                        new Date()
                );
                user = userDao.getUser(userId);
            }


        } catch (DBIException dbException) {
            LOG.error("DB EXCEPTION", dbException);
            throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
        }

        return user;
    }

    @POST
    @Path("/user/forgotpassword")
    @Consumes({MediaType.APPLICATION_JSON})
    public void forgotPassword(String email) {
        User user = userDao.getUserByEmail(email);

        if (user == null)
            throw new UserResourceException("There is no user that exists with that email");

        memmeeMailSender.sendForgotPasswordEmail(user);
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
}