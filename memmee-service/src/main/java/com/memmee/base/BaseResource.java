package com.memmee.base;

import com.memmee.domain.user.dao.UserDAO;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Created with IntelliJ IDEA.
 * User: trevorewen
 * Date: 7/24/12
 * Time: 10:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaseResource {
    protected final UserDAO userDAO;

    public BaseResource(UserDAO userDao) {
        super();
        this.userDAO = userDao;
    }

    protected void validateAccess(String apiKey) {
        if (userDAO.getUserByApiKey(apiKey) == null)
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
    }

}
