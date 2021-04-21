package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #(credentialid)")
    public Credential getCredential(Integer credentialid);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    public Credential[] getCredentialListings(Integer userid);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES(#{url}, #{userName}, #{key}, #{password}, #{userid})")
    public Integer insert(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    public Integer deleteCredentials(Integer credentialid);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, key = #{key}, password = #{password} WHERE credentialid = #{credentialid}")
    void updateCredential(Integer credentialid, String username, String url, String key, String password);
}
