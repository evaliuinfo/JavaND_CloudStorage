package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NoteMapper {
    @Select("SELECT * FROM NOTES")
    List<Notes> findAll();

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteid}")
    public Notes findNote(Integer noteid);

    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    public List<Notes> findByUserId(Integer userid);

    @Select("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES (#{note.notetitle}, #{note.notedescription}, #{userid})")
    public Integer insertNote(Notes note, Integer userid);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
    public Integer deleteNote(Integer noteid);

    @Update("UPDATE NOTES SET notetitle = #{note.notetitle}, notedescription = #{note.notedescription}, WHERE noteid = #{note.noteid}")
    public Integer updateNote(Notes note);
}
