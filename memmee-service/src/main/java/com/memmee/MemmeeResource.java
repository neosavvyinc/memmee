package com.memmee;


import java.util.Date;
import java.util.List;

import com.memmee.attachment.dao.TransactionalAttachmentDAO;
import com.memmee.attachment.dto.Attachment;
import com.memmee.memmees.dao.TransactionalMemmeeDAO;
import com.memmee.memmees.dto.Memmee;
import com.memmee.user.dao.UserDAO;
import com.memmee.user.dto.User;
import com.memmee.util.OsUtil;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;


@Path("/memmeerest")
public class MemmeeResource {
    private final UserDAO userDao;
    private final TransactionalMemmeeDAO memmeeDao;
    private final TransactionalAttachmentDAO attachmentDAO;
    private static final Log LOG = Log.forClass(MemmeeResource.class);

    public MemmeeResource(UserDAO userDao, TransactionalMemmeeDAO memmeeDao, TransactionalAttachmentDAO attachmentDao) {
        super();
        this.userDao = userDao;
        this.memmeeDao = memmeeDao;
        this.attachmentDAO = attachmentDao;
    }


    @GET
    @Path("/getmemmees")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Memmee> getMemmees(@QueryParam("apiKey") String apiKey) {

        User user = userDao.getUserByApiKey(apiKey);

        if (user == null) {
            LOG.error("USER NOT FOUND FOR API KEY:" + apiKey);
            throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
        }

        List<Memmee> memmeesbyUser = memmeeDao.getMemmeesbyUser(user.getId());

        return memmeesbyUser;


    }

    @GET
    @Path("/getmemmee")
    @Produces({MediaType.APPLICATION_JSON})
    public Memmee getMemmee(@QueryParam("apiKey") String apiKey, @QueryParam("id") Long id) {

        User user = userDao.getUserByApiKey(apiKey);


        if (user == null) {
            LOG.error("USER NOT FOUND FOR API KEY:" + apiKey);
            throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
        }
        return memmeeDao.getMemmee(id);


    }

    @POST
    @Path("/insertmemmee")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Memmee insertMemmee(@QueryParam("apiKey") String apiKey, @Valid final Memmee memmee) {

        long memmeeId = -1;

        final User user = userDao.getUserByApiKey(apiKey);

        if (user == null) {
            LOG.error("USER NOT FOUND FOR API KEY:" + apiKey);
            throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
        }

        try {
            final Attachment attachment = memmee.getAttachment();
            if (attachment != null) {

                memmeeId = memmeeDao.inTransaction(new Transaction<Integer, TransactionalMemmeeDAO>() {
                    public Integer inTransaction(TransactionalMemmeeDAO tx, TransactionStatus status) throws Exception {
                        Long memmeeId = memmeeDao.insert(user.getId(), memmee.getTitle(), memmee.getText(),
                                new Date(), new Date(), new Date(), "", null, null);

                        Long attachmentId = memmeeDao.insertAttachment(memmeeId, attachment.getFilePath(), attachment.getType());

                        memmeeDao.update(memmeeId, memmee.getTitle(), memmee.getText(), new Date(), new Date(), null, attachmentId, null);

                        return memmeeId.intValue();
                    }
                });
            } else {
                memmeeId = memmeeDao.insert(
                        user.getId()
                        , memmee.getTitle()
                        , memmee.getText()
                        , new Date()
                        , new Date()
                        , new Date()
                        , memmee.getShareKey()
                        , null
                        , null).intValue();
            }

        } catch (DBIException dbException) {
            LOG.error("DB EXCEPTION", dbException);
            throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
        }


        return memmeeDao.getMemmee(new Long(memmeeId));

    }

    @PUT
    @Path("/updatememmeewithattachment")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Memmee updateMemmeeWithAttachment(@QueryParam("apiKey") String apiKey, final Memmee memmee) {

        int count = 0;

        final User user = userDao.getUserByApiKey(apiKey);

        if (user == null) {
            LOG.error("USER NOT FOUND FOR API KEY:" + apiKey);
            throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
        }

        try {


            count = memmeeDao.inTransaction(new Transaction<Integer, TransactionalMemmeeDAO>() {
                public Integer inTransaction(TransactionalMemmeeDAO tx, TransactionStatus status) throws Exception {

                    int count = memmeeDao.update(memmee.getId(), memmee.getTitle(), memmee.getText(),
                            new Date(), new Date(), memmee.getShareKey(), null, null);

                    memmeeDao.updateAttachment(memmee.getAttachment().getId(), null, null);

                    return count;

                }
            });

        } catch (DBIException dbException) {
            LOG.error("DB EXCEPTION", dbException);
            throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
        }

        if (count == 0) {
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
    public Memmee updateMemmee(@QueryParam("apiKey") String apiKey, final Memmee memmee) {

        int count = 0;

        final User user = userDao.getUserByApiKey(apiKey);

        if (user == null) {
            LOG.error("USER NOT FOUND FOR API KEY:" + apiKey);
            throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
        }

        try {


            count = memmeeDao.update(memmee.getId(), memmee.getTitle(), memmee.getText(),
                    new Date(), new Date(), memmee.getShareKey(), null, null);

        } catch (DBIException dbException) {
            LOG.error("DB EXCEPTION", dbException);
            throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
        }

        if (count == 0) {
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
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public void delete(@QueryParam("apiKey") String apiKey, @QueryParam("id") final Long id)

    {
        final User user = userDao.getUserByApiKey(apiKey);

        if (user == null) {
            LOG.error("USER NOT FOUND FOR API KEY:" + apiKey);
            throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
        }


        memmeeDao.delete(id);
    }


    @POST
    @Path("/uploadattachment")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(
            @QueryParam("apiKey") String apiKey,
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail) {


        final User user = userDao.getUserByApiKey(apiKey);

        if (user == null) {
            LOG.error("USER NOT FOUND FOR API KEY:" + apiKey);
            throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
        }

        String output = "";
        String uploadedFileLocation = "";

        try {

            if(OsUtil.isWindows())     {
             uploadedFileLocation = "c://memmee/temp/" + fileDetail.getFileName();
            }else if(OsUtil.isMac()){
             uploadedFileLocation = "/tmp/" + fileDetail.getFileName();
            }
            // save it
            writeToFile(uploadedInputStream, uploadedFileLocation);

            output = "File uploaded to : " + uploadedFileLocation;

            attachmentDAO.insert(null, uploadedFileLocation, "Image");
        } catch (Exception e) {
            LOG.error("ERROR UPLOADING ATTACHMENT ");
            throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
        }
        return Response.status(200).entity(output).build();
    }

    // save uploaded file to new location
    private void writeToFile(InputStream uploadedInputStream,
                             String uploadedFileLocation) {

        try {
            OutputStream out = new FileOutputStream(new File(
                    uploadedFileLocation));
            int read = 0;
            byte[] bytes = new byte[1024];

            out = new FileOutputStream(new File(uploadedFileLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {

            e.printStackTrace();
        }

    }


}
