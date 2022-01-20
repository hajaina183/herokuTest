package com.springboot.app;

public class SignalementChangerStatus {
	int id;
    int idStatusSignalement;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdStatusSignalement() {
        return idStatusSignalement;
    }

    public void setIdStatusSignalement(int idStatusSignalement) {
        this.idStatusSignalement = idStatusSignalement;
    }

	public SignalementChangerStatus(int id, int idStatusSignalement) {
		super();
		this.id = id;
		this.idStatusSignalement = idStatusSignalement;
	}

	public SignalementChangerStatus() {
		super();
	}
    
}
