package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CredentialsController {
    @Autowired
    private CredentialService credentialService;

    @PostMapping("/credentials")
    public String saveOrUpdateCredentials(Authentication authentication, Credentials credential) {
        User user = (User) authentication.getPrincipal();
        if (credential.getCredentialid() > 0) {
            credentialService.updateCredential(credential);
        } else {
            credentialService.addCredential(credential, user.getUserId());
        }
        return "redirect:/result?success";
    }

    @GetMapping("/credentials/delete")
    public String deleteNote(@RequestParam("id") Integer credentialid) {
        if (credentialid > 0) {
            credentialService.deleteCredential(credentialid);
            return "redirect:/result?success";
        }
        return "redirect:/result?error";
    }
}
