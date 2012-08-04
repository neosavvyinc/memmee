package com.memmee;


import com.memmee.base.BaseResource;
import com.memmee.domain.inspirations.dao.TransactionalInspirationDAO;
import com.memmee.domain.inspirations.dto.Inspiration;
import com.memmee.domain.user.dao.TransactionalUserDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/memmeeinspirationrest")
public class InspirationResource extends BaseResource {

    public static final String BASE_URL = "memmeeinspirationrest";

    private TransactionalInspirationDAO inspirationDAO;

    public InspirationResource(TransactionalUserDAO userDao, TransactionalInspirationDAO inspirationDAO) {
        super(userDao);
        this.inspirationDAO = inspirationDAO;
    }

    @GET
    @Path("/getrandominspiration")
    @Produces({MediaType.APPLICATION_JSON})
    public Inspiration getRandomInspiration(@QueryParam("apiKey") String apiKey, @QueryParam("excludeId") Long excludeId) {
        validateAccess(apiKey);
        if (excludeId != null)
            return inspirationDAO.getRandomInspiration(excludeId);
        return inspirationDAO.getRandomInspiration();
    }
}
