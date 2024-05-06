package com.example.mylisttest.interfaces;

import java.util.List;

import com.example.mylisttest.models.Note;

public interface MyListService {
	public List<Note> getAllNotes();
	public Note getOneNote(int id);
	public void addNote(Note note);
	public void updateNote(Note note);
	public void deleteNote(int id);
}
