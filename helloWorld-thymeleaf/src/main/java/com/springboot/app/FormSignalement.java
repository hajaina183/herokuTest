package com.springboot.app;

public class FormSignalement {
	int idType;
	String titre;
	String image;
	double longitude;
	double latitude;
	String description;
	public int getIdType() {
		return idType;
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
			String description) {
		super();
		this.idType = idType;
		this.titre = titre;
		this.image = image;
		this.longitude = longitude;
		this.latitude = latitude;
		this.description = description;
	}
	public FormSignalement() {
		super();
	}
	
}
