package com.memmee;

import com.memmee.user.dao.UserDAO;
import com.memmee.user.dto.User;
import com.yammer.dropwizard.logging.Log;
import org.skife.jdbi.v2.exceptions.DBIException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.ws.rs.core.Response.Status;
import javax.validation.Valid;

@Path("/memmeeuserrest")
public class UserResource {

    private UserDAO userDao;
    private static final Log LOG = Log.forClass(UserResource.class);

    public UserResource(UserDAO dao) {
        super();
        this.userDao = dao;
    }

    @GET
    @Path("/user")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public List<User> fetch() {

        return userDao.findAll();
    }

    @POST
    @Path("/user/login")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public User loginUser(@Valid User user) {
        User returnValue = userDao.loginUser(user.getEmail(), user.getPassword());
        return returnValue;
    }


    @PUT
    @Path("/user/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public int update(
            @QueryParam("apiKey") String apiKey,
            @PathParam("id") Long id, @Valid User user) {

        final User userLookup = userDao.getUserByApiKey(apiKey);
        if (userLookup == null) {
            LOG.error("USER NOT FOUND FOR API KEY:" + apiKey);
            throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
        }

        try {
            return userDao.update(
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
    }


    @POST
    @Path("/user")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public User add(@Valid User user) {
        user.setApiKey(UUID.randomUUID().toString());

        try {
            if (userDao.getUserCount(user.getEmail()) < 1) {
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