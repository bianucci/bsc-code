package de.samson.service.database.entities.description;

import java.util.ArrayList;

public class WMZDescription extends ArrayList<WMWDescrption> {
	private static final long serialVersionUID = 1L;

	String name;

	public WMZDescription() {
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
