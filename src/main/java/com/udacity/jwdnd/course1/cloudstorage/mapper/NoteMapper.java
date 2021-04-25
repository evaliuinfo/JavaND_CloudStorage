package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    Note[] getNotesForUser(Integer userId);

    @Select("SELECT * FROM NOTES")
    Note[] getNoteListings();

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) " +
            "VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(Note note);

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteId}")
    Note getNote(Integer noteId);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
    void deleteNote(Integer noteid);

    @Update("UPDATE NOTES SET notetitle = #{title}, notedescription = #{description} WHERE noteid = #{noteId}")
    void updateNote(Integer noteId, String title, String description);
}
