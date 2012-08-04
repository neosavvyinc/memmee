package com.memmee.base;

import com.memmee.domain.user.dao.TransactionalUserDAO;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class BaseResource {
    protected final TransactionalUserDAO userDAO;

    public BaseResource(TransactionalUserDAO userDao) {
        super();
        this.userDAO = userDao;
    }

    protected void validateAccess(String apiKey) {
        if (userDAO.getUserByApiKey(apiKey) == null)
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
    }

}
