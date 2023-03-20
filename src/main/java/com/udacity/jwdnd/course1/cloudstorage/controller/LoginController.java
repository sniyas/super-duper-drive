package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

	@GetMapping
	public String loginView(Model model, @ModelAttribute(name = "signupSuccess") String signupSuccess) {
		if (StringUtils.isEmpty(signupSuccess)) {

			model.addAttribute("signupSuccess", false);
		} else {
			model.addAttribute("signupSuccess", signupSuccess);
		}
		return "login";
	}
}
