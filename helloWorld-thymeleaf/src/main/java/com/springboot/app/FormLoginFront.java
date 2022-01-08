package com.springboot.app;

public class FormLoginFront {
	String nom;
	String mdp;
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
	public FormLoginFront(String nom, String mdp) {
		super();
		this.nom = nom;
		this.mdp = mdp;
	}
	public FormLoginFront() {
		super();
	}
	
}
