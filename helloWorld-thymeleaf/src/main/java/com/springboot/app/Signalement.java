package com.springboot.app;

public class Signalement {
	int id;
    int idType;
    int idRegion;
    String titre;
    String image;
    double longitude;
    double latitude;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdType() {
		return idType;
	}
	public void setIdType(int idType) {
		this.idType = idType;
	}
	public int getIdRegion() {
		return idRegion;
	}
	public void setIdRegion(int idRegion) {
		this.idRegion = idRegion;
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
	public Signalement(int id, int idType, int idRegion, String titre, String image, double longitude,
			double latitude) {
		super();
		this.id = id;
		this.idType = idType;
		this.idRegion = idRegion;
		this.titre = titre;
		this.image = image;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	public Signalement() {
		super();
	}
    
    
}
