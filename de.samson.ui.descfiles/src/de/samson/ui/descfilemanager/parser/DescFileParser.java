package de.samson.ui.descfilemanager.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.samson.service.database.entities.description.STR_Coil;
import de.samson.service.database.entities.description.STR_Geraet;
import de.samson.service.database.entities.description.STR_HoldingReg;
import de.samson.ui.descfilemanager.exceptions.DescFileCorruptedException;
import de.samson.ui.descfilemanager.exceptions.DescFileParsingException;
import de.samson.ui.descfilemanager.exceptions.NoDescDirectoryNotFoundEception;

public class DescFileParser {

	private static ArrayList<STR_Geraet> controller;

	private static ArrayList<File> allControllerDirs;

	private static File controllerDir = null;

	public static void init(String rootDir)
			throws NoDescDirectoryNotFoundEception, IOException,
			DescFileParsingException {

		List<DescFileCorruptedException> allCorruptedFiles = new ArrayList<>();
		List<FileNotFoundException> allMissingFiles = new ArrayList<>();
		boolean errorOccured = false;

		controllerDir = new File(rootDir);
		controller = new ArrayList<>();
		allControllerDirs = new ArrayList<>();

		if (!controllerDir.exists()) {
			throw new FileNotFoundException(rootDir);
		}

		identifyAllControllerDirectories(controllerDir);
		if (allControllerDirs.size() <= 0) {
			throw new NoDescDirectoryNotFoundEception(rootDir);
		}

		Iterator<File> i = allControllerDirs.iterator();
		while (i.hasNext()) {

			File next = i.next();

			try {
				String[] str_geraetData = parseDescriptionFile(next,
						EnumDescFileType.Geraet).get(0);

				ArrayList<String[]> str_coilsData = parseDescriptionFile(next,
						EnumDescFileType.Coil);

				ArrayList<String[]> str_holdingregData = parseDescriptionFile(
						next, EnumDescFileType.HoldingRegister);

				String comment = parseDescriptionFile(next,
						EnumDescFileType.Comment).get(0)[0];

				String revCount = parseDescriptionFile(next,
						EnumDescFileType.RevCount).get(0)[0];

				STR_Geraet tempGeraet = createGeraetFromParsedData(
						str_geraetData, revCount, comment);

				for (String[] data : str_coilsData) {
					STR_Coil tempCoil = createCoilFromParsedData(data,
							tempGeraet);
					tempGeraet.getCoilsList().add(tempCoil);
				}

				for (String[] data : str_holdingregData) {
					STR_HoldingReg tempHReg = createHoldingRegFromParsedData(
							data, tempGeraet);
					tempGeraet.getRegisterList().add(tempHReg);
				}
				controller.add(tempGeraet);

			} catch (DescFileCorruptedException e) {
				errorOccured = true;
				if (e.getCorruptedFile() == null) {
					e.setCorruptedFile(next);
				}
				allCorruptedFiles.add(e);
			} catch (FileNotFoundException e) {
				errorOccured = true;
				allMissingFiles.add(e);
			}

		}

		if (errorOccured) {
			throw new DescFileParsingException(allCorruptedFiles,
					allMissingFiles);
		}
	}

	private static void identifyAllControllerDirectories(File rootDir) {

		File[] filesInThisPath = rootDir.listFiles();

		for (int i = 0; i < filesInThisPath.length; i++) {

			if (filesInThisPath[i].isDirectory()) {
				identifyAllControllerDirectories(filesInThisPath[i]);

			} else if (filesInThisPath[i].getName().toLowerCase()
					.endsWith(".txt")) {

				File parent = filesInThisPath[i].getParentFile();

				File a = new File(parent.getAbsolutePath() + "\\"
						+ EnumDescFileType.Geraet.getFileName());

				File b = new File(parent.getAbsolutePath() + "\\"
						+ EnumDescFileType.Coil.getFileName());

				File c = new File(parent.getAbsolutePath() + "\\"
						+ EnumDescFileType.HoldingRegister.getFileName());

				if (!allControllerDirs.contains(parent)) {
					if (a.exists() && b.exists() && c.exists())
						allControllerDirs.add(parent);
				}
			}
		}
	}

