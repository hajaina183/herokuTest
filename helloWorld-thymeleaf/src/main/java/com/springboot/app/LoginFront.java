package com.springboot.app;

public class LoginFront {
	int id;
	int idRegion;
	String nom;
	String mdp;
	
	public LoginFront(int id, int idRegion, String nom, String mdp) {
		super();
		this.id = id;
		this.idRegion = idRegion;
		this.nom = nom;
		this.mdp = mdp;
	}
	public LoginFront() {
		super();
	}
	public LoginFront(int idRegion, String nom, String mdp) {
		super();
		this.idRegion = idRegion;
		this.nom = nom;
		this.mdp = mdp;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdRegion() {
		return idRegion;
	}
	public void setIdRegion(int idRegion) {
		this.idRegion = idRegion;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getMdp() {
		return mdp;
	}
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	
}
