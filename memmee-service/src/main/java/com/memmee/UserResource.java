package com.memmee;

import com.memmee.user.dao.UserDAO;
import com.memmee.user.dto.User;
import com.yammer.dropwizard.logging.Log;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    public List<User> fetch(){

        return userDao.findAll();
    }
        
    @POST
    @Path("/user/login")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public User loginUser(User user){
        return userDao.loginUser(user.getEmail(), user.getPassword());
    }
    

    @PUT
    @Path("/user/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public int update(
//            @QueryParam("apiKey") String apiKey,
              @PathParam("id") Long id, User user)
    {
//    	final User userLookup = userDao.getUserByApiKey(apiKey);
//
//    	if(userLookup == null){
//    		LOG.error("USER NOT FOUND FOR API KEY:" + apiKey);
//    		throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
//    	}
    	
        return userDao.update(
                id,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getApiKey(),
                new Date()
        );
    }

    
    @POST
    @Path("/user")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public User add(User user)
    {
    	user.setApiKey(UUID.randomUUID().toString());

    	if(userDao.getUserCount(user.getEmail()) < 1){
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

        return user;
    }

    
    @DELETE
    @Path("/user/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON})
    public void delete(
//            @QueryParam("apiKey") String apiKey,
            @PathParam("id") Long id)
    {
//    	final User user = userDao.getUserByApiKey(apiKey);
//
//    	if(user == null){
//    		LOG.error("USER NOT FOUND FOR API KEY:" + apiKey);
//    		throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
//    	}
    	
    	userDao.delete(id);
    }

}