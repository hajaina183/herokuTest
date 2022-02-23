package com.springboot.app;

public class ReponseLoginPerson {
	int id;
	String token;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public ReponseLoginPerson(int id, String token) {
		super();
		this.id = id;
		this.token = token;
	}
	public ReponseLoginPerson() {
		super();
	}
	
}
