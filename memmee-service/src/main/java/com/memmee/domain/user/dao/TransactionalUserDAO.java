package com.memmee.domain.user.dao;

import com.memmee.domain.user.dto.User;
import com.memmee.domain.user.dto.UserMapper;
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

public interface TransactionalUserDAO extends Transactional<TransactionalUserDAO>, GetHandle, CloseMe {

    @SqlQuery("select u.id, u.firstName, u.email, u.passwordId, u.apiKey, u.apiDate, u.creationDate, " +
            "p.id as passwordId, p.value as passwordValue, p.temp as passwordTemp from user u " +
            "LEFT OUTER JOIN password p on u.passwordId = passwordId")
    @Mapper(UserMapper.class)
    List<User> findAll();

    @SqlQuery("select u.id, u.firstName, u.email, u.passwordId, u.apiKey, u.apiDate, u.creationDate, " +
            "p.id as passwordId, p.value as passwordValue, p.temp as passwordTemp from user u " +
            "LEFT OUTER JOIN password p on u.passwordId = passwordId " +
            "where u.id = :id")
    @Mapper(UserMapper.class)
    User getUser(@Bind("id") Long id);

    @SqlQuery("select u.id, u.firstName, u.email, u.passwordId, u.apiKey, u.apiDate, u.creationDate, " +
            "p.id as passwordId, p.value as passwordValue, p.temp as passwordTemp from user u " +
            "LEFT OUTER JOIN password p on u.passwordId = passwordId " +
            "where u.apiKey = :apiKey")
    @Mapper(UserMapper.class)
    User getUserByApiKey(@Bind("apiKey") String apiKey);

    @SqlQuery("select u.id, u.firstName, u.email, u.passwordId, u.apiKey, u.apiDate, u.creationDate, " +
            "p.id as passwordId, p.value as passwordValue, p.temp as passwordTemp from user u " +
            "LEFT OUTER JOIN password p on u.passwordId = passwordId " +
            "where u.email = :email")
    @Mapper(UserMapper.class)
    User getUserByEmail(
            @Bind("email") String email);

    @SqlQuery("select u.id, u.firstName, u.email, u.passwordId, u.apiKey, u.apiDate, u.creationDate, " +
            "p.id as passwordId, p.value as passwordValue, p.temp as passwordTemp from user u " +
            "LEFT OUTER JOIN password p on u.passwordId = passwordId " +
            "where u.email = :email AND p.value = :password")
    @Mapper(UserMapper.class)
    User loginUser(
            @Bind("email") String email,
            @Bind("password") String password);


    @SqlUpdate("insert into user (firstName, email, passwordId, apiKey, apiDate, creationDate)" +
            " values (:firstName, :email, :passwordId, :apiKey, :apiDate, :creationDate)")
    @GetGeneratedKeys
    Long insert(
            @Bind("firstName") String firstName
            , @Bind("email") String email
            , @Bind("passwordId") Long passwordId
            , @Bind("apiKey") String apiKey
            , @Bind("apiDate") Date apiDate
            , @Bind("creationDate") Date creationDate
    );

    @SqlUpdate("update user set firstName = :firstName, email = :email, passwordId = :passwordId, " +
            "apiKey = :apiKey, apiDate = :apiDate where id = :id")
    int update(
            @Bind("id") Long id
            , @Bind("firstName") String firstName
            , @Bind("email") String email
            , @Bind("passwordId") Long passwordId
            , @Bind("apiKey") String apiKey
            , @Bind("apiDate") Date apiDate
    );

    @SqlUpdate("delete from user where id = :id")
    void delete(
            @Bind("id") Long id
    );

    @SqlQuery("select count(*) from user where email = :email")
    int getUserCount(
            @Bind("email") String email
    );

    void close();
}