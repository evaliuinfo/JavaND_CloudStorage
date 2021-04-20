package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FileMapper {
    @Select("SELECT * FROM FILES")
    List<Files> findAll();

    @Select("SELECT * FROM FILES WHERE fileid = #{fileid}")
    public Files findFile(Integer fileid);

    @Select("SELECT * FROM FILES WHERE filename = #{filename}")
    public Files getFile(String filename);

    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    public List<Files> findByUserId(Integer userid);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{file.filename}, #{file.contenttype}, #{file.filesize}, #{userid}, #{file.filedata})")
    public int insertFile(Files file, Integer userid);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileid}")
    public int deleteFile(Integer fileid);
}
