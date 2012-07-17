package com.memmee.attachment.dao;

import com.memmee.attachment.dto.Attachment;
import com.memmee.attachment.dto.AttachmentMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

public interface AttachmentDAO {

    @SqlQuery("select * from attachment where id = :id")
    @Mapper(AttachmentMapper.class)
    Attachment getAttachment(@Bind("id") Long id);

    @SqlQuery("select * from attachment where memmeeId = :memmeeId")
    @Mapper(AttachmentMapper.class)
    Attachment getAttachmentByMemmeeId(@Bind("memmeeId") Long memmeeId);

    @SqlUpdate("insert into attachment (memmeeId, filePath, thumbFilePath, type) values (:memmeeId, :filePath, :thumbFilePath, :type)")
    @GetGeneratedKeys
    Long insert(
            @Bind("memmeeId") Long memmeeId
            , @Bind("filePath") String filePath
            , @Bind("thumbFilePath") String thumbFilePath
            , @Bind("type") String type
    );

    @SqlUpdate("update attachment set filePath = :filePath, thumbFilePath = :thumbFilePath, type = :type where id = :id")
    int update(
            @Bind("id") Long id
            , @Bind("filePath") String filePath
            , @Bind("thumbFilePath") String thumbFilePath
            , @Bind("type") String type
    );

    @SqlUpdate("delete from attachment where id = :id")
    void delete(
            @Bind("id") Long id
    );

    void close();

}
