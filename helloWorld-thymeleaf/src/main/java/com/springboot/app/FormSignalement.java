package com.springboot.app;

import java.util.Date;

public class FormSignalement {
	int idType;
	String titre;
	String image;
	double longitude;
	double latitude;
	String description;
	int idStatusSignalement;
	int idPersonne;
	Date date;
	
	public int getIdPersonne() {
		return idPersonne;
	}
	public void setIdPersonne(int idPersonne) {
		this.idPersonne = idPersonne;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getIdType() {
		return idType;
	}
	public int getIdStatusSignalement() {
		return idStatusSignalement;
	}
	public void setIdStatusSignalement(int idStatusSignalement) {
		this.idStatusSignalement = idStatusSignalement;
	}
	public void setIdType(int idType) {
		this.idType = idType;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public FormSignalement(int idType, String titre, String image, double longitude, double latitude,
			String description, int idStatusSignalement, int idPersonne, Date date) {
		super();
		this.idType = idType;
		this.titre = titre;
		this.image = image;
		this.longitude = longitude;
		this.latitude = latitude;
		this.description = description;
		this.idStatusSignalement = idStatusSignalement;
		this.idPersonne = idPersonne;
		this.date = date;
	}
	public FormSignalement() {
		super();
	}
	
}
