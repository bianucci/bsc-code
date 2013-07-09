package de.samson.ui.descfilemanager.reglerTreeFactory;

import java.util.ArrayList;

import de.samson.service.database.entities.description.STR_Geraet;

public class ReglerRevisionGroup extends ArrayList<STR_Geraet> {
	private static final long serialVersionUID = 8179731244023418133L;

	String name;

	public ReglerRevisionGroup(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
