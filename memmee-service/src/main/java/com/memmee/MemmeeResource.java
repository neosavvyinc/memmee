package com.memmee;

import java.sql.SQLException;
import java.util.Date;

import com.memmee.attachment.dao.TransactionalAttachmentDAO;
import com.memmee.attachment.dto.Attachment;
import com.memmee.memmees.dao.TransactionalMemmeeDAO;
import com.memmee.memmees.dto.Memmee;
import com.memmee.user.dao.TransactionalUserDAO;
import com.memmee.user.dao.UserDAO;
import com.memmee.user.dto.User;
import com.yammer.dropwizard.logging.Log;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.skife.jdbi.v2.exceptions.TransactionException;


@Path("/memmeetest")
public class MemmeeResource {

    private UserDAO userDao;
    private TransactionalMemmeeDAO memmeeDao;
    private TransactionalAttachmentDAO attachmentDao;
    private static final Log LOG = Log.forClass(MemmeeResource.class);

    public MemmeeResource(UserDAO userDao,TransactionalMemmeeDAO memmeeDao,TransactionalAttachmentDAO attachmentDao) {
        super();
        this.userDao = userDao;
        this.memmeeDao = memmeeDao;
        this.attachmentDao = attachmentDao;
    }


    @POST
    @Path("/insertmemmee")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public void add(@QueryParam("apiKey") String apiKey, Memmee memmee, Attachment attachment) 
    {
    	
    	User user = userDao.getUserByApiKey(apiKey);
    	
    	if(user == null){
    		LOG.error("USER NOT FOUND FOR API KEY:" + apiKey);
    		throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
    	}
    	
    	try{
    	
    	if(attachment != null){
    		
	    	memmeeDao.begin();
	    	long memmeeId = memmeeDao.insert(user.getId(), memmee.getTitle(), memmee.getText(),
	    			memmee.getLastUpdateDate(), memmee.getCreationDate(), memmee.getDisplayDate(), memmee.getShareKey(), null, null);
	        memmeeDao.commit();
	    	attachmentDao.begin();
	    	long attachmentId = attachmentDao.insert(memmeeId, attachment.getFilePath(), attachment.getType());
	    	attachmentDao.commit();
	    	memmeeDao.begin();
	    	memmeeDao.update(memmeeId, memmee.getTitle(), memmee.getText(), memmee.getLastUpdateDate(), memmee.getDisplayDate(), memmee.getShareKey(), attachmentId, null);
	    	memmeeDao.commit();
    	}
    	else{
    		memmeeDao.begin();
    		long memmeeId = memmeeDao.insert(user.getId(), memmee.getTitle(), memmee.getText(),
    		memmee.getLastUpdateDate(), memmee.getCreationDate(), memmee.getDisplayDate(), memmee.getShareKey(), null, null);
    		memmeeDao.commit();
    	}
    	}catch(TransactionException te){
    		
    		memmeeDao.rollback();
	    	attachmentDao.rollback();
	    	LOG.error(te.getMessage());
    	
    	}
    	finally{
    		memmeeDao.close();
    		attachmentDao.close();
    	}
	    	
    }

    @GET
    @Path("/insertmemmee2")
    @Produces({MediaType.APPLICATION_JSON})
    public void add2(@QueryParam("apiKey") String apiKey, @QueryParam("title") String title, @QueryParam("text") String text, @QueryParam("filePath") String filePath, @QueryParam("type") String type) 
    {
    	
    	User user = userDao.getUserByApiKey(apiKey);
    	
    	if(user == null){
    		LOG.error("USER NOT FOUND FOR API KEY:" + apiKey);
    		throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
    	}
    	
    	try{
    
	    	memmeeDao.begin();
	    	Long memmeeId = memmeeDao.insert(user.getId(), title, text,
	    			new Date(), new Date(), new Date(),"", null, null);
	        memmeeDao.commit();
	        
	    	attachmentDao.begin();
	    	Long attachmentId = attachmentDao.insert(memmeeId, filePath, type);
	    	attachmentDao.commit();
	    	memmeeDao.begin();
	    	memmeeDao.update(memmeeId, title, text, new Date(), new Date(), null, attachmentId, null);
	    	memmeeDao.commit();
    	
 
    	}catch(TransactionException te){
    		
    		memmeeDao.rollback();
	    	attachmentDao.rollback();
	    	LOG.error(te.getMessage());
    	
    	}
    	finally{
    		memmeeDao.close();
    		attachmentDao.close();
    	}
	    	
    }
    
}
