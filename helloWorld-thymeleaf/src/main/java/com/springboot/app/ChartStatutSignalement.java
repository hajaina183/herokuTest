package com.springboot.app;

public class ChartStatutSignalement {
	int idStatussignalement;
	double pource;
	
	public int getIdStatussignalement() {
		return idStatussignalement;
	}
	public void setIdStatussignalement(int idStatussignalement) {
		this.idStatussignalement = idStatussignalement;
	}
	public double getPource() {
		return pource;
	}
	public void setPource(double pource) {
		this.pource = pource;
	}
	public ChartStatutSignalement(int idStatussignalement, double pource) {
		super();
		this.idStatussignalement = idStatussignalement;
		this.pource = pource;
	}
	public ChartStatutSignalement() {
		super();
	}
	
}
