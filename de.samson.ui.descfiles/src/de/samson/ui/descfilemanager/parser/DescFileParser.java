package de.samson.ui.descfilemanager.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.samson.service.database.entities.description.CoilDesc;
import de.samson.service.database.entities.description.GeraeteDesc;
import de.samson.service.database.entities.description.HRegDesc;
import de.samson.service.database.entities.description.Masseinheit;
import de.samson.service.database.entities.description.WmwDesc;
import de.samson.service.database.entities.description.WmzDesc;
import de.samson.ui.descfilemanager.exceptions.DescDirectoryNotFoundEception;
import de.samson.ui.descfilemanager.exceptions.DescFileCorruptedException;
import de.samson.ui.descfilemanager.exceptions.DescFileParsingException;

public class DescFileParser {

	private static ArrayList<GeraeteDesc> controller;

	private static ArrayList<File> allControllerDirs;

	private static File controllerDir = null;

	public static void init(String rootDir)
			throws DescDirectoryNotFoundEception, IOException,
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
			throw new DescDirectoryNotFoundEception(rootDir);
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

				GeraeteDesc tempGeraet = createGeraetFromParsedData(
						str_geraetData, revCount, comment);

				for (String[] data : str_coilsData) {
					CoilDesc tempCoil = createCoilFromParsedData(data,
							tempGeraet);
					tempGeraet.getCoilsList().add(tempCoil);
				}

				for (String[] data : str_holdingregData) {
					HRegDesc tempHReg = createHoldingRegFromParsedData(data,
							tempGeraet);
					tempGeraet.getRegisterList().add(tempHReg);
				}

				List<WmzDesc> parsedJSONFile = parseJSONFile(next, tempGeraet);
				tempGeraet.setAllWMZ(parsedJSONFile);
				controller.add(tempGeraet);

			} catch (DescFileCorruptedException e) {
				errorOccured = true;
				if (e.getCorruptedFile() == null) {
					e.setCorruptedFile(next);
				}
				allCorruptedFiles.add(e);
				e.printStackTrace();
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

				File d = new File(parent.getAbsolutePath() + "\\"
						+ EnumDescFileType.JSON.getFileName());

				if (!allControllerDirs.contains(parent)) {
					if (a.exists() && b.exists() && c.exists() && d.exists())
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

	@SuppressWarnings({ "unchecked" })
	public static List<WmzDesc> parseJSONFile(File jsonFile, GeraeteDesc regler)
			throws JsonParseException, IOException, DescFileCorruptedException {

		jsonFile = new File(jsonFile.getAbsolutePath() + "\\"
				+ EnumDescFileType.JSON.getFileName());

		if (!jsonFile.exists())
			throw new FileNotFoundException(jsonFile.getAbsolutePath());

		ObjectMapper mapper = new ObjectMapper();

		List<WmzDesc> l = new ArrayList<WmzDesc>();
		WmzDesc wmz;
		WmwDesc wmw;

		// ALL JSON DATA
		Map<String, Object> userData = mapper.readValue(jsonFile, Map.class);

		// ALL WMZ
		Map<String, Object> mapAllWMZ = ((Map<String, Object>) userData
				.get("TabWMZ"));
		if (mapAllWMZ == null)
			throw new DescFileCorruptedException(EnumDescFileType.JSON,
					new String[] {}, "", jsonFile, 0);
		Object[] arrayAllWMZ = mapAllWMZ.values().toArray();

		for (int i = 0; i < arrayAllWMZ.length; i++) {
			wmz = new WmzDesc();
			wmz.setWmwList(new ArrayList<WmwDesc>());

			// CURRENT WMZ
			LinkedHashMap<String, Object> mapWMZ = (LinkedHashMap<String, Object>) arrayAllWMZ[i];
			wmz.setBezeichnung((String) mapAllWMZ.keySet().toArray()[i]);

			// ALL WMW FOR THIS WMZ
			Object[] arrayAllWMW = mapWMZ.values().toArray();

			// ITERATE THROUGH ALL WMW
			for (int j = 0; j < arrayAllWMW.length; j++) {
				wmw = new WmwDesc();
				wmw.setMasseinheiten(new ArrayList<Masseinheit>());
				wmw.setWerteRegister(new ArrayList<HRegDesc>());

				// CURRENT WMW
				Map<String, Object> mapWMW = (Map<String, Object>) arrayAllWMW[j];
				wmw.setCategory((String) mapWMZ.keySet().toArray()[j]);

				// SET EINHEIT REGISTER FOR WMZ
				Map<String, Object> einheit = (Map<String, Object>) mapWMW
						.get("Einheit");
				int einReg = (int) einheit.get("IntRegister");
				for (HRegDesc sreg : regler.getRegisterList()) {
					if (sreg.getHrnr() == einReg) {
						wmw.setRegisterEinheitIsStoredIn(sreg);
						sreg.setWmwThisRegisterStoresMasseinheitKeyFor(wmw);
						break;
					}
				}

				// SET MASzEINHEIT FOR WMZ
				Map<String, Object> tabEinheit = (Map<String, Object>) einheit
						.get("TabEinheiten");
				for (int k = 0; k <= tabEinheit.keySet().size() - 1; k++) {
					String key = (String) tabEinheit.keySet().toArray()[k];
					String value = (String) tabEinheit.get(key);

					Masseinheit m = new Masseinheit();
					m.setKeyEinheit(key);
					m.setValueEinheit(value);
					m.setWmw(wmw);
					wmw.getMasseinheiten().add(m);
				}

				// SET WERTE REGISTER FOR WMZ
				ArrayList<Object> wertigkeiten = (ArrayList<Object>) mapWMW
						.get("Wertigkeiten");
				for (Object o : wertigkeiten) {
					Map<String, Object> dp = (Map<String, Object>) o;
					double faktor = (double) dp.get("DblWertigkeit");
					int reg = (int) dp.get("IntRegister");
					for (HRegDesc sreg : regler.getRegisterList()) {
						if (sreg.getHrnr() == reg) {
							sreg.setFaktor(faktor);
							wmw.getWerteRegister().add(sreg);
							sreg.setWmwThisRegisterStoresValueFor(wmw);
							break;
						}
					}
				}
				wmz.getWmwList().add(wmw);
				wmw.setWmz(wmz);
			}
			wmz.setGeraeteDesc(regler);
			l.add(wmz);
		}
		return l;
	}

	private static CoilDesc createCoilFromParsedData(String[] parsedData,
			GeraeteDesc geraet) throws DescFileCorruptedException {

		CoilDesc tempCoil = new CoilDesc();

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

	private static HRegDesc createHoldingRegFromParsedData(String[] parsedData,
			GeraeteDesc geraet) throws DescFileCorruptedException {
		HRegDesc tempHReg = new HRegDesc();

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

	private static GeraeteDesc createGeraetFromParsedData(String[] parsedData,
			String revision, String comment) throws DescFileCorruptedException {
		GeraeteDesc tempGeraet = new GeraeteDesc();

		tempGeraet.setGeraeteKennung(parsedData[0]);

		tempGeraet.setGeraeteTyp(parsedData[1]);
		tempGeraet.setIndexName(parsedData[3]);
		tempGeraet.setInCSV(parsedData[4].equals("1"));
		tempGeraet.setSingleUse(parsedData[6]);
		tempGeraet.setComment(comment);

		tempGeraet.setRegisterList(new ArrayList<HRegDesc>());
		tempGeraet.setCoilsList(new ArrayList<CoilDesc>());

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

	public static ArrayList<GeraeteDesc> getController() {
		return controller;
	}
}
