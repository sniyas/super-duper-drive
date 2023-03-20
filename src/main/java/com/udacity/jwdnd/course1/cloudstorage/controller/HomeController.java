package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
@RequestMapping("/home")
public class HomeController {

	@Autowired
	private FileService fileService;
	@Autowired
	private UserService userService;
	@Autowired
	private NoteService noteService;
	@Autowired
	private CredentialService credentialService;
	@Autowired
	private EncryptionService encryptionService;

	/**
	 * To load home page with all the tabs populated
	 * 
	 * @param authentication
	 * @param newFile
	 * @param newNote
	 * @param newCredential
	 * @param model
	 * @return
	 */
	@GetMapping
	public String getHomePage(Authentication authentication, @ModelAttribute("newFile") FileForm newFile,
			@ModelAttribute("newNote") NoteForm newNote, @ModelAttribute("newCredential") CredentialForm newCredential,
			Model model) {

		try {

			User user = userService.getUser(authentication.getName());

			model.addAttribute("files", fileService.getFiles(user.getUserId()));
			model.addAttribute("notes", noteService.getNotes(user.getUserId()));
			model.addAttribute("credentials", credentialService.getUserCredentials(user.getUserId()));
			 model.addAttribute("encryptionService", encryptionService);

			return "home";
		} catch (Exception e) {
			model.addAttribute("message", "Home page loading error");
			model.addAttribute("result", "error");
			return "result";
		}
	}

}
