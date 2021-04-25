package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CredentialService {
    @Autowired
    private CredentialMapper credentialMapper;

    @Autowired
    private UserMapper userMapper;

    public CredentialService(UserMapper userMapper, CredentialMapper credentialMapper) {
        this.userMapper = userMapper;
        this.credentialMapper = credentialMapper;
    }

    public void addCredential(String url, String userName, String credentialUserName, String key, String password) {
        Integer userId = userMapper.getUser(userName).getUserId();
        Credential credential = new Credential(0, url, credentialUserName, key, password, userId);
        credentialMapper.insert(credential);
        System.out.println("CredentialService user id: " + userId);
        System.out.println("CredentialService URL: " + url);
        System.out.println("CredentialService new credential username " + credentialUserName);
        System.out.println("CredentialService username: " + userName);
        System.out.println("CredentialService password: " + password);
        System.out.println("CredentialService key: " + key);
    }

    public Credential[] getCredentialListings(Integer userId) {
        System.out.println("Credential listing is: " + credentialMapper.getCredentialListings(userId));
        return credentialMapper.getCredentialListings(userId);
    }

    public Credential getCredential(Integer credentialId) {
        System.out.println("Get Credential key is: " + credentialMapper.getCredential(credentialId).getKey());
        return credentialMapper.getCredential(credentialId);
    }

    public void deleteCredential(Integer credentialid) {
        credentialMapper.deleteCredentials(credentialid);
    }

    public void updateCredential(Integer credentialId, String userName, String url, String key, String password) {
        credentialMapper.updateCredential(credentialId, userName, url, key, password);
    }
}
