package com.springboot.app;

public class ReponseLoginFront {
	int reponse;
	String token;
	public int getReponse() {
		return reponse;
	}
	public void setReponse(int reponse) {
		this.reponse = reponse;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public ReponseLoginFront(int reponse, String token) {
		super();
		this.reponse = reponse;
		this.token = token;
	}
	public ReponseLoginFront() {
		super();
	}
	
}
