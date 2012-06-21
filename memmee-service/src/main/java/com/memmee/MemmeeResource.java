package com.memmee;


import java.util.Date;
import java.util.List;
import com.memmee.attachment.dto.Attachment;
import com.memmee.memmees.dao.TransactionalMemmeeDAO;
import com.memmee.memmees.dto.Memmee;
import com.memmee.user.dao.UserDAO;
import com.memmee.user.dto.User;
import com.yammer.dropwizard.logging.Log;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.Transaction;
import org.skife.jdbi.v2.exceptions.DBIException;
import org.skife.jdbi.v2.exceptions.TransactionException;
import org.skife.jdbi.v2.TransactionStatus;

@Path("/memmeerest")
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
    public Memmee insertMemmee(@QueryParam("apiKey") String apiKey, @Valid final Memmee memmee)
    {
    	
    	long memmeeId = -1;
    	
    	final User user = userDao.getUserByApiKey(apiKey);
    	
    	if(user == null){
    		LOG.error("USER NOT FOUND FOR API KEY:" + apiKey);
    		throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
    	}

    	try{
    		
//    	if(attachment != null){
//
//    		memmeeId = memmeeDao.inTransaction(new Transaction<Integer, TransactionalMemmeeDAO>()
//  				  {
//  				    public Integer inTransaction(TransactionalMemmeeDAO tx, TransactionStatus status) throws Exception
//  				    {
//
//  				    	Long memmeeId = memmeeDao.insert(user.getId(), memmee.getTitle(), memmee.getText(),
//  				    			new Date(), new Date(), new Date(),"", null, null);
//
//  				    	Long attachmentId = memmeeDao.insertAttachment(memmeeId, attachment.getFilePath(), attachment.getType());
//
//  				    	memmeeDao.update(memmeeId, memmee.getTitle(), memmee.getText(), new Date(), new Date(), null, attachmentId, null);
//
//  				    	return memmeeId.intValue();
//
//  				    }
//  				  });
//    	}
//    	else{
    		memmeeId = memmeeDao.insert(
                    user.getId()
                    ,memmee.getTitle()
                    ,memmee.getText()
                    ,new Date()
                    ,new Date()
                    ,new Date()
                    ,memmee.getShareKey()
                    ,null
                    ,null).intValue();
//    	}
    	
    	}catch(DBIException dbException){
    		LOG.error("DB EXCEPTION",dbException);
    		throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
    	}
    	
    	
    	return memmeeDao.getMemmee(new Long(memmeeId));
    		    	
    }
    
    @PUT
    @Path("/updatememmeewithattachment")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Memmee updateMemmeeWithAttachment(@QueryParam("apiKey") String apiKey, final Memmee memmee)
    {
    	
        int count = 0;
    	
    	final User user = userDao.getUserByApiKey(apiKey);
    	
    	if(user == null){
    		LOG.error("USER NOT FOUND FOR API KEY:" + apiKey);
    		throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
    	}

    	try{
    		

    		count = memmeeDao.inTransaction(new Transaction<Integer, TransactionalMemmeeDAO>()
  				  {
  				    public Integer inTransaction(TransactionalMemmeeDAO tx, TransactionStatus status) throws Exception
  				    {
  				    	
  				    	int count = memmeeDao.update(memmee.getId(), memmee.getTitle(), memmee.getText(),
  				    			new Date(), new Date(),memmee.getShareKey(), null, null);

  				    	memmeeDao.updateAttachment(memmee.getAttachment().getId(), null, null);

  				    	return count;
  				   
  				    }
  				  });
    			
    	}catch(DBIException dbException){
    		LOG.error("DB EXCEPTION",dbException);
    		throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
    	}
    	
    	if(count == 0){
		   	LOG.error("MEMMEE NOT UPDATED");
	    	throw new WebApplicationException(Status.NOT_MODIFIED);
	    }

        Memmee returnValue = memmeeDao.getMemmee(new Long(memmee.getId()));

        return returnValue;
    		    	
    }
    
    @PUT
    @Path("/updatememmee")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Memmee updateMemmee(@QueryParam("apiKey") String apiKey, final Memmee memmee) 
    {
    	
    	int count = 0;
    	
    	final User user = userDao.getUserByApiKey(apiKey);
    	
    	if(user == null){
    		LOG.error("USER NOT FOUND FOR API KEY:" + apiKey);
    		throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
    	}

    	try{
    		

    
    	count = memmeeDao.update(memmee.getId(), memmee.getTitle(), memmee.getText(),
    			new Date(), new Date(),memmee.getShareKey(), null, null);
    	
    	}catch(DBIException dbException){
    		LOG.error("DB EXCEPTION",dbException);
    		throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
    	}
    	
    	if(count == 0){
		   	LOG.error("MEMMEE NOT UPDATED");
	    	throw new WebApplicationException(Status.NOT_MODIFIED);
	    }
    	
     	return memmeeDao.getMemmee(new Long(memmee.getId()));
    		    	
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
    
    /*
    @GET
    @Path("/insertmemmeeparams")
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
    */
    
    
    @DELETE
    @Path("/deletememmee")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON})
    public void delete(@QueryParam("apiKey") String apiKey,@QueryParam("id") final Long id)
    
    {
    	final User user = userDao.getUserByApiKey(apiKey);

    	if(user == null){
    		LOG.error("USER NOT FOUND FOR API KEY:" + apiKey);
    		throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
    	}
    	
    	
    	memmeeDao.delete(id);
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
