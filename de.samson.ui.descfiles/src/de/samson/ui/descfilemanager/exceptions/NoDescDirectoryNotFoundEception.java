package de.samson.ui.descfilemanager.exceptions;

public class NoDescDirectoryNotFoundEception extends Exception {
	private static final long serialVersionUID = -852116294975551114L;

	public NoDescDirectoryNotFoundEception(String rootDir) {
		super();
		System.err
				.println("No description files were found in this directory: "
						+ rootDir);
	}

}
