package com.memmee;


import com.memmee.base.BaseResource;
import com.memmee.domain.inspirationcategories.dao.TransactionalInspirationCategoryDAO;
import com.memmee.domain.inspirationcategories.domain.InspirationCategory;
import com.memmee.domain.inspirations.dao.TransactionalInspirationDAO;
import com.memmee.domain.inspirations.dto.Inspiration;
import com.memmee.domain.user.dao.TransactionalUserDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/memmeeinspirationrest")
public class InspirationResource extends BaseResource {

    public static final String BASE_URL = "memmeeinspirationrest";

    private TransactionalInspirationDAO inspirationDAO;
    private TransactionalInspirationCategoryDAO inspirationCategoryDAO;

    public InspirationResource(TransactionalUserDAO userDao, TransactionalInspirationDAO inspirationDAO, TransactionalInspirationCategoryDAO inspirationCategoryDAO) {
        super(userDao);
        this.inspirationDAO = inspirationDAO;
        this.inspirationCategoryDAO = inspirationCategoryDAO;
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

    @GET
    @Path("/getnextinspiration")
    @Produces({MediaType.APPLICATION_JSON})
    public Inspiration getNextInspiration(@QueryParam("apiKey") String apiKey, @QueryParam("currentId") Long currentId) {
        validateAccess(apiKey);
        Inspiration givenInspiration = inspirationDAO.getInspiration(currentId);
        Inspiration returnInspiration = inspirationDAO.getInspirationForInspirationCategoryAndIndex(givenInspiration.getInspirationCategory().getId(), givenInspiration.getInspirationCategoryIndex() + 1);
        return nextInspiration(givenInspiration, returnInspiration);
    }


    @GET
    @Path("/getpreviousinspiration")
    @Produces({MediaType.APPLICATION_JSON})
    public Inspiration getPreviousInspiration(@QueryParam("apiKey") String apiKey, @QueryParam("currentId") Long currentId) {
        validateAccess(apiKey);
        Inspiration givenInspiration = inspirationDAO.getInspiration(currentId);
        Inspiration returnInspiration = inspirationDAO.getInspirationForInspirationCategoryAndIndex(givenInspiration.getInspirationCategory().getId(), givenInspiration.getInspirationCategoryIndex() - 1);
        return previousInspiration(givenInspiration, returnInspiration);
    }

    protected Inspiration nextInspiration(Inspiration givenInspiration, Inspiration returnInspiration) {
        if (returnInspiration == null) {
            InspirationCategory nextInspirationCategory = inspirationCategoryDAO.getInspirationCategoryByIndex(givenInspiration.getInspirationCategory().getIndex() + 1);
            if (nextInspirationCategory == null) {
                nextInspirationCategory = inspirationCategoryDAO.getInspirationCategoryByIndex(Long.parseLong("0"));
            }
            returnInspiration = inspirationDAO.getInspirationForInspirationCategoryAndIndex(nextInspirationCategory.getId(), Long.parseLong("0"));
        }
        return returnInspiration;
    }

    protected Inspiration previousInspiration(Inspiration givenInspiration, Inspiration returnInspiration) {
        if (returnInspiration == null) {
            InspirationCategory previousInspirationCategory = inspirationCategoryDAO.getInspirationCategoryByIndex(givenInspiration.getInspirationCategory().getIndex() - 1);
            if (previousInspirationCategory == null) {
                previousInspirationCategory = inspirationCategoryDAO.getHighestInspirationCategory();
            }
            returnInspiration = inspirationDAO.getInspirationForInspirationCategoryAndIndex(previousInspirationCategory.getId(), Long.parseLong("0"));
        }
        return returnInspiration;
    }
}
