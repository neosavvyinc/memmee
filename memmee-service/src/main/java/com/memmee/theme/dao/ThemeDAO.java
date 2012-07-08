package com.memmee.theme.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import com.memmee.theme.dto.Theme;
import com.memmee.theme.dto.ThemeMapper;

public interface ThemeDAO {


    @SqlQuery("select * from theme where id = :id")
    @Mapper(ThemeMapper.class)
    Theme getTheme(@Bind("id") Long id);

    @SqlUpdate("insert into theme (name, stylePath) values (:name, :stylePath)")
    @GetGeneratedKeys
    Long insert(
            @Bind("name") String name
            , @Bind("stylePath") String stylePath
    );

    @SqlUpdate("update theme set stylePath = :stylePath, name = :name where id = :id")
    int update(
            @Bind("id") Long id
            , @Bind("stylePath") String stylePath
            , @Bind("name") String name
    );

    @SqlUpdate("delete from theme where id = :id")
    void delete(
            @Bind("id") Long id
    );

    void close();


}
