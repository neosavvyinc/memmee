package com.memmee.domain.attachment.dao;

import com.memmee.domain.attachment.dto.Attachment;
import com.memmee.domain.attachment.dto.AttachmentMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.mixins.CloseMe;
import org.skife.jdbi.v2.sqlobject.mixins.GetHandle;
import org.skife.jdbi.v2.sqlobject.mixins.Transactional;

public interface TransactionalAttachmentDAO extends Transactional<TransactionalAttachmentDAO>, GetHandle, CloseMe {

    @SqlQuery("select * from attachment where id = :id")
    @Mapper(AttachmentMapper.class)
    Attachment getAttachment(@Bind("id") Long id);

    @SqlUpdate("insert into attachment (filePath, thumbFilePath, type) values (:filePath, :thumbFilePath, :type)")
    @GetGeneratedKeys
    long insert(
            @Bind("filePath") String filePath
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
