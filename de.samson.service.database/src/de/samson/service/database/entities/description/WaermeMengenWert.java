package de.samson.service.database.entities.description;

import java.util.Map;

public class WaermeMengenWert {
	// Register mit faktor
	Map<STR_HoldingReg, Double> wertigkeiten;

	// Register mit wert für Einheit
	STR_HoldingReg einheitRegister;

	// Wert zu Bedeutung, z.b. 0=m^3/h
	Map<String, String> massEinheit;

	String category;

	public WaermeMengenWert() {
		super();
	}

	public WaermeMengenWert(Map<STR_HoldingReg, Double> wertigkeiten,
			STR_HoldingReg einheitRegister, Map<String, String> massEinheit,
			String category) {
		super();
		this.wertigkeiten = wertigkeiten;
		this.einheitRegister = einheitRegister;
		this.massEinheit = massEinheit;
		this.category = category;
	}

	public void setWertigkeiten(Map<STR_HoldingReg, Double> wertigkeiten) {
		this.wertigkeiten = wertigkeiten;
	}

	public void setEinheitRegister(STR_HoldingReg einheitRegister) {
		this.einheitRegister = einheitRegister;
	}

	public void setMassEinheit(Map<String, String> massEinheit) {
		this.massEinheit = massEinheit;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Map<STR_HoldingReg, Double> getWertigkeiten() {
		return wertigkeiten;
	}

	public STR_HoldingReg getEinheitRegister() {
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
