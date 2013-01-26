package com.memmee;


import com.memmee.base.BaseResource;
import com.memmee.domain.inspirationcategories.dao.TransactionalInspirationCategoryDAO;
import com.memmee.domain.inspirationcategories.domain.InspirationCategory;
import com.memmee.domain.inspirations.dao.TransactionalInspirationDAO;
import com.memmee.domain.inspirations.dto.Inspiration;
import com.memmee.domain.user.dao.TransactionalUserDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

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
    @Path("/inspirations/all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Inspiration> getRandomInspiration() {
        return inspirationDAO.getAllInspirations();
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
    public Inspiration getNextInspiration(@QueryParam("apiKey") String apiKey, @QueryParam("startingId") Long startingId, @QueryParam("currentId") Long currentId) {
        validateAccess(apiKey);
        Inspiration startingInspiration = inspirationDAO.getInspiration(startingId);
        Inspiration givenInspiration = inspirationDAO.getInspiration(currentId);
        Inspiration returnInspiration = inspirationDAO.getInspirationForInspirationCategoryAndIndex(givenInspiration.getInspirationCategory().getId(), givenInspiration.getInspirationCategoryIndex() + 1);

        if (returnInspiration == null)
            returnInspiration = inspirationDAO.getInspirationForInspirationCategoryAndIndex(givenInspiration.getInspirationCategory().getId(), Long.parseLong("0"));

        return nextInspiration(startingInspiration, givenInspiration, returnInspiration);
    }


    @GET
    @Path("/getpreviousinspiration")
    @Produces({MediaType.APPLICATION_JSON})
    public Inspiration getPreviousInspiration(@QueryParam("apiKey") String apiKey, @QueryParam("startingId") Long startingId, @QueryParam("currentId") Long currentId) {
        validateAccess(apiKey);
        Inspiration startingInspiration = inspirationDAO.getInspiration(startingId);
        Inspiration givenInspiration = inspirationDAO.getInspiration(currentId);
        Inspiration returnInspiration = inspirationDAO.getInspirationForInspirationCategoryAndIndex(givenInspiration.getInspirationCategory().getId(), givenInspiration.getInspirationCategoryIndex() - 1);

        if (returnInspiration == null) {
            Long highestInspirationId = inspirationDAO.getHighestInspirationForCategory(givenInspiration.getInspirationCategory().getId()).getId();
            returnInspiration = inspirationDAO.getInspiration(highestInspirationId);
        }

        return previousInspiration(startingInspiration, givenInspiration, returnInspiration);
    }

    protected Inspiration nextInspiration(Inspiration startingInspiration, Inspiration givenInspiration, Inspiration returnInspiration) {
        if (returnInspiration.equals(startingInspiration)) {
            InspirationCategory nextInspirationCategory = inspirationCategoryDAO.getInspirationCategoryByIndex(givenInspiration.getInspirationCategory().getIndex() + 1);
            if (nextInspirationCategory == null) {
                nextInspirationCategory = inspirationCategoryDAO.getInspirationCategoryByIndex(Long.parseLong("0"));
            }
            returnInspiration = inspirationDAO.getInspirationForInspirationCategoryAndIndex(nextInspirationCategory.getId(), Long.parseLong("0"));
        }
        return returnInspiration;
    }

    protected Inspiration previousInspiration(Inspiration startingInspiration, Inspiration givenInspiration, Inspiration returnInspiration) {
        if (returnInspiration.equals(startingInspiration)) {
            InspirationCategory previousInspirationCategory = inspirationCategoryDAO.getInspirationCategoryByIndex(givenInspiration.getInspirationCategory().getIndex() - 1);
            if (previousInspirationCategory == null) {
                previousInspirationCategory = inspirationCategoryDAO.getHighestInspirationCategory();
            }
            returnInspiration = inspirationDAO.getInspirationForInspirationCategoryAndIndex(previousInspirationCategory.getId(), Long.parseLong(Integer.toString(inspirationDAO.getInspirationsForInspirationCategory(previousInspirationCategory.getId()).size() - 1)));
        }
        return returnInspiration;
    }
}
