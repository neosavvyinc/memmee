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

    @SqlQuery("select i.id, i.text, i.inspirationCategoryId, i.inspirationCategoryIndex, i.creationDate, i.lastUpdateDate, " +
            "ic.id as inspirationCategoryId, ic.name as inspirationCategoryName from inspiration i " +
            "LEFT OUTER JOIN inspirationcategory ic on i.inspirationCategoryId = inspirationCategoryId " +
            "where i.id = :id")
    @Mapper(InspirationMapper.class)
    Inspiration getInspiration(@Bind("id") Long id);

    @SqlQuery("select i.id, i.text, i.inspirationCategoryId, i.inspirationCategoryIndex, i.creationDate, i.lastUpdateDate, " +
            "ic.id as inspirationCategoryId, ic.name as inspirationCategoryName from inspiration i " +
            "LEFT OUTER JOIN inspirationcategory ic on i.inspirationCategoryId = inspirationCategoryId " +
            "order by rand() limit 1")
    @Mapper(InspirationMapper.class)
    Inspiration getRandomInspiration();

    @SqlQuery("select i.id, i.text, i.inspirationCategoryId, i.inspirationCategoryIndex, i.creationDate, i.lastUpdateDate, " +
            "ic.id as inspirationCategoryId, ic.name as inspirationCategoryName from inspiration i " +
            "LEFT OUTER JOIN inspirationcategory ic on i.inspirationCategoryId = inspirationCategoryId " +
            "where i.id <> :excludeId order by rand() limit 1")
    @Mapper(InspirationMapper.class)
    Inspiration getRandomInspiration(@Bind("excludeId") Long excludeId);

    @SqlUpdate("insert into inspiration (text, inspirationCategoryId, inspirationCategoryIndex, creationDate, lastUpdateDate) " +
            "values (:text, :inspirationCategoryId, :inspirationCategoryIndex, :creationDate, :lastUpdateDate)")
    @GetGeneratedKeys
    Long insert(
            @Bind("text") String text,
            @Bind("inspirationCategoryId") Long inspirationCategoryId,
            @Bind("inspirationCategoryIndex") Long inspirationCategoryIndex,
            @Bind("creationDate") Date creationDate,
            @Bind("lastUpdateDate") Date lastUpdateDate
    );

    void close();
}
