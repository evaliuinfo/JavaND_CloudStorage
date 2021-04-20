package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NotesController {
    @Autowired
    private NoteService noteService;

    @PostMapping("/notes")
    public String createOrUpdateNote(Authentication authentication, Notes note) {
        User user = (User) authentication.getPrincipal();
        if (note.getNoteId() > 0) {
            noteService.updateNote(note);
        } else {
            noteService.addNote(note, user.getUserId());
        }
        return "redirect:/result?success";
    }

    @GetMapping("/notes/delete")
    public String deleteNote(@RequestParam("id") Integer noteid) {
        if (noteid > 0) {
            noteService.deleteNote(noteid);
            return "redirect:/result?success";
        }
        return "redirect:/result?error";
    }
}
