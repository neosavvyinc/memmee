package com.memmee.memmees.dao;

import java.util.Date;
import java.util.List;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.mixins.Transactional;

import com.memmee.memmees.dto.Memmee;
import com.memmee.memmees.dto.MemmeeAttachmentMapper;
import com.memmee.memmees.dto.MemmeeMapper;
 
public interface MemmeeDAO //extends Transactional<MemmeeDAO>
{
	
	
	@SqlQuery("select * from memmee where id = :id")
    @Mapper(MemmeeMapper.class)
    Memmee getMemmeeNoAttachment(@Bind("id") Long id);
	
	   @SqlQuery("select m.id, m.userId, m.title, m.lastUpdateDate, m.creationDate, m.displayDate, m.text, m.shareKey," +
			   " a.id as attachmentId, a.filePath, a.type from memmee m " +
			   "LEFT OUTER JOIN attachment a on m.id = a.memmeeId where m.id = :id"
			   )
	   @Mapper(MemmeeAttachmentMapper.class)
	   Memmee getMemmee(@Bind("id") Long id);
	   
	   
	   @SqlQuery("select m.id, m.userId, m.title, m.lastUpdateDate, m.creationDate, m.displayDate, m.text, m.shareKey," +
	   " a.id as attachmentId, a.filePath, a.type from memmee m " +
	   "LEFT OUTER JOIN attachment a on m.id = a.memmeeId where m.userId = :userId"
	   )
	    @Mapper(MemmeeAttachmentMapper.class)
	    List<Memmee> getMemmeesbyUser(@Bind("userId") Long userId);  
	   
	   @SqlUpdate("insert into memmee (userId, title, text, lastUpdateDate, creationDate, displayDate, shareKey, attachmentId, themeId)" +
	   		" values (:userId, :title, :text, :lastUpdateDate, :creationDate, :displayDate, :shareKey, :attachmentId, :themeId)")
	   @GetGeneratedKeys
	   Long insert(
			 @Bind("userId") Long userId
	        ,@Bind("title") String title
	        ,@Bind("text") String text
	        ,@Bind("lastUpdateDate") Date lastUpdateDate
	        ,@Bind("creationDate") Date creationDate
	        ,@Bind("displayDate") Date displayDate
	        ,@Bind("shareKey") String shareKey
	        ,@Bind("attachmentId") Long attachmentId
	        ,@Bind("themeId") Long themeId
	    );
	    
	    @SqlUpdate("update memmee set title = :title, text = :text, lastUpdateDate = :lastUpdateDate, displayDate = :displayDate, shareKey = :shareKey, " +
	    		"attachmentId = :attachmentId, themeId = :themeId where id = :id")
	    int update(
	        @Bind("id") Long id
	        ,@Bind("title") String title
	        ,@Bind("text") String text
	        ,@Bind("lastUpdateDate") Date lastUpdateDate
	        ,@Bind("displayDate") Date displayDate
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

	    void close();
}
