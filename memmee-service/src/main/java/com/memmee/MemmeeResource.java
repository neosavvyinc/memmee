package com.memmee;

import com.memmee.user.dao.UserDAO;
import com.memmee.user.dto.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.skife.jdbi.v2.sqlobject.Bind;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: waparrish
 * Date: 5/2/12
 * Time: 11:52 AM
 * To change this template use File | Settings | File Templates.
 */

@Path("/memmeerest")
public class MemmeeResource {

    private UserDAO memmeeDAO;

    public MemmeeResource(UserDAO memmeeDAO) {
        super();
        this.memmeeDAO = memmeeDAO;
    }

    @GET
    @Path("/user")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public List<User> fetch(){

        return memmeeDAO.findAll();
    }

    @PUT
    @Path("/user/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public int update(@PathParam("id") Long id, User user)
    {
        return memmeeDAO.update(
                id,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getApiKey(),
                user.getApiDate()
        );
    }

    
    @POST
    @Path("/user")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public void add(User user)
    {
    	memmeeDAO.insert(user.getId(),
    			user.getFirstName(),
    			user.getLastName(),
    			user.getEmail(),
    			user.getApiKey(),
    			user.getApiDate(),
    			user.getCreationDate());
    }

    
    @DELETE
    @Path("/user/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON})
    public void delete(@PathParam("id") Long id)
    {
    	memmeeDAO.delete(id);
    }

}
