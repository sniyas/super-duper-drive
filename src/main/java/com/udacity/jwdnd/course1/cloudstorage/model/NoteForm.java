package com.udacity.jwdnd.course1.cloudstorage.model;

public class NoteForm {
	private Integer noteId;
	private String title;
	private String description;

	public NoteForm() {
	}

	public NoteForm(Integer noteId, String noteTitle, String noteDescription) {
		this.noteId = noteId;
		this.title = noteTitle;
		this.description = noteDescription;
	}

	public Integer getNoteId() {
		return noteId;
	}

	public void setNoteId(Integer noteId) {
		this.noteId = noteId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String noteTitle) {
		this.title = noteTitle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String noteDescription) {
		this.description = noteDescription;
	}
}
