package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteTests extends CloudStorageApplicationTests {

    private void createNote(String noteTitle, String noteDescription, HomePage homePage) {
        homePage.navToNotesTab();
        homePage.addNewNote();
        homePage.setNoteTitle(noteTitle);
        homePage.setNoteDescription(noteDescription);
        homePage.saveNoteChanges();
        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();
        homePage.navToNotesTab();
    }

    private void deleteNote(HomePage homePage) {
        homePage.deleteNote();
        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();
    }

    @Test
    public void testDelete() {
        String noteTitle = "testing note";
        String noteDescription = "this is a testing note";
        HomePage homePage = signupAndLogin();
        createNote(noteTitle, noteDescription, homePage);
        homePage.navToNotesTab();
        homePage = new HomePage(driver);

        Assertions.assertFalse(homePage.noNotes(driver));
        deleteNote(homePage);
        Assertions.assertTrue(homePage.noNotes(driver));
    }

    @Test
    public void testCreateandDisplay() {
        String noteTitle = "testing create note";
        String noteDescription = "This is for testing create note";
        HomePage homePage = signupAndLogin();
        createNote(noteTitle, noteDescription, homePage);
        homePage.navToNotesTab();
        homePage = new HomePage(driver);
        Note note = homePage.getFirstNote();
        Assertions.assertEquals(noteTitle, note.getNoteTitle());
        Assertions.assertEquals(noteDescription, note.getNoteDescription());
        deleteNote(homePage);
        homePage.logout();
    }

}
