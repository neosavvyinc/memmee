package com.memmee.user.dao;

import com.memmee.user.dto.User;
import com.memmee.user.dto.UserMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: waparrish
 * Date: 5/2/12
 * Time: 2:08 PM
 * To change this template use File | Settings | File Templates.
 */
public interface UserDAO {

    @SqlQuery("select * from user")
    @Mapper(UserMapper.class)
    List<User> findAll();

    @SqlUpdate("insert into user (id, firstName, lastName, email, apiKey, apiDate, creationDate)" +
    		" values (:id, :firstName, :lastName, :email, :apiKey, :apiDate, :creationDate)")
    void insert(
        @Bind("id") Long id
        ,@Bind("firstName") String firstName
        ,@Bind("lastName") String lastName
        ,@Bind("email") String email
        ,@Bind("apiKey") String apiKey
        ,@Bind("apiDate") Date apiDate
        ,@Bind("creationDate") Date creationDate
    );

    @SqlUpdate("update user set firstName = :firstName, lastName = :lastName, email = :email, apiKey = :apiKey, apiDate = :apiDate" +
    		" where id = :id")
    int update(
        @Bind("id") Long id
        ,@Bind("firstName") String firstName
        ,@Bind("lastName") String lastName
        ,@Bind("email") String email
        ,@Bind("apiKey") String apiKey
        ,@Bind("apiDate") Date apiDate
    );

    @SqlUpdate("delete from user where id = :id")
    void delete(
        @Bind("id") Long id
    );

    void close();
}
