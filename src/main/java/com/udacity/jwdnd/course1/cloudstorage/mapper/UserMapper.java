package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    @Select("SELECT * FROM USERS")
    List<User> findAll();

    @Select("SELECT * FROM USERS WHERE username = #{username}")
    public User findByUsername(String username);

    @Select("SELECT * FROM USERS WHERE userid = #{userid}")
    public User findByUserid(Integer userid);

    @Insert("INSERT INTO USERS (username, salt, password, firstname, lastname) VALUES(#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    public int insert(User user);

    @Insert("INSERT INTO USERS (username, password, salt, firstname, lastname) VALUES (#{username}, #{password}, #{salt}, #{firstname}, #{lastname})")
    public int insertUser(User user);

    @Delete("DELETE FROM USERS WHERE username = #{username}")
    public Integer deleteUser(String username);
}