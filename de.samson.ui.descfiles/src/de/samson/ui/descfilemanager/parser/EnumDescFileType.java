package de.samson.ui.descfilemanager.parser;

public enum EnumDescFileType {
	Coil(9, "STR_Coils.txt"), Geraet(7, "STR_Geraet.txt"), HoldingRegister(13,
			"STR_HoldingReg.txt"), Comment(1, "Comment.txt"), RevCount(1,
			"RevCount.txt"), JSON(0, "WMZ_Desc.json");

	int columns;
	String fileName;

	private EnumDescFileType(int columns, String fileName) {
		this.columns = columns;
		this.fileName = fileName;
	}

	public int getColumns() {
		return columns;
	}

	public String getFileName() {
		return fileName;
	}
}