	private static ArrayList<String[]> parseDescriptionFile(File controllerDir,

	EnumDescFileType enumDescFileType) throws IOException,
			DescFileCorruptedException {

		File descFile = new File(controllerDir.getAbsolutePath() + "\\"
				+ enumDescFileType.getFileName());

		FileReader fr = new FileReader(descFile);
		BufferedReader br = new BufferedReader(fr);

		ArrayList<String[]> parsedObjects = new ArrayList<String[]>();

		String line = null;
		int lineCount = 0;

		while (null != (line = br.readLine())) {

			if (line.replaceAll(" ", "").length() > 0) {

				if (!line.toLowerCase().startsWith("rem")) {
					String untouched = line.toString();

					line = line.replaceAll("_", " ");
					line = line.replaceAll("\"", "");
					line = line.replaceAll(" +[ ]", "");
					line = line.replaceAll("^ ", "");
					line = line.replaceAll("\t", "");

					String[] split = line.split(";");
					if (split.length != enumDescFileType.getColumns()) {
						br.close();
						throw new DescFileCorruptedException(enumDescFileType,
								split, untouched, descFile, lineCount);
					} else {
						parsedObjects.add(split);
					}
				}
			}
			lineCount++;
		}

		br.close();

		return parsedObjects;
	}

	private static STR_Coil createCoilFromParsedData(String[] parsedData,
			STR_Geraet geraet) throws DescFileCorruptedException {

		STR_Coil tempCoil = new STR_Coil();

		tempCoil.setGeraet(geraet);
		tempCoil.setGeraeteKennung(geraet.getGeraeteKennung());
		tempCoil.setRevision(geraet.getRevision());
		tempCoil.setAnzKategorie(parsedData[2]);
		tempCoil.setBezeichnung(parsedData[3]);
		tempCoil.setKommentar(parsedData[4]);
		tempCoil.setRo(parsedData[5].equals("1"));
		tempCoil.setText0(parsedData[6]);
		tempCoil.setText1(parsedData[7]);

		try {
			tempCoil.setClnr(Integer.valueOf(parsedData[0]));
			tempCoil.setMaskCSV(Integer.valueOf(parsedData[8]));
		} catch (NumberFormatException e) {
			throw new DescFileCorruptedException(EnumDescFileType.Coil,
					parsedData, null, null, 0);
		}
		return tempCoil;
	}

	private static STR_HoldingReg createHoldingRegFromParsedData(
			String[] parsedData,

			STR_Geraet geraet) throws DescFileCorruptedException {
		STR_HoldingReg tempHReg = new STR_HoldingReg();

		tempHReg.setGeraet(geraet);
		tempHReg.setGeraeteKennung(geraet.getGeraeteKennung());
		tempHReg.setRevision(geraet.getRevision());
		tempHReg.setAnzKategorie(parsedData[2]);
		tempHReg.setBezeichnung(parsedData[3]);
		tempHReg.setKommentar(parsedData[4]);
		tempHReg.setRo(parsedData[5].equals("1"));
		tempHReg.setUeBerAnfang(parsedData[6]);
		tempHReg.setUeBerEnde(parsedData[7]);
		tempHReg.setaBerAnfang(parsedData[8]);
		tempHReg.setaBerEnde(parsedData[9]);
		tempHReg.setEinheit(parsedData[11]);

		try {
			tempHReg.setHrnr(Integer.valueOf(parsedData[0]));
			tempHReg.setNkS(Integer.valueOf(parsedData[10]));
			tempHReg.setMaskCSV(Integer.valueOf(parsedData[12]));

		} catch (NumberFormatException e) {
			throw new DescFileCorruptedException(EnumDescFileType.Coil,
					parsedData, null, null, 0);
		}

		return tempHReg;
	}

	private static STR_Geraet createGeraetFromParsedData(String[] parsedData,
			String revision, String comment) throws DescFileCorruptedException {
		STR_Geraet tempGeraet = new STR_Geraet();

		tempGeraet.setGeraeteKennung(parsedData[0]);

		tempGeraet.setGeraeteTyp(parsedData[1]);
		tempGeraet.setIndexName(parsedData[3]);
		tempGeraet.setInCSV(parsedData[4].equals("1"));
		tempGeraet.setSingleUse(parsedData[6]);
		tempGeraet.setComment(comment);

		tempGeraet.setRegisterList(new ArrayList<STR_HoldingReg>());
		tempGeraet.setCoilsList(new ArrayList<STR_Coil>());

		try {
			tempGeraet.setRevision(Integer.valueOf(revision));
			tempGeraet.setIdxAdr(Integer.valueOf(parsedData[2]));
			tempGeraet.setMaskCSVFilterGruppe(Integer.valueOf(parsedData[5]));

		} catch (NumberFormatException e) {
			throw new DescFileCorruptedException(EnumDescFileType.Coil,
					parsedData, null, null, 0);
		}

		return tempGeraet;
	}

	public static ArrayList<STR_Geraet> getController() {
		return controller;
	}
}
