package com.memmee;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.memmee.attachment.dao.TransactionalAttachmentDAO;
import com.memmee.attachment.dto.Attachment;
import com.memmee.memmees.dao.MemmeeDAO;
import com.memmee.memmees.dao.TransactionalMemmeeDAO;
import com.memmee.memmees.dto.Memmee;
import com.memmee.theme.dto.Theme;
import com.memmee.user.dao.UserDAO;
import com.memmee.user.dto.User;
import com.yammer.dropwizard.db.Database;
import com.yammer.dropwizard.logging.Log;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.Transaction;
import org.skife.jdbi.v2.exceptions.DBIException;
import org.skife.jdbi.v2.exceptions.TransactionException;
import org.skife.jdbi.v2.TransactionStatus;



@Path("/memmeetest")
public class MemmeeResource {
    private final UserDAO userDao;
	private final TransactionalMemmeeDAO memmeeDao;
    private static final Log LOG = Log.forClass(MemmeeResource.class);

    public MemmeeResource(UserDAO userDao,TransactionalMemmeeDAO memmeeDao) {
        super();       
        this.userDao = userDao;
        this.memmeeDao = memmeeDao;
    }
    
    
    @GET
    @Path("/getmemmees")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Memmee> getMemmees(@QueryParam("apiKey") String apiKey){
    	
    	User user = userDao.getUserByApiKey(apiKey);
    	
    	if(user == null){
	    	LOG.error("USER NOT FOUND FOR API KEY:" + apiKey);
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
    	}
    	
        return memmeeDao.getMemmeesbyUser(user.getId());
        
        
    }
    
    @GET
    @Path("/getmemmee")
    @Produces({MediaType.APPLICATION_JSON})
    public Memmee getMemmee(@QueryParam("apiKey") String apiKey, @QueryParam("id") Long id){

    	User user = userDao.getUserByApiKey(apiKey);
    	
    	
    	if(user == null){
	    	LOG.error("USER NOT FOUND FOR API KEY:" + apiKey);
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
    	}
        return memmeeDao.getMemmee(id);
        
        
    }

    @POST
    @Path("/insertmemmee")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Memmee add(@QueryParam("apiKey") String apiKey, final Memmee memmee, final Attachment attachment, final Theme theme) 
    {
    	
    	long memmeeId = -1;
    	
    	final User user = userDao.getUserByApiKey(apiKey);
    	
    	if(user == null){
    		LOG.error("USER NOT FOUND FOR API KEY:" + apiKey);
    		throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
    	}

    	try{
    		
    	if(attachment != null){
    		
    		memmeeId = memmeeDao.inTransaction(new Transaction<Integer, TransactionalMemmeeDAO>()
  				  {
  				    public Integer inTransaction(TransactionalMemmeeDAO tx, TransactionStatus status) throws Exception
  				    {
  				    	
  				    	Long memmeeId = memmeeDao.insert(user.getId(), memmee.getTitle(), memmee.getText(),
  				    			new Date(), new Date(), new Date(),"", null, theme.getId());

  				    	Long attachmentId = memmeeDao.insertAttachment(memmeeId, attachment.getFilePath(), attachment.getType());
	
  				    	memmeeDao.update(memmeeId, memmee.getTitle(), memmee.getText(), new Date(), new Date(), null, attachmentId, theme.getId());
  				      
  				    	return memmeeId.intValue();
  				   
  				    }
  				  });
    	}
    	else{
    		memmeeId = memmeeDao.insert(user.getId(), memmee.getTitle(), memmee.getText(),
    		memmee.getLastUpdateDate(), memmee.getCreationDate(), memmee.getDisplayDate(), memmee.getShareKey(), null, theme.getId()).intValue();
    	}
    	
    	}catch(DBIException dbException){
    		throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
    	}
    	
    	
    	return memmeeDao.getMemmee(new Long(memmeeId));
    		    	
    }
    	
    
/*
    @GET
    @Path("/insertmemmee2")
    @Produces({MediaType.APPLICATION_JSON})
    public Memmee add2(@QueryParam("apiKey") String apiKey, @QueryParam("title") String title, @QueryParam("text") String text, @QueryParam("filePath") String filePath, @QueryParam("type") String type, @QueryParam("themeId") Long themeId) throws DBIException 
    {
    	
    	final Handle h = this.getWriteHandle();
        final memmeeDao memmeeDao = h.attach(memmeeDao.class);
        final TransactionalAttachmentDAO attachmentDao = h.attach(TransactionalAttachmentDAO.class);
       
    	User user = userDao.getUserByApiKey(apiKey);
    	long memmeeId = -1;
    	
    	if(user == null){
    		LOG.error("USER NOT FOUND FOR API KEY:" + apiKey);
    		throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
    	}
    	
    	try{
    
	    	memmeeId = memmeeDao.insert(user.getId(), title, text,
	    			new Date(), new Date(), new Date(),"", null, themeId);

	    	Long attachmentId = attachmentDao.insert(memmeeId, filePath, type);

	    	memmeeDao.update(memmeeId, title, text, new Date(), new Date(), null, attachmentId, themeId);

    		
    	}catch(DBIException dbException){
    	  	attachmentDao.rollback();
    		memmeeDao.rollback();
	    	LOG.error(dbException.getMessage());
	    	throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
    	
    	}
    	finally{
    		memmeeDao.commit();
    		attachmentDao.commit();
    		memmeeDao.close();
    		attachmentDao.close();
    	}
    	
    	return memmeeDao.getMemmee(memmeeId);
	    	
    }
  */
    
    @GET
    @Path("/insertmemmee3")
    @Produces({MediaType.APPLICATION_JSON})
    public Memmee add3(@QueryParam("apiKey") String apiKey, @QueryParam("title") final String title, @QueryParam("text") final String text, @QueryParam("filePath") final String filePath, @QueryParam("type") final String type, @QueryParam("themeId") final Long themeId) throws DBIException 
    {
    
    	final User user = userDao.getUserByApiKey(apiKey);
    	int memmeeId = 0;
    	
    	if(user == null){
    		LOG.error("USER NOT FOUND FOR API KEY:" + apiKey);
    		throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
    	}
    	
    	try{
    		memmeeId = memmeeDao.inTransaction(new Transaction<Integer, TransactionalMemmeeDAO>()
    				  {
    				    public Integer inTransaction(TransactionalMemmeeDAO tx, TransactionStatus status) throws Exception
    				    {
    				    	
    				    	Long memmeeId = memmeeDao.insert(user.getId(), title, text,
    				    			new Date(), new Date(), new Date(),"", null, themeId);

    				    	Long attachmentId = memmeeDao.insertAttachment(memmeeId, filePath, type);
 
    				    	memmeeDao.update(memmeeId, title, text, new Date(), new Date(), null, attachmentId, themeId);
    				      
    				    	return memmeeId.intValue();
    				   
    				    }
    				  });
    	}catch(DBIException dbException){
    		throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
    	}
   
    	return memmeeDao.getMemmee(new Long(memmeeId));
	    	
    }
    
    /*
    private Handle getWriteHandle(){
    	  Handle h = db.open();
          try{
          	h.getConnection().setAutoCommit(false);
          }catch(SQLException se){
          	LOG.error(se.getMessage());
          }
          return h;
    }
    */
    
}
