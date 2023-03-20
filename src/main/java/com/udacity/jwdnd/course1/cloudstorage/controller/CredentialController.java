package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
@RequestMapping("credential")
public class CredentialController {

	@Autowired
	private CredentialService credentialService;

	@Autowired
	private EncryptionService encryptionService;

	@Autowired
	private UserService userService;

	/**
	 * To add new/update credential for the user
	 * 
	 * @param authentication
	 * @param form
	 * @param model
	 * @return
	 */
	@PostMapping("/new-credential")
	public String addCredential(Authentication authentication, @ModelAttribute("newCredential") CredentialForm form,
			Model model) {

		try {
			User user = userService.getUser(authentication.getName());

			Integer credentialId = form.getCredentialId();

			if (credentialId == null) {
				Credential credential = new Credential();
				credential.setUrl(form.getUrl());
				credential.setUserName(form.getUserName());
				credential.setUserid(user.getUserId());

				String encryptionKey = encryptionService.generateKey();
				credential.setKey(encryptionKey);

				String encryptedPassword = encryptionService.encryptValue(form.getPassword(), encryptionKey);
				credential.setPassword(encryptedPassword);

				credentialService.addCredential(credential);

			} else {

				credentialService.updateCredential(credentialId, form.getUserName(), form.getUrl(), form.getPassword());
			}

			model.addAttribute("result", "success");
		} catch (Exception e) {
			model.addAttribute("message", "Failed to save credential");
			model.addAttribute("result", "error");
		}

		return "result";
	}

	/**
	 * To delete an existing credential
	 * 
	 * @param authentication
	 * @param credentialId
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/delete-credential/{credentialId}")
	public String deleteCredential(Authentication authentication, @PathVariable Integer credentialId, Model model) {
		try {
			credentialService.deleteCredential(credentialId);
			model.addAttribute("result", "success");
		} catch (Exception e) {
			model.addAttribute("message", "Failed to delete credential");
			model.addAttribute("result", "error");
		}
		return "result";
	}

}
