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
import org.skife.jdbi.v2.exceptions.DBIException;



@Path("/memmeetest")
public class MemmeeResource {
    private final Database db;
    private final UserDAO readUserDao;
	private final MemmeeDAO readMemmeeDao;
    private static final Log LOG = Log.forClass(MemmeeResource.class);

    public MemmeeResource(Database db,UserDAO userDao,MemmeeDAO memmeeDao) {
        super();
        this.db = db;        
        this.readUserDao = userDao;
        this.readMemmeeDao = memmeeDao;
    }
    
    
    @GET
    @Path("/getmemmees")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Memmee> getMemmees(@QueryParam("apiKey") String apiKey){
    	
    	User user = readUserDao.getUserByApiKey(apiKey);
    	
    	if(user == null){
	    	LOG.error("USER NOT FOUND FOR API KEY:" + apiKey);
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
    	}
    	
        return readMemmeeDao.getMemmeesbyUser(user.getId());
        
        
    }
    
    @GET
    @Path("/getmemmee")
    @Produces({MediaType.APPLICATION_JSON})
    public Memmee getMemmee(@QueryParam("apiKey") String apiKey, @QueryParam("id") Long id){

    	User user = readUserDao.getUserByApiKey(apiKey);
    	
    	
    	if(user == null){
	    	LOG.error("USER NOT FOUND FOR API KEY:" + apiKey);
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
    	}
        return readMemmeeDao.getMemmee(id);
        
        
    }

    @POST
    @Path("/insertmemmee")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Memmee add(@QueryParam("apiKey") String apiKey, Memmee memmee, Attachment attachment, Theme theme) 
    {
    	
    	final Handle h = this.getWriteHandle();
        final TransactionalMemmeeDAO memmeeDao = h.attach(TransactionalMemmeeDAO.class);
        final TransactionalAttachmentDAO attachmentDao = h.attach(TransactionalAttachmentDAO.class);
    	long memmeeId = -1;
    	
    	User user = readUserDao.getUserByApiKey(apiKey);
    	
    	if(user == null){
    		LOG.error("USER NOT FOUND FOR API KEY:" + apiKey);
    		throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
    	}
    	
    	try{
    	
    	if(attachment != null){
    		
	    	memmeeId = memmeeDao.insert(user.getId(), memmee.getTitle(), memmee.getText(),
	    			memmee.getLastUpdateDate(), memmee.getCreationDate(), memmee.getDisplayDate(), memmee.getShareKey(), null, theme.getId());
	    	
	    	long attachmentId = attachmentDao.insert(memmeeId, attachment.getFilePath(), attachment.getType());

	    	memmeeDao.update(memmeeId, memmee.getTitle(), memmee.getText(), memmee.getLastUpdateDate(), memmee.getDisplayDate(), memmee.getShareKey(), attachmentId, theme.getId());

    	}
    	else{
    		memmeeId = memmeeDao.insert(user.getId(), memmee.getTitle(), memmee.getText(),
    		memmee.getLastUpdateDate(), memmee.getCreationDate(), memmee.getDisplayDate(), memmee.getShareKey(), null, theme.getId());
    	}
    	}catch(DBIException dbException){
    		
    		memmeeDao.rollback();
	    	attachmentDao.rollback();
	    	LOG.error(dbException.getMessage());
	    	throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
    	
    	}
    	finally{
    		memmeeDao.commit();
    		attachmentDao.commit();
    		memmeeDao.close();
    		attachmentDao.close();
    	}
    	
    	return readMemmeeDao.getMemmee(memmeeId);
	    	
    }
    

    @GET
    @Path("/insertmemmee2")
    @Produces({MediaType.APPLICATION_JSON})
    public Memmee add2(@QueryParam("apiKey") String apiKey, @QueryParam("title") String title, @QueryParam("text") String text, @QueryParam("filePath") String filePath, @QueryParam("type") String type, @QueryParam("themeId") Long themeId) throws DBIException 
    {
    	
    	final Handle h = this.getWriteHandle();
        final TransactionalMemmeeDAO memmeeDao = h.attach(TransactionalMemmeeDAO.class);
        final TransactionalAttachmentDAO attachmentDao = h.attach(TransactionalAttachmentDAO.class);
       
    	User user = readUserDao.getUserByApiKey(apiKey);
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
    	
    	return readMemmeeDao.getMemmee(memmeeId);
	    	
    }
    
    private Handle getWriteHandle(){
    	  Handle h = db.open();
          try{
          	h.getConnection().setAutoCommit(false);
          }catch(SQLException se){
          	LOG.error(se.getMessage());
          }
          return h;
    }
    
}
