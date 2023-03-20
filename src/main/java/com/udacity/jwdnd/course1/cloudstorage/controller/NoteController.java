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

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
@RequestMapping("/note")
public class NoteController {

	@Autowired
	private NoteService noteService;
	@Autowired
	private UserService userService;

	/**
	 * To add a new note/ update existing note
	 * 
	 * @param authentication
	 * @param newNote
	 * @param model
	 * @return
	 */
	@PostMapping("add-note")
	public String addNote(Authentication authentication, @ModelAttribute("newNote") NoteForm newNote, Model model) {

		try {
			User user = userService.getUser(authentication.getName());
			Integer noteId = newNote.getNoteId();
			String noteTitle = newNote.getTitle();
			String noteDescription = newNote.getDescription();

			if (noteId == null) {
				Note note = new Note(null, noteTitle, noteDescription, user.getUserId());
				noteService.addNote(note);
				model.addAttribute("result", "success");
			} else {
				noteService.updateNote(new Note(noteId, noteTitle, noteDescription, user.getUserId()));
				model.addAttribute("result", "success");
			}

		} catch (Exception e) {
			model.addAttribute("message", "Failed to save note");
			model.addAttribute("result", "error");
		}
		return "result";
	}

	/**
	 * To delete an existing note
	 * @param authentication
	 * @param noteId
	 * @param model
	 * @return
	 */
	@GetMapping("/delete-note/{noteId}")
	public String deleteNote(Authentication authentication, @PathVariable("noteId") Integer noteId, Model model) {
		try {
			String userName = authentication.getName();
			User user = userService.getUser(userName);
			Integer userId = user.getUserId();
			noteService.deleteNote(noteId);
			model.addAttribute("notes", noteService.getNotes(userId));
			model.addAttribute("result", "success");
		} catch (Exception e) {
			model.addAttribute("message", "Failed to delete note");
			model.addAttribute("result", "error");
		}
		return "result";
	}

}
