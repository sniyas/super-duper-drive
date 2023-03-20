package com.udacity.jwdnd.course1.cloudstorage.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;

@Service
public class NoteService {

	@Autowired
	private NoteMapper noteMapper;

	/**
	 * Adds a new note
	 * 
	 * @param note
	 * @return
	 */
	public int addNote(Note note) {
		return noteMapper.insert(note);
	}

	/**
	 * Fetches all notes for a user
	 * 
	 * @param userId
	 * @return
	 */
	public List<Note> getNotes(Integer userId) {
		return noteMapper.getNotesByUser(userId);
	}

	/**
	 * Updates an existing note
	 * 
	 * @param note
	 * @return
	 */
	public int updateNote(Note note) {
		return noteMapper.updateNote(note);
	}

	/**
	 * Deletes an existing note
	 * 
	 * @param noteId
	 * @return
	 */
	public int deleteNote(Integer noteId) {
		return noteMapper.deleteNote(noteId);
	}
}
