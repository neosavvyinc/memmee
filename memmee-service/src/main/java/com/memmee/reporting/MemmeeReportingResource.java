package com.memmee.reporting;


import com.memmee.domain.user.dto.User;
import com.memmee.reporting.dao.MemmeeReportingDAO;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: waparrish
 * Date: 10/30/12
 * Time: 7:39 PM
 */
@Path("/reporting")
public class MemmeeReportingResource  {

    private MemmeeReportingDAO reportingDAO;

    public MemmeeReportingResource(MemmeeReportingDAO reportingDAO) {
        this.reportingDAO = reportingDAO;
    }

    @GET
    @Path("/users")
    @Produces({MediaType.APPLICATION_JSON})
    public List<User> getUsers() {
        return reportingDAO.getUsers();
    }

    @GET
    @Path("/users/web")
    @Produces({MediaType.APPLICATION_JSON})
    public List<User> getUsersForWeb() {
        return reportingDAO.getUsersForWeb();
    }

    @GET
    @Path("/users/mobile")
    @Produces({MediaType.APPLICATION_JSON})
    public List<User> getUsersForMobile() {
        return reportingDAO.getUsersForMobile();
    }

    @GET
    @Path("/users/completed/profile")
    @Produces({MediaType.APPLICATION_JSON})
    public List<User> getUsersWithCompletedProfiles() {
        return reportingDAO.getUsersWithCompletedProfiles();
    }

    @GET
    @Path("/users/no/profile")
    @Produces({MediaType.APPLICATION_JSON})
    public List<User> getUsersWithNoCompletedProfiles() {
        return reportingDAO.getUsersWithNoCompletedProfiles();
    }

    @GET
    @Path("/users/memmeecount")
    @Produces({MediaType.APPLICATION_JSON})
    public List<User> getUsersWithMemmeeCount() {
        return reportingDAO.getUsersWithMemmeeCount();
    }

    @GET
    @Path("/users/no/memmeecount")
    @Produces({MediaType.APPLICATION_JSON})
    public List<User> getUsersWithNoMemmees() {
        return reportingDAO.getUsersWithNoMemmees();
    }

    @GET
    @Path("/users/usercount")
    @Produces({MediaType.APPLICATION_JSON})
    public Integer getNumUsers() {
        return reportingDAO.getNumUsers();
    }

}