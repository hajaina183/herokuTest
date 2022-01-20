package com.springboot.app;

public class StatusSignalement {
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

	public StatusSignalement(int id, String intitule) {
		super();
		this.id = id;
		this.intitule = intitule;
	}

	public StatusSignalement() {
		super();
	}
    
}
