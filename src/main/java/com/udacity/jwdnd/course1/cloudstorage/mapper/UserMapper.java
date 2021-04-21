package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM USERS")
    User[] findAll();

    @Select("SELECT * FROM USERS WHERE username = #{username}")
    public User getUser(String username);

    @Select("SELECT * FROM USERS WHERE userid = #{userId}")
    public User getUserById(Integer userId);

    @Insert("INSERT INTO USERS (username, salt, password, firstname, lastname) VALUES(#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    public int insert(User user);

    @Delete("DELETE FROM USERS WHERE username = #{username}")
    public Integer deleteUser(String username);
}