package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;
    private final UserMapper userMapper;

    public CredentialService(UserMapper userMapper, CredentialMapper credentialMapper) {
        this.userMapper = userMapper;
        this.credentialMapper = credentialMapper;
    }

    public void addCredential(String url, String userName, String credentialUserName, String key, String password) {
        Integer userId = userMapper.getUser(userName).getUserId();
        Credential credential = new Credential(0, url, credentialUserName, key, password, userId);
        credentialMapper.insert(credential);
    }

    public Credential[] getCredentialListings(Integer userId) {
        return credentialMapper.getCredentialListings(userId);
    }

    public Credential getCredential(Integer credentialId) {
        return credentialMapper.getCredential(credentialId);
    }

    public void deleteCredential(Integer credentialid) {
        credentialMapper.deleteCredentials(credentialid);
    }

    public void updateCredential(Integer credentialId, String userName, String url, String key, String password) {
        credentialMapper.updateCredential(credentialId, userName, url, key, password);
    }
}
