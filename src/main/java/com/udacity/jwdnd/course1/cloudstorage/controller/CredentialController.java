package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Base64;

@Controller()
@RequestMapping("credential")
public class CredentialController {

    @Autowired
    private CredentialService credentialService;

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private UserService userService;

    public CredentialController(CredentialService credentialService, EncryptionService encryptionService, UserService userService) {
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
        this.userService = userService;
    }

    @GetMapping
    public String getHomePage(
            Authentication authentication,
            @ModelAttribute("newFile") FileForm newFile,
            @ModelAttribute("newNote") NoteForm newNote,
            @ModelAttribute("newCredential") CredentialForm newCredential,
            Model model) {
        String userName = authentication.getName();
        User user = userService.getUser(userName);
        model.addAttribute("credentials", this.credentialService.getCredentialListings(user.getUserId()));
        model.addAttribute("encryptionService", encryptionService);
        return "home";
    }

    @PostMapping("add-credential")
    public String newCredential(
            Authentication authentication,
            @ModelAttribute("newFile") FileForm newFile,
            @ModelAttribute("newNote") NoteForm newNote,
            @ModelAttribute("newCredential") CredentialForm newCredential,
            Model model) {
        String userName = authentication.getName();
        String newUrl = newCredential.getUrl();
        String credentialIdStr = newCredential.getCredentialId();
        String password = newCredential.getPassword();

        SecureRandom random  = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(password, encodedKey);
        if (credentialIdStr.isEmpty()) {
            credentialService.addCredential(newUrl, userName, newCredential.getUserName(), encodedKey, encryptedPassword);
        } else {
            Credential existingCredential = getCredential(Integer.parseInt(credentialIdStr));
            credentialService.updateCredential(existingCredential.getCredentialid(), newCredential.getUserName(), newUrl, encodedKey, encryptedPassword);
        }
        System.out.println("CredentialController URL: " + newUrl);
        System.out.println("CredentialController new credential username " + newCredential.getUserName());
        System.out.println("CredentialController username: " + userName);
        System.out.println("CredentialController password: " + encryptedPassword);
        System.out.println("CredentialController key: " + encodedKey);


        User user = userService.getUser(userName);
        model.addAttribute("credentials", credentialService.getCredentialListings(user.getUserId()));
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("result", "success");
        return "result";
    }

    @GetMapping(value = "/get-credential/{credentialId}")
    public Credential getCredential(@PathVariable Integer credentialId) {
        return credentialService.getCredential(credentialId);
    }

    @GetMapping(value = "/delete-credential/{credentialId}")
    public String deleteCredential(
            Authentication authentication, @PathVariable Integer credentialId,
            @ModelAttribute("newFile") FileForm newFile,
            @ModelAttribute("newNote") NoteForm newNote,
            @ModelAttribute("newCredential") CredentialForm newCredential,
            Model model) {
        credentialService.deleteCredential(credentialId);
        String userName = authentication.getName();
        User user = userService.getUser(userName);
        model.addAttribute("credentials", credentialService.getCredentialListings(user.getUserId()));
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("result", "success");
        return "result";
    }
}
