package com.springboot.app;

public class ChartRegion {
	int idRegion;
	double pource;
	public int getIdRegion() {
		return idRegion;
	}
	public void setIdRegion(int idRegion) {
		this.idRegion = idRegion;
	}
	public double getPource() {
		return pource;
	}
	public void setPource(double pource) {
		this.pource = pource;
	}
	public ChartRegion(int idRegion, double pource) {
		super();
		this.idRegion = idRegion;
		this.pource = pource;
	}
	public ChartRegion() {
		super();
	}
	
}
