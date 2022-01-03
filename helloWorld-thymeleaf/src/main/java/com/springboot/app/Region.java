package com.springboot.app;

public class Region {
	int id;
	String nom;
	
	public Region() {
		super();
	}
	
	public Region(String nom) {
		super();
		this.nom = nom;
	}

	public Region(int id, String nom) {
		super();
		this.id = id;
		this.nom = nom;
	}

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
	
}
