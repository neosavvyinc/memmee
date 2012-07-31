package com.memmee.domain.inspirations.dao;

import com.memmee.domain.inspirations.dto.Inspiration;
import com.memmee.domain.inspirations.dto.InspirationMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.mixins.CloseMe;
import org.skife.jdbi.v2.sqlobject.mixins.GetHandle;
import org.skife.jdbi.v2.sqlobject.mixins.Transactional;

import java.util.Date;

public interface TransactionalInspirationDAO extends Transactional<TransactionalInspirationDAO>, GetHandle, CloseMe {

    @SqlQuery("select * from inspiration where id = :id")
    @Mapper(InspirationMapper.class)
    Inspiration getInspiration(@Bind("id") Long id);

    @SqlQuery("select * from inspiration order by rand() limit 1")
    @Mapper(InspirationMapper.class)
    Inspiration getRandomInspiration();

    @SqlQuery("select * from inspiration where id <> :excludeId order by rand() limit 1")
    @Mapper(InspirationMapper.class)
    Inspiration getRandomInspiration(@Bind("excludeId") Long excludeId);

    @SqlUpdate("insert into inspiration (text, creationDate, lastUpdateDate) " +
        "values (:text, :creationDate, :lastUpdateDate)")
    @GetGeneratedKeys
    Long insert(
            @Bind("text") String text,
            @Bind("creationDate") Date creationDate,
            @Bind("lastUpdateDate") Date lastUpdateDate
    );

    void close();
}
