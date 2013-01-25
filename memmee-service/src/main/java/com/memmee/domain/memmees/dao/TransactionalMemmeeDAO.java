package com.memmee.domain.memmees.dao;

import java.util.Date;
import java.util.List;

import com.memmee.domain.memmees.dto.MemmeeAttachmentInspirationMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.mixins.CloseMe;
import org.skife.jdbi.v2.sqlobject.mixins.GetHandle;
import org.skife.jdbi.v2.sqlobject.mixins.Transactional;

import com.memmee.domain.memmees.dto.Memmee;
import com.memmee.domain.memmees.dto.MemmeeMapper;

public interface TransactionalMemmeeDAO extends Transactional<TransactionalMemmeeDAO>, GetHandle, CloseMe {


    @SqlQuery("select * from memmee where id = :id")
    @Mapper(MemmeeMapper.class)
    Memmee getMemmeeMin(@Bind("id") Long id);

    @SqlQuery("select m.id, m.userId, m.lastUpdateDate, m.creationDate, m.displayDate, m.text, m.shareKey, m.shortenedUrl, m.facebookUrl," +
            "a.id as attachmentId, a.filePath, a.thumbFilePath, a.type, " +
            "i.id as inspirationId, i.text as inspirationText, i.creationDate as inspirationCreationDate," +
            "i.lastUpdateDate as inspirationLastUpdateDate, t.id as themeId, " +
            "t.name as themeName, t.listName as themeListName, t.stylePath as themeStylePath from memmee m " +
            "LEFT OUTER JOIN attachment a on m.attachmentId = a.id " +
            "LEFT OUTER JOIN inspiration i on m.inspirationId = i.id " +
            "LEFT OUTER JOIN theme t on m.themeId = t.id " +
            "where m.id = :id"
    )
    @Mapper(MemmeeAttachmentInspirationMapper.class)
    Memmee getMemmee(@Bind("id") Long id);

    @SqlQuery("select m.id, m.userId, m.lastUpdateDate, m.creationDate, m.displayDate, m.text, m.shareKey, m.shortenedUrl, m.facebookUrl," +
            "a.id as attachmentId, a.filePath, a.thumbFilePath, a.type, " +
            "i.id as inspirationId, i.text as inspirationText, i.creationDate as inspirationCreationDate," +
            "i.lastUpdateDate as inspirationLastUpdateDate, t.id as themeId, " +
            "t.name as themeName, t.listName as themeListName, t.stylePath as themeStylePath from memmee m " +
            "LEFT OUTER JOIN attachment a on m.attachmentId = a.id " +
            "LEFT OUTER JOIN inspiration i on m.inspirationId = i.id " +
            "LEFT OUTER JOIN theme t on m.themeId = t.id " +
            "where m.shareKey = :shareKey"
    )
    @Mapper(MemmeeAttachmentInspirationMapper.class)
    Memmee getMemmee(@Bind("shareKey") String shareKey);

    @SqlQuery("select m.id, m.userId, m.lastUpdateDate, m.creationDate, m.displayDate, m.text, m.shareKey, m.shortenedUrl, m.facebookUrl," +
            "a.id as attachmentId, a.filePath, a.thumbFilePath, a.type, " +
            "i.id as inspirationId, i.text as inspirationText, i.creationDate as inspirationCreationDate," +
            "i.lastUpdateDate as inspirationLastUpdateDate, t.id as themeId, t.name as themeName, " +
            "t.listName as themeListName, t.stylePath as themeStylePath from memmee m " +
            "LEFT OUTER JOIN attachment a on m.attachmentId = a.id " +
            "LEFT OUTER JOIN inspiration i on m.inspirationId = i.id " +
            "LEFT OUTER JOIN theme t on m.themeId = t.id " +
            "where m.userId = :userId " +
            "ORDER BY m.displayDate DESC"
    )
    @Mapper(MemmeeAttachmentInspirationMapper.class)
    List<Memmee> getMemmeesbyUser(@Bind("userId") Long userId);

    @SqlUpdate("insert into memmee (userId, text, lastUpdateDate, creationDate, displayDate, shareKey, attachmentId, themeId, inspirationId)" +
            " values (:userId, :text, :lastUpdateDate, :creationDate, :displayDate, :shareKey, :attachmentId, :themeId, :inspirationId)")
    @GetGeneratedKeys
    Long insert(
            @Bind("userId") Long userId
            , @Bind("text") String text
            , @Bind("lastUpdateDate") Date lastUpdateDate
            , @Bind("creationDate") Date creationDate
            , @Bind("displayDate") Date displayDate
            , @Bind("shareKey") String shareKey
            , @Bind("attachmentId") Long attachmentId
            , @Bind("themeId") Long themeId
            , @Bind("inspirationId") Long inspirationId
    );

    @SqlUpdate("update memmee set text = :text, lastUpdateDate = :lastUpdateDate, displayDate = :displayDate, shareKey = :shareKey, " +
            "attachmentId = :attachmentId, themeId = :themeId where id = :id")
    int update(
            @Bind("id") Long id
            , @Bind("text") String text
            , @Bind("lastUpdateDate") Date lastUpdateDate
            , @Bind("displayDate") Date displayDate
            , @Bind("shareKey") String shareKey
            , @Bind("attachmentId") Long attachmentId
            , @Bind("themeId") Long themeId
    );

    @SqlUpdate("update memmee set shareKey = :shareKey where id = :id")
    int updateShareKey(
            @Bind("id") Long id
            , @Bind("shareKey") String shareKey
    );

    @SqlUpdate("update memmee set shortenedUrl = :shortenedUrl, facebookUrl = :facebookUrl where id = :id")
    int updateShortenedUrl(
            @Bind("id") Long id
            , @Bind("shortenedUrl") String shortenedUrl
            , @Bind("facebookUrl") String facebookUrl
    );

    @SqlUpdate("delete from memmee where id = :id")
    void delete(
            @Bind("id") Long id
    );

    @SqlUpdate("insert into attachment (memmeeId, filePath, type) values (:memmeeId, :filePath, :type)")
    @GetGeneratedKeys
    Long insertAttachment(
            @Bind("memmeeId") Long memmeeId
            , @Bind("filePath") String filePath
            , @Bind("type") String type
    );

    @SqlUpdate("update attachment set filePath = :filePath, type = :type where id = :id")
    int updateAttachment(
            @Bind("id") Long id
            , @Bind("filePath") String filePath
            , @Bind("type") String type
    );

    @SqlUpdate("delete from attachment where id = :id")
    void deleteAttachment(
            @Bind("id") Long id
    );

    void close();
}
