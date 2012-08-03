package com.memmee.domain.password.dao;

import com.memmee.domain.password.dto.PasswordMapper;
import com.memmee.domain.user.dto.User;
import com.memmee.domain.user.dto.UserMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.mixins.CloseMe;
import org.skife.jdbi.v2.sqlobject.mixins.GetHandle;
import org.skife.jdbi.v2.sqlobject.mixins.Transactional;

public interface TransactionalPasswordDAO extends Transactional<TransactionalPasswordDAO>, GetHandle, CloseMe {

    @SqlQuery("select * from password where id = :id")
    @Mapper(PasswordMapper.class)
    PasswordMapper getPassword(@Bind("id") Long id);

    @SqlUpdate("insert into password (value, temp) values (:value, :temp)")
    Long insert(@Bind("value") String value, @Bind("temp") int temp);

    @SqlUpdate("update password set value = :value, temp = :temp where id = :id")
    Long update(@Bind("id") Long id, @Bind("value") String value, @Bind("temp") int temp);

}
