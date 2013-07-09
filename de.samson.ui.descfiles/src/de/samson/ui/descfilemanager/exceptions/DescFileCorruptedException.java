package de.samson.ui.descfilemanager.exceptions;

import java.io.File;

import de.samson.ui.descfilemanager.parser.EnumDescFileType;


public class DescFileCorruptedException extends Exception {
	private static final long serialVersionUID = -2463869273503605750L;

	File corruptedFile;
	EnumDescFileType enumDescFileType;
	int lineCount;
	String[] resultDataset;
	String unparsedDataset;

	public DescFileCorruptedException(EnumDescFileType enumDescFileType,
			String[] resultDataset, String unparsedDataset, File corruptedFile,
			int lineCount) {

		this.enumDescFileType = enumDescFileType;
		this.resultDataset = resultDataset;
		this.unparsedDataset = unparsedDataset;
		this.corruptedFile = corruptedFile;
		this.lineCount = lineCount;
	}

	public File getCorruptedFile() {
		return corruptedFile;
	}

	public EnumDescFileType getDescFileType() {
		return enumDescFileType;
	}

	public int getLineCount() {
		return lineCount;
	}

	public String[] getResultDataset() {
		return resultDataset;
	}

	public String getResultDatasetAsLine() {
		String resultDatasetLine = "";
		for (int i = 0; i < resultDataset.length; i++)
			resultDatasetLine += resultDataset[i] + "; ";
		return resultDatasetLine;
	}

	public String getUnparsedDataset() {
		return unparsedDataset;
	}

	public void setCorruptedFile(File corruptedFile) {
		this.corruptedFile = corruptedFile;
	}

}
