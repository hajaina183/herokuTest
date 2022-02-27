package com.springboot.app;

public class Region {
	int id;
	String nom;
	double latitude;
	double longitude;
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
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public Region(int id, String nom, double latitude, double longitude) {
		super();
		this.id = id;
		this.nom = nom;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	public Region() {
		super();
	}
	
	
	
}
