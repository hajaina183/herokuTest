package com.springboot.app;

public class DetailSignalement {
	Signalement signalement;
	Type signalementType;
	Region signalementRegion;
	String signalementDescription;
	
	public Signalement getSignalement() {
		return signalement;
	}


	public void setSignalement(Signalement signalement) {
		this.signalement = signalement;
	}

	public Type getSignalementType() {
		return signalementType;
	}


	public void setSignalementType(Type signalementType) {
		this.signalementType = signalementType;
	}


	public Region getSignalementRegion() {
		return signalementRegion;
	}


	public void setSignalementRegion(Region signalementRegion) {
		this.signalementRegion = signalementRegion;
	}
	
	public String getSignalementDescription() {
		return signalementDescription;
	}


	public void setSignalementDescription(String signalementDescription) {
		this.signalementDescription = signalementDescription;
	}


	public DetailSignalement(Signalement signalement, Type type, Region region,String description) {
		super();
		this.signalement = signalement;
		this.signalementType = type;
		this.signalementRegion = region;
		this.signalementDescription = description;
	}


	public DetailSignalement() {
		super();
	}
}
