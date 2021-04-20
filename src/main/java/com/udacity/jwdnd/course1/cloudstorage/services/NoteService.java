package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NoteMapper noteMapper;

    public NoteService(UserMapper userMapper, NoteMapper noteMapper) {
        this.userMapper = userMapper;
        this.noteMapper = noteMapper;
    }

    public void addNote(String title, String description, String username) {
        Integer userId = userMapper.findByUsername(username).getUserId();
        Notes note = new Notes(0, title, description, userId);
        noteMapper.insertNote(note, userId);
    }

    public List<Notes> getNoteLists(Integer userId) {
        return noteMapper.findByUserId(userId);
    }

    public void addNote(Notes note, Integer userid) {
        noteMapper.insertNote(note, userid);
    }

    public void updateNote(Notes note) {
        noteMapper.updateNote(note);
    }

    public void deleteNote(Integer noteid) {
        noteMapper.deleteNote(noteid);
    }
}
