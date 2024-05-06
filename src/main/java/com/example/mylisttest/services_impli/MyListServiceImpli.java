package com.example.mylisttest.services_impli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.mylisttest.interfaces.MyListService;
import com.example.mylisttest.models.Note;

@Service
public class MyListServiceImpli implements MyListService {

	private List<Note> mainNotes;
	
	public MyListServiceImpli() {
		mainNotes = new ArrayList<Note>();
		
		Note placeNoteOne = new Note(1, "Pick Berry", "Pick up Rachel Berry from practice", new Date());
		Note placeNoteTwo = new Note(2, "Buy Album", "Get the latest Charli XCX album", new Date());
		Note placeNoteThree = new Note(3, "Do Laundry", "Go to Ari's and do laundry", new Date());

		mainNotes.addAll(Arrays.asList(placeNoteOne, placeNoteTwo, placeNoteThree));
	}
	@Override
	public List<Note> getAllNotes() {
		return mainNotes;
	}

	@Override
	public Note getOneNote(int id) {
		return mainNotes.stream()
				.filter(note -> note.getId() == id)
				.findFirst()
				.orElse(null);
	}

	@Override
	public void addNote(Note note) {
		note.setId(mainNotes.size() + 1);
		mainNotes.add(note);
	}

	@Override
	public void updateNote(Note note) {
		mainNotes.stream().filter(aNote -> aNote.getId() == note.getId())
		.findFirst()
		.ifPresent(selectedNote -> {
			selectedNote.setTitle(note.getTitle());
			selectedNote.setDesc(note.getDesc());
			selectedNote.setDate(new Date());
		});
	}

	@Override
	public void deleteNote(int id) {
		mainNotes = mainNotes.stream()
        .filter(aNote -> aNote.getId() != id)
        .collect(Collectors.toList());
	}

}
