package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.UserForm;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
@RequestMapping("/signup")
public class SignupController {

	@Autowired
	private UserService userService;

	@GetMapping
	public String getSignupPage(Model model) {
		model.addAttribute("userForm", new UserForm());
		return "signup";
	}

	/**
	 * Create a user
	 * 
	 * @param user
	 * @param model
	 * @param redir
	 * @return
	 */
	@PostMapping()
	public RedirectView signupUser(@ModelAttribute User user, Model model, RedirectAttributes redir) {

		RedirectView redirectView = new RedirectView("/signup", true);
		String signupErrorMessage = null;

		try {
			if (!userService.isUsernameAvailable(user.getUsername())) {
				signupErrorMessage = "The username already exists.";
			} else {
				int rowsAdded = userService.createUser(user);
				if (rowsAdded < 0) {
					signupErrorMessage = "There was an error signing you up. Please try again.";
				} else {
					redir.addFlashAttribute("signupSuccess", true);
					redirectView = new RedirectView("/login", true);
				}
			}

		} catch (Exception e) {
			signupErrorMessage = "Error during sign up.";
		}

		if (signupErrorMessage != null) {
			redir.addFlashAttribute("signupError", true);
			redir.addFlashAttribute("signupErrorMessage", signupErrorMessage);
		}

		return redirectView;
	}
}
