package com.memmee;

import com.memmee.user.dao.MemmeeUserDAO;
import com.memmee.user.dto.MemmeeUser;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: waparrish
 * Date: 5/2/12
 * Time: 11:52 AM
 * To change this template use File | Settings | File Templates.
 */

@Path("/backend")
public class MemmeeResource {

    private MemmeeUserDAO memmeeDAO;

    public MemmeeResource(MemmeeUserDAO memmeeDAO) {
        super();
        this.memmeeDAO = memmeeDAO;
    }

    @GET
    @Path("/user")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public List<MemmeeUser> fetch(){

        return memmeeDAO.findAll();
    }

    @PUT
    @Path("/user/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public int update(@PathParam("id") Long id, MemmeeUser user)
    {
        return memmeeDAO.update(
                id,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }

    @POST
    @Path("/user")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public void add(MemmeeUser user)
    {
    	memmeeDAO.insert(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());
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
