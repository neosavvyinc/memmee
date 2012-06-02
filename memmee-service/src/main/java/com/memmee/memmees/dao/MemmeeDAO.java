package com.memmee.memmees.dao;

import java.util.Date;
import java.util.List;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.mixins.Transactional;

import com.memmee.memmees.dto.Memmee;
import com.memmee.memmees.dto.MemmeeAttachmentMapper;

public interface MemmeeDAO //extends Transactional<MemmeeDAO>
{
	
	   @SqlQuery("select m.id, m.userId, m.title, m.lastUpdateDate, m.creationDate, m.displayDate, m.text, m.shareKey," +
			   " a.id as attachmentId, a.filePath, a.type, t.id as themeId, t.name, t.stylePath from memmee m " +
			   "INNER JOIN attachment a on m.id = a.memmeeId " +
			   "INNER JOIN theme t on m.themeId = t.id where m.id = :id"
			   )
	   @Mapper(MemmeeAttachmentMapper.class)
	   Memmee getMemmee(@Bind("id") Long id);
	   
	   
	   @SqlQuery("select m.id, m.userId, m.title, m.lastUpdateDate, m.creationDate, m.displayDate, m.text, m.shareKey," +
	   " a.id as attachmentId, a.filePath, a.type, t.id as themeId, t.name, t.stylePath from memmee m " +
	   "INNER JOIN attachment a on m.id = a.memmeeId " +
	   "INNER JOIN theme t on m.themeId = t.id where m.userId = :userId"
	   )
	    @Mapper(MemmeeAttachmentMapper.class)
	    List<Memmee> getMemmeesbyUser(@Bind("userId") Long userId);


	   @SqlUpdate("insert into memmee (id, userId, title, text, lastUpdateDate, creationDate, displayDate, shareKey, attachmentId, themeId)" +
	   		" values (:id, :userId, :title, :text, :lastUpdateDate, :creationDate, :displayDate, :shareKey, :attachmentId, :themeId)")
	    void insert(
	         @Bind("id") Long id
	        ,@Bind("userId") Long userId
	        ,@Bind("title") String title
	        ,@Bind("text") String text
	        ,@Bind("lastUpdateDate") Date lastUpdateDate
	        ,@Bind("creationDate") Date creationDate
	        ,@Bind("displayDate") Date displayDate
	        ,@Bind("shareKey") String shareKey
	        ,@Bind("attachmentId") Long attachmentId
	        ,@Bind("themeId") Long themeId
	    );
	    
	    @SqlUpdate("update memmee set title = :title, text = :text, lastUpdateDate = :lastUpdateDate, shareKey = :shareKey, " +
	    		"attachmentId = :attachmentId, themeId = :themeId where id = :id")
	    int update(
	        @Bind("id") Long id
	        ,@Bind("title") String title
	        ,@Bind("text") String text
	        ,@Bind("lastUpdateDate") String lastUpdateDate
	        ,@Bind("shareKey") String shareKey
	        ,@Bind("attachmentId") Long attachmentId
	        ,@Bind("themeId") Long themeId
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
