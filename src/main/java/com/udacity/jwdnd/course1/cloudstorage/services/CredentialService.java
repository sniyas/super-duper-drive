package com.udacity.jwdnd.course1.cloudstorage.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;

@Service
public class CredentialService {

	@Autowired
	private CredentialMapper credentialMapper;
	@Autowired
	private EncryptionService encryptionService;

	/**
	 * Fetches all credentials for a user
	 * 
	 * @param userId
	 * @return
	 */
	public List<Credential> getUserCredentials(Integer userId) {
		return credentialMapper.getUserCredentials(userId);
	}

	/**
	 * Adds a new credential
	 * 
	 * @param credential
	 * @return
	 */
	public Integer addCredential(Credential credential) {
		return credentialMapper.insert(credential);
	}

	/**
	 * Deletes a credential
	 * 
	 * @param credentialId
	 */
	public void deleteCredential(Integer credentialId) {
		credentialMapper.deleteCredential(credentialId);
	}

	/**
	 * Updates an existing credential
	 * 
	 * @param credentialId
	 * @param userName
	 * @param url
	 * @param password
	 */
	public void updateCredential(Integer credentialId, String userName, String url, String password) {
		Credential credential = credentialMapper.getCredential(credentialId);
		String encryptionKey = credential.getKey();
		String encryptedPassword = encryptionService.encryptValue(password, encryptionKey);

		credentialMapper.updateCredential(credentialId, userName, url, encryptionKey, encryptedPassword);
	}
}
