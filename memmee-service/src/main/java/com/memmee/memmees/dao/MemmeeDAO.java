package com.memmee.memmees.dao;

import java.util.Date;
import java.util.List;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import com.memmee.memmees.dto.Memmee;
import com.memmee.memmees.dto.MemmeeAttachmentMapper;

public interface MemmeeDAO {
	
	
	
	
	   @SqlQuery("select m.id, m.userId, m.title, m.lastUpdateDate, m.creationDate, m.displayDate, m.text, m.shareKey" +
	   " a.id as attachmentId, a.filePath, a.type from memmee m " +
	   "INNER JOIN attachment a on m.id = a.memmeeID where m.id = :id")
	    @Mapper(MemmeeAttachmentMapper.class)
	    List<Memmee> getMemmees(@Bind("id") Long id);


	   @SqlUpdate("insert into memmee (id, userId, title, text, lastUpdateDate, creationDate, displayDate, shareKey)" +
	   		" values (:id, :userId, :title, :text, :lastUpdateDate, :creationDate, :displayDate, :shareKey)")
	    void insert(
	         @Bind("id") Long id
	        ,@Bind("userId") Long userId
	        ,@Bind("title") String title
	        ,@Bind("text") String text
	        ,@Bind("lastUpdateDate") Date lastUpdateDate
	        ,@Bind("creationDate") Date creationDate
	        ,@Bind("displayDate") Date displayDate
	        ,@Bind("shareKey") String shareKey
	    );
	    
	    @SqlUpdate("update memmee set title = :title, text = :text, lastUpdateDate = :lastUpdateDate, shareKey = :shareKey" +
	    		" where id = :id")
	    int update(
	        @Bind("id") Long id
	        ,@Bind("title") String title
	        ,@Bind("text") String text
	        ,@Bind("lastUpdateDate") String lastUpdateDate
	        ,@Bind("shareKey") String shareKey
	    );
	    
	    @SqlUpdate("update memmee set shareKey = :shareKey where id = :id")
	    int updateShareKey(
	        @Bind("id") Long id
	        ,@Bind("shareKey") String shareKey
	    );
	    

	    @SqlUpdate("delete from memmee where id = :id")
	    void delete(
	        @Bind("id") Long id
	    );
	    
	   
	/*
	public List<Memmee> getMemmeeTitles(Long userId,  Database db){
		
	    MemmeeMapper mapper = new MemmeeMapper();
	    Handle h = db.open();
	    AttachmentDAO attachmentDAO = db.open(AttachmentDAO.class);
	    
		List<Memmee> memmees = h.createQuery("select * from memmee where userId = :userId")
		.bind("userId", userId)
		.map(mapper)
		.list();
		
		for(Memmee memmee : memmees){
			memmee.setAttachment(attachmentDAO.getAttachment(memmee.getId()));
		}
	
		attachmentDAO.close();
		h.close();
		
		return memmees;
	}
	
	public List<Memmee> getMemmees(Long userId, Database db){
			
		    MemmeeMapper mapper = new MemmeeMapper();
		    Handle h = db.open();
		    AttachmentDAO attachmentDAO = db.open(AttachmentDAO.class);
		    
			List<Memmee> memmees = h.createQuery("select * from memmee where userId = :userId")
			.bind("userId", userId)
			.map(mapper)
			.list();
			
			
			for(Memmee memmee : memmees){
				memmee.setAttachment(attachmentDAO.getAttachment(memmee.getId()));
			}
		
			attachmentDAO.close();
			h.close();
			
			return memmees;
		}
	*/

}
