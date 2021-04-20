package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CredentialService {
    @Autowired
    private CredentialMapper credentialMapper;

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private UserMapper userMapper;

    public CredentialService(UserMapper userMapper, CredentialMapper credentialMapper) {
        this.userMapper= userMapper;
    }

    private Credentials encryptPassword(Credentials credential) {
        SecureRandom random = new SecureRandom();
        String key = random.toString();
        credential.setKey(key);
        credential.setPassword(encryptionService.encryptValue(credential.getPassword(), key));
        return credential;
    }

    public Credentials decryptPassword(Credentials credential) {
        credential.setPassword(encryptionService.decryptValue(credential.getPassword(), credential.getKey()));
        return credential;
    }

    public List<Credentials> getAllCredentials(Integer userid) throws Exception {
        List<Credentials> credentials = credentialMapper.findByUserId(userid);
        if (credentials == null) {
            throw new Exception();
        }
        return credentials.stream().map(this::decryptPassword).collect(Collectors.toList());
    }

    public void addCredential(Credentials credential, Integer userid) {
        credentialMapper.insertCredentials(encryptPassword(credential), userid);
    }

    public void updateCredential(Credentials credential) {
        credentialMapper.updateCredentials(encryptPassword(credential));
    }

    public void deleteCredential(Integer credentialid) {
        credentialMapper.deleteCredentials(credentialid);
    }
}
