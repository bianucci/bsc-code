package de.samson.ui.descfilemanager.exceptions;

import java.io.FileNotFoundException;
import java.util.List;

public class DescFileParsingException extends Exception {
	private static final long serialVersionUID = -1203345344923435973L;

	private List<DescFileCorruptedException> allCorruptedFiles;
	private List<FileNotFoundException> allMissingFiles;

	public DescFileParsingException(
			List<DescFileCorruptedException> allCorruptedFiles,
			List<FileNotFoundException> allMissingFiles) {

		this.allCorruptedFiles = allCorruptedFiles;
		this.allMissingFiles = allMissingFiles;
	}

	public List<DescFileCorruptedException> getAllCorruptedFiles() {
		return allCorruptedFiles;
	}

	public void setAllCorruptedFiles(
			List<DescFileCorruptedException> allCorruptedFiles) {
		this.allCorruptedFiles = allCorruptedFiles;
	}

	public List<FileNotFoundException> getAllMissingFiles() {
		return allMissingFiles;
	}

	public void setAllMissingFiles(List<FileNotFoundException> allMissingFiles) {
		this.allMissingFiles = allMissingFiles;
	}

	@Override
	public String getMessage() {
		String message = "";

		for (DescFileCorruptedException e : allCorruptedFiles)
			message = message + "\n" + e.getCorruptedFile().toString() + "\\"
					+ e.getDescFileType().getFileName();

		for (FileNotFoundException e : allMissingFiles)
			message = message + "\n" + e.getMessage().toString();

		return message;
	}
	
	@Override
	public void printStackTrace() {
		System.err.println(getMessage());
	}
}
