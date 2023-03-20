package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/result")
public class ResultController {
	
    @GetMapping("/success")
    public String successView() {
        return "result";
    }

    @ModelAttribute("successMessage")
    public String successMessage() {
        return "Success! Your operation was completed successfully.";
    }
}
