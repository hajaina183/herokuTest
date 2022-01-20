package com.springboot.app;

public class Type {
	int id;
	String intitule;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIntitule() {
		return intitule;
	}
	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}
	public Type(String intitule) {
		super();
		this.intitule = intitule;
	}
	
	public Type(int id, String intitule) {
		super();
		this.id = id;
		this.intitule = intitule;
	}
	public Type() {
		super();
	}
		
}
