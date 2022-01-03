package com.springboot.app;

public class ChartType {
	int idType;
	double pource;
	public int getIdType() {
		return idType;
	}
	public void setIdType(int idtype) {
		this.idType = idtype;
	}
	public double getPource() {
		return pource;
	}
	public void setPource(double pource) {
		this.pource = pource;
	}
	public ChartType(int idtype, double pource) {
		super();
		this.idType = idtype;
		this.pource = pource;
	}
	public ChartType() {
		super();
	}
	
}
