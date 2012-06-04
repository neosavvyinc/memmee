package com.memmee.user.dao;

import com.memmee.user.dto.User;
import com.memmee.user.dto.UserMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import java.util.Date;
import java.util.List;

public interface UserDAO {

    @SqlQuery("select * from user")
    @Mapper(UserMapper.class)
    List<User> findAll();
    
	@SqlQuery("select * from user where id = :id")
    @Mapper(UserMapper.class)
    User getUser(@Bind("id") Long id);
	
	@SqlQuery("select * from user where apiKey = :apiKey")
    @Mapper(UserMapper.class)
    User getUserByApiKey(@Bind("apiKey") Long apiKey);
	
	@SqlQuery("select * from user where email = :email and password = :password")
    @Mapper(UserMapper.class)
    User loginUser(
    	@Bind("email") String email,
    	@Bind("password") String password);

    @SqlUpdate("insert into user (id, firstName, lastName, email, password, apiKey, apiDate, creationDate)" +
    		" values (:id, :firstName, :lastName, :email, :password, :apiKey, :apiDate, :creationDate)")
    void insert(
        @Bind("id") Long id
        ,@Bind("firstName") String firstName
        ,@Bind("lastName") String lastName
        ,@Bind("email") String email
        ,@Bind("password") String password
        ,@Bind("apiKey") String apiKey
        ,@Bind("apiDate") Date apiDate
        ,@Bind("creationDate") Date creationDate
    );

    @SqlUpdate("update user set firstName = :firstName, lastName = :lastName, email = :email, password = :password, " +
    		"apiKey = :apiKey, apiDate = :apiDate where id = :id")
    int update(
        @Bind("id") Long id
        ,@Bind("firstName") String firstName
        ,@Bind("lastName") String lastName
        ,@Bind("email") String email
        ,@Bind("password") String password
        ,@Bind("apiKey") String apiKey
        ,@Bind("apiDate") Date apiDate
    );

    @SqlUpdate("delete from user where id = :id")
    void delete(
        @Bind("id") Long id
    );

    void close();
}