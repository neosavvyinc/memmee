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
import java.util.List;

public interface TransactionalInspirationDAO extends Transactional<TransactionalInspirationDAO>, GetHandle, CloseMe {

    @SqlQuery("select i.id, i.text, i.inspirationCategoryId, i.inspirationCategoryIndex, i.creationDate, i.lastUpdateDate, " +
            "ic.id as inspirationCategoryId, ic.name as inspirationCategoryName from inspiration i " +
            "LEFT OUTER JOIN inspirationcategory ic on i.inspirationCategoryId = inspirationCategoryId")
    @Mapper(InspirationMapper.class)
    List<Inspiration> getAllInspirations();

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

    @SqlQuery("select i.id, i.text, i.inspirationCategoryId, i.inspirationCategoryIndex, i.creationDate, i.lastUpdateDate, " +
            "ic.id as inspirationCategoryId, ic.name as inspirationCategoryName from inspiration i " +
            "LEFT OUTER JOIN inspirationcategory ic on i.inspirationCategoryId = inspirationCategoryId " +
            "where i.inspirationCategoryId = :inspirationCategoryId group by i.id order by inspirationCategoryIndex")
    @Mapper(InspirationMapper.class)
    List<Inspiration> getInspirationsForInspirationCategory(@Bind("inspirationCategoryId") Long inspirationCategoryId);

    @SqlQuery("select i.id, i.text, i.inspirationCategoryId, i.inspirationCategoryIndex, i.creationDate, i.lastUpdateDate, " +
            "ic.id as inspirationCategoryId, ic.name as inspirationCategoryName from inspiration i " +
            "LEFT OUTER JOIN inspirationcategory ic on i.inspirationCategoryId = inspirationCategoryId " +
            "where i.inspirationCategoryId = :inspirationCategoryId and i.inspirationCategoryIndex = :inspirationCategoryIndex")
    @Mapper(InspirationMapper.class)
    Inspiration getInspirationForInspirationCategoryAndIndex(@Bind("inspirationCategoryId") Long inspirationCategoryId, @Bind("inspirationCategoryIndex") Long inspirationCategoryIndex);

    @SqlQuery("select i.id, i.text, i.inspirationCategoryId, i.inspirationCategoryIndex, i.creationDate, i.lastUpdateDate, " +
            "ic.id as inspirationCategoryId, ic.name as inspirationCategoryName from inspiration i " +
            "LEFT OUTER JOIN inspirationcategory ic on i.inspirationCategoryId = ic.id " +
            "where i.inspirationCategoryId = :inspirationCategoryId and i.inspirationCategoryIndex = (select MAX(inspirationCategoryIndex) " +
            "from inspiration inneric where inneric.inspirationCategoryId = :inspirationCategoryId)")
    @Mapper(InspirationMapper.class)
    Inspiration getHighestInspirationForCategory(@Bind("inspirationCategoryId") Long inspirationCategoryId);

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
