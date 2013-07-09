package de.samson.modbusphp.datapointwriter;

import org.eclipse.jface.preference.IPreferenceStore;

import de.samson.service.directregleraccess.Activator;
import de.samson.service.directregleraccess.DataPointWriter;
import de.samson.service.directregleraccess.DataPointWriterProcess;

public class DataPointWriterService {
	String ip;
	int port;
	String pathPHPFile;
	String pathPHPWIN;

	private static DataPointWriter dpw;

	public DataPointWriterService() {
		super();

		IPreferenceStore store = Activator.getDefault().getPreferenceStore();

		ip = store.getString("IP");
		port = store.getInt("PORT");
		pathPHPFile = store.getString("DIRECTEDITORPHP");
		pathPHPWIN = store.getString("PHPWIN");

		DataPointWriterProcess dpwp = new DataPointWriterProcess(pathPHPFile,
				pathPHPWIN);
		Thread t = new Thread(dpwp);
		t.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		dpw = new DataPointWriter(ip, port);
	}

	public static void readCoilValue(String ip, String station, String dpNr) {
		dpw.readCoilValue(ip, station, dpNr);
	}

	public static void writeCoilValue(String ip, String station, String dpNr,
			boolean value) {
		dpw.writeCoilValue(ip, station, dpNr, value);
	}

	public static void readRegValue(String ip, String station, String dpNr) {
		dpw.readRegValue(ip, station, dpNr);
	}

	public static void writeRegValue(String ip, String station, String dpNr,
			String value) {
		dpw.writeRegValue(ip, station, dpNr, value);
	}
}
