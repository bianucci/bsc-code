package de.samson.service.database.entities.description;

import java.util.Map;

public class WMWDescrption {
	// Register mit faktor
	Map<HoldingRegiterDescription, Double> wertigkeiten;

	// Register mit wert für Einheit
	HoldingRegiterDescription einheitRegister;

	// Wert zu Bedeutung, z.b. 0=m^3/h
	Map<String, String> massEinheit;

	String category;

	public WMWDescrption() {
		super();
	}

	public WMWDescrption(Map<HoldingRegiterDescription, Double> wertigkeiten,
			HoldingRegiterDescription einheitRegister, Map<String, String> massEinheit,
			String category) {
		super();
		this.wertigkeiten = wertigkeiten;
		this.einheitRegister = einheitRegister;
		this.massEinheit = massEinheit;
		this.category = category;
	}

	public void setWertigkeiten(Map<HoldingRegiterDescription, Double> wertigkeiten) {
		this.wertigkeiten = wertigkeiten;
	}

	public void setEinheitRegister(HoldingRegiterDescription einheitRegister) {
		this.einheitRegister = einheitRegister;
	}

	public void setMassEinheit(Map<String, String> massEinheit) {
		this.massEinheit = massEinheit;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Map<HoldingRegiterDescription, Double> getWertigkeiten() {
		return wertigkeiten;
	}

	public HoldingRegiterDescription getEinheitRegister() {
		return einheitRegister;
	}

	public Map<String, String> getMassEinheit() {
		return massEinheit;
	}

	public String getCategory() {
		return category;
	}

	@Override
	public String toString() {
		return "WärmeMengenWert [wertigkeiten=" + wertigkeiten
				+ ", einheitRegister=" + einheitRegister + ", massEinheit="
				+ massEinheit + ", category=" + category + "]";
	}

}
