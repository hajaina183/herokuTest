package com.springboot.app;

public class LoginPersonInscription {
	String email;
    String mdp;
    String nom;
    int age;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMdp() {
		return mdp;
	}
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public LoginPersonInscription(String email, String mdp, String nom, int age) {
		super();
		this.email = email;
		this.mdp = mdp;
		this.nom = nom;
		this.age = age;
	}
	public LoginPersonInscription() {
		super();
	}
}
