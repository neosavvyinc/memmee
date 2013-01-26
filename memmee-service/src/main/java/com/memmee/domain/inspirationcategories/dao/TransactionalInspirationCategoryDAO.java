package com.memmee.domain.inspirationcategories.dao;

import com.memmee.domain.inspirationcategories.domain.InspirationCategory;
import com.memmee.domain.inspirationcategories.domain.InspirationCategoryMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.mixins.CloseMe;
import org.skife.jdbi.v2.sqlobject.mixins.GetHandle;
import org.skife.jdbi.v2.sqlobject.mixins.Transactional;

import java.util.List;

public interface TransactionalInspirationCategoryDAO extends Transactional<TransactionalInspirationCategoryDAO>, GetHandle, CloseMe {

    @SqlQuery("select * from inspirationcategory")
    @Mapper(InspirationCategoryMapper.class)
    List<InspirationCategory> getAllInspirations();

    @SqlQuery("select * from inspirationcategory where id = :id")
    @Mapper(InspirationCategoryMapper.class)
    InspirationCategory getInspirationCategory(@Bind("id") Long id);

    @SqlQuery("select * from inspirationcategory where idx = :idx")
    @Mapper(InspirationCategoryMapper.class)
    InspirationCategory getInspirationCategoryByIndex(@Bind("idx") Long index);

    @SqlQuery("select * from inspirationcategory where idx = (select MAX(idx) from inspirationcategory)")
    @Mapper(InspirationCategoryMapper.class)
    InspirationCategory getHighestInspirationCategory();

    @SqlUpdate("insert into inspirationcategory (idx, name) values (:idx, :name)")
    @GetGeneratedKeys
    Long insert(@Bind("idx") Long index, @Bind("name") String name);

    void close();
}
