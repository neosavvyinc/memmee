package com.memmee;

import com.cribbstechnologies.clients.mandrill.exception.RequestFailedException;
import com.cribbstechnologies.clients.mandrill.model.MandrillHtmlMessage;
import com.cribbstechnologies.clients.mandrill.model.MandrillMessageRequest;
import com.cribbstechnologies.clients.mandrill.model.MandrillRecipient;
import com.cribbstechnologies.clients.mandrill.model.response.message.SendMessageResponse;
import com.cribbstechnologies.clients.mandrill.request.MandrillMessagesRequest;
import com.cribbstechnologies.clients.mandrill.request.MandrillRESTRequest;
import com.cribbstechnologies.clients.mandrill.util.MandrillConfiguration;
import com.memmee.user.dao.UserDAO;
import com.memmee.user.dto.User;
import com.yammer.dropwizard.logging.Log;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.skife.jdbi.v2.exceptions.DBIException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.util.*;
import javax.ws.rs.core.Response.Status;
import javax.validation.Valid;

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
    public List<User> fetch() {

        return userDao.findAll();
    }

    @GET
    @Path("/user/login")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public User loginUserByApiKey(@QueryParam("apiKey") String apiKey ) {
        final User userLookup = userDao.getUserByApiKey(apiKey);
        return userLookup;
    }


    @POST
    @Path("/user/login")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public User loginUser(@Valid User user) {
        User returnValue = userDao.loginUser(user.getEmail(), user.getPassword());

        if( returnValue == null )
        {
            throw new WebApplicationException(Status.UNAUTHORIZED);
        }

        return returnValue;
    }


    @PUT
    @Path("/user/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public User update(
            @QueryParam("apiKey") String apiKey,
            @PathParam("id") Long id, @Valid User user) {

        User userLookup = userDao.getUserByApiKey(apiKey);
        if (userLookup == null) {
            LOG.error("USER NOT FOUND FOR API KEY:" + apiKey);
            throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
        }

        try {
            userDao.update(
                    id,
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getApiKey(),
                    new Date()
            );
        } catch (DBIException dbException) {
            LOG.error("DB EXCEPTION", dbException);
            throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
        }

        userLookup = userDao.getUserByApiKey(user.getApiKey());
        return userLookup;
    }

    /**
     * Mandrill settings:
     *
     * SMTP: smtp.mandrillapp.com
     * PORT: 587
     * username: neosavvy
     * API Key:  439475a8-4c90-493b-96bc-0fc7bb4d1acb
     *
     * @param user
     * @return
     */

    @POST
    @Path("/user")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public User add(@Valid User user) {
        user.setApiKey(UUID.randomUUID().toString());
        sendConfirmationEmail(user);

        try {
            if (userDao.getUserCount(user.getEmail()) < 1) {
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
        } catch (DBIException dbException) {
            LOG.error("DB EXCEPTION", dbException);
            throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
        }

        return user;
    }


    private static MandrillRESTRequest request = new MandrillRESTRequest();
    private static MandrillConfiguration config = new MandrillConfiguration();
    private static MandrillMessagesRequest messagesRequest = new MandrillMessagesRequest();
    private static HttpClient client;
    private static ObjectMapper mapper = new ObjectMapper();

    private void sendConfirmationEmail(User user) {

        config.setApiKey("439475a8-4c90-493b-96bc-0fc7bb4d1acb");
        config.setApiVersion("1.0");
        config.setBaseURL("https://mandrillapp.com/api");
        request.setConfig(config);
        request.setObjectMapper(mapper);
        messagesRequest.setRequest(request);

        client = new DefaultHttpClient();
        request.setHttpClient(client);

        MandrillMessageRequest mmr = new MandrillMessageRequest();
        MandrillHtmlMessage message = new MandrillHtmlMessage();
        Map<String, String> headers = new HashMap<String, String>();
        message.setFrom_email("aparrish@neosavvy.com");
        message.setFrom_name("Adam Parrish");
        message.setHeaders(headers);
        message.setHtml("<html><body><h1>Welcome to Memmee.com!</h1>Please click here to confirm your account and fill out your profile.<a href=\"http://local.memmee.com/#/profile?apiKey="+user.getApiKey()+"\">Confirm your account</a></body></html>");
        message.setSubject("This is the subject");
        MandrillRecipient[] recipients = new MandrillRecipient[]{new MandrillRecipient("New Memmee User!", user.getEmail())};
        message.setTo(recipients);
        message.setTrack_clicks(true);
        message.setTrack_opens(true);
        String[] tags = new String[]{"tag1", "tag2", "tag3"};
        message.setTags(tags);
        mmr.setMessage(message);

        try {
            SendMessageResponse response = messagesRequest.sendMessage(mmr);
        } catch (RequestFailedException e) {
            e.printStackTrace();
        }
    }


    @DELETE
    @Path("/user/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public void delete(
            @QueryParam("apiKey") String apiKey,
            @PathParam("id") Long id) {
        final User user = userDao.getUserByApiKey(apiKey);

        if (user == null) {
            LOG.error("USER NOT FOUND FOR API KEY:" + apiKey);
            throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
        }

        try {
            userDao.delete(id);
        } catch (DBIException dbException) {
            LOG.error("DB EXCEPTION", dbException);
            throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
        }
    }

}