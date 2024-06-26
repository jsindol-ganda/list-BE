package com.example.mylisttest.models;

import java.util.Date;


public class Note {
	private int id;
	private String title;
	private String desc;
	private Date date;
	
	public Note() {
		
	}
	
	public Note(int id, String title, String desc, Date date) {
		this.id = id;
		this.title = title;
		this.desc = desc;
		this.date = date;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

}
