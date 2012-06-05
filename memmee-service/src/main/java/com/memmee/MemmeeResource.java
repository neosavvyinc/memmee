package com.memmee;

import com.memmee.user.dao.UserDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

public class MemmeeResource {

    private UserDAO userDao;

    public MemmeeResource(UserDAO userDao) {
        super();
        this.userDao = userDao;
    }


    @GET
    @Path("/memmee")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String fetch(){

        return "Hello world";
    }

}
