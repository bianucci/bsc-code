package de.samson.ui.descfilemanager.exceptions;

public class DescDirectoryNotFoundEception extends Exception {
	private static final long serialVersionUID = -852116294975551114L;

	public DescDirectoryNotFoundEception(String rootDir) {
		super();
		System.err
				.println("No description files were found in this directory: "
						+ rootDir);
	}

}
