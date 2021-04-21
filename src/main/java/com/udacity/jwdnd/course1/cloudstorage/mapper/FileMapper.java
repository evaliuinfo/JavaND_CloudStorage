package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES")
    List<File> findAll();

    @Select("SELECT * FROM FILES WHERE filename = #{fileName}")
    public File getFile(String fileName);

    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    String[] getFileListings(Integer userId);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    public int insert(File file);

    @Delete("DELETE FROM FILES WHERE filename = #{fileName}")
    public int deleteFile(String fileName);
}
