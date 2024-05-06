package com.example.mylisttest.controllers;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.mylisttest.interfaces.MyListService;
import com.example.mylisttest.models.Note;
import com.google.gson.Gson;

import io.micrometer.common.util.StringUtils;

@RestController
public class MyListController {
	
	@Autowired
	MyListService listService;
	
	@GetMapping(path = "getAllNotes")
	public ResponseEntity<List<Note>> getAllNotes() {
		return new ResponseEntity<List<Note>>(listService.getAllNotes(), HttpStatus.OK);
	}
	
	@GetMapping(path = "getNote")
	public ResponseEntity<Note> getANote(@RequestBody HashMap<String, Integer> request) {
		ResponseEntity<Note> response;

		if (!StringUtils.isEmpty(request.get("id").toString())) {
			response = new ResponseEntity<Note>(listService.getOneNote(request.get("id").intValue()), HttpStatus.OK);
		} else {
			response = new ResponseEntity<Note>(new Note(), HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}
	
	@PostMapping(path = "addNote")
	public ResponseEntity<String> addNote(@RequestBody String payload) {
		Gson gson = new Gson();
		listService.addNote(gson.fromJson(payload, Note.class));
		return new ResponseEntity<String>("Note Added", HttpStatus.OK);
	}
	
	@PutMapping(path = "updateNote")
	public ResponseEntity<String> updateNote(@RequestBody String payload) {
		ResponseEntity<String> response;
		Gson gson = new Gson();
		Note toBeUpdatedNote = gson.fromJson(payload, Note.class);
		
		if (toBeUpdatedNote.getId() != 0) {
			listService.updateNote(toBeUpdatedNote);
			response = new ResponseEntity<>("Note Updated", HttpStatus.OK);
		} else {
			response = new ResponseEntity<>("Failed to update note", HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}
	
	@DeleteMapping(path = "deleteNote")
	public ResponseEntity<String> deleteNote(@RequestBody HashMap<String, Integer> request) {
		ResponseEntity<String> response;

		if (!StringUtils.isEmpty(request.get("id").toString())) {
			listService.deleteNote(request.get("id").intValue());
			response = new ResponseEntity<>("Note deleted", HttpStatus.OK);
		} else {
			response = new ResponseEntity<>("Failed to delete note", HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}
}
