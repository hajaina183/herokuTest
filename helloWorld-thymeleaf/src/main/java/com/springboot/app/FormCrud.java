package com.springboot.app;

public class FormCrud {
	String nomRegion;
	String nomChefRegion;
	
	public FormCrud() {
		super();
	}
	public FormCrud(String nomRegion, String nomChefRegion) {
		super();
		this.nomRegion = nomRegion;
		this.nomChefRegion = nomChefRegion;
	}
	public String getNomRegion() {
		return nomRegion;
	}
	public void setNomRegion(String nomRegion) {
		this.nomRegion = nomRegion;
	}
	public String getNomChefRegion() {
		return nomChefRegion;
	}
	public void setNomChefRegion(String nomChefRegion) {
		this.nomChefRegion = nomChefRegion;
	}
	
}
