package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.HashService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
@RequestMapping("/files")
public class FileController {

	@Autowired
	private FileService fileService;
	@Autowired
	private UserService userService;
	@Autowired
	UserMapper userMapper;
	@Autowired
	HashService hashService;

	/**
	 * To upload a new file
	 * 
	 * @param file
	 * @param authentication
	 * @param model
	 * @return
	 */
	@PostMapping("/upload")
	public String uploadFile(@RequestParam("fileUpload") MultipartFile file, Authentication authentication,
			Model model) {

		User user = userService.getUser(authentication.getName());

		if (!file.isEmpty()) {
			try {
				fileService.storeFile(file, user.getUserId());
				model.addAttribute("result", "success");

			} catch (FileService.DuplicateFileNameException e) {
				model.addAttribute("message", "A file with the same name already exists.");
				model.addAttribute("result", "error");
			} catch (Exception e) {
				model.addAttribute("message", "Failed to store the file");
				model.addAttribute("result", "error");
			}
		} else {
			model.addAttribute("message", "Please select a file to upload.");
			model.addAttribute("result", "error");
		}

		return "result";
	}

	/**
	 * Fetch a file
	 * 
	 * @param fileId
	 * @param response
	 * @throws IOException
	 */
	@GetMapping(value = "/get-file/{fileId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public void getFile(@PathVariable Integer fileId, HttpServletResponse response) throws IOException {

		File file = fileService.getFile(fileId);

		response.setContentType(file.getContentType());
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=" + file.getFileName();
		response.setHeader(headerKey, headerValue);
		response.getOutputStream().write(file.getFileData());
	}

	/**
	 * Delete an existing file
	 * 
	 * @param fileId
	 * @param authentication
	 * @param model
	 * @return
	 */
	@GetMapping("/delete-file/{fileId}")
	public String deleteFile(@PathVariable("fileId") Integer fileId, Authentication authentication, Model model) {

		try {
			User user = userService.getUser(authentication.getName());
			fileService.deleteFile(fileId, user.getUserId());
			model.addAttribute("result", "success");
		} catch (Exception e) {
			model.addAttribute("message", "Failed to delete the file.");
			model.addAttribute("result", "error");
		}

		return "result";
	}

}
