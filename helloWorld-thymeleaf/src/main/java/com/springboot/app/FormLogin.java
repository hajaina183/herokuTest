package com.springboot.app;

public class FormLogin {
	int id;
	String nom;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public FormLogin(int id, String nom) {
		super();
		this.id = id;
		this.nom = nom;
	}
	public FormLogin() {
		super();
	}
	
}
