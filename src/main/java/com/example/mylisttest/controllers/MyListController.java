package com.example.mylisttest.controllers;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mylisttest.interfaces.MyListService;
import com.example.mylisttest.models.Note;
import com.example.mylisttest.models.NoteResponse;
import com.google.gson.Gson;

import io.micrometer.common.util.StringUtils;

@RestController
@RequestMapping("/api/")
public class MyListController {
	
	@Autowired
	MyListService listService;
	
	@GetMapping(path = "getAllNotes")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<List<Note>> getAllNotes() {
		return new ResponseEntity<List<Note>>(listService.getAllNotes(), HttpStatus.OK);
	}
	
	@GetMapping(path = "getNote")
	@CrossOrigin(origins = "http://localhost:4200")
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
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<NoteResponse> addNote(@RequestBody String payload) {
		Gson gson = new Gson();
		NoteResponse noteResponse = new NoteResponse(HttpStatus.OK, "Note Added");
		listService.addNote(gson.fromJson(payload, Note.class));
		return new ResponseEntity<NoteResponse>(noteResponse, HttpStatus.OK);
	}
	
	@PutMapping(path = "updateNote")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<NoteResponse> updateNote(@RequestBody String payload) {
		ResponseEntity<NoteResponse> response;
		Gson gson = new Gson();
		Note toBeUpdatedNote = gson.fromJson(payload, Note.class);
		
		if (toBeUpdatedNote.getId() != 0) {
			listService.updateNote(toBeUpdatedNote);
			response = new ResponseEntity<>(new NoteResponse(HttpStatus.OK, "Note Updated"), HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(new NoteResponse(HttpStatus.BAD_REQUEST, "Failed to update note"), HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}
	
	@DeleteMapping(path = "deleteNote")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<NoteResponse> deleteNote(@RequestBody HashMap<String, Integer> request) {
		ResponseEntity<NoteResponse> response;

		if (!StringUtils.isEmpty(request.get("id").toString())) {
			listService.deleteNote(request.get("id").intValue());
			response = new ResponseEntity<>(new NoteResponse(HttpStatus.OK, "Note Deleted"), HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(new NoteResponse(HttpStatus.BAD_REQUEST, "Failed to delete note"), HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}
}
