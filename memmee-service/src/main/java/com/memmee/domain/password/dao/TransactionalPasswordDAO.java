package com.memmee.domain.password.dao;

import com.memmee.domain.password.dto.Password;
import com.memmee.domain.password.dto.PasswordMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.mixins.CloseMe;
import org.skife.jdbi.v2.sqlobject.mixins.GetHandle;
import org.skife.jdbi.v2.sqlobject.mixins.Transactional;

public interface TransactionalPasswordDAO extends Transactional<TransactionalPasswordDAO>, GetHandle, CloseMe {

    @SqlQuery("select * from password where id = :id")
    @Mapper(PasswordMapper.class)
    Password getPassword(@Bind("id") Long id);

    @SqlUpdate("insert into password (value, temp) values (:value, :temp)")
    @GetGeneratedKeys
    Long insert(@Bind("value") String value, @Bind("temp") int temp);

    @SqlUpdate("update password set value = :value, temp = :temp where id = :id")
    int update(@Bind("id") Long id, @Bind("value") String value, @Bind("temp") int temp);

}
