package de.samson.service.database.entities.description;

import java.util.ArrayList;

public class WaermeMengenZähler extends ArrayList<WaermeMengenWert> {
	private static final long serialVersionUID = 1L;

	String name;

	public WaermeMengenZähler() {
	}

	public void setName(String name) {
		this.name = name;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getName() {
		return name;
	}

}
