package de.samson.service.directregleraccess;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.eclipse.jface.preference.IPreferenceStore;

import de.samson.modbusphp.datapointwriter.exception.WriteDatapointFailedException;

public class DataPointWriter {

	private Socket s;
	private OutputStream os;
	private InputStream in;
	IPreferenceStore store;

	public DataPointWriter(String ip, int port) {
		try {
			s = new Socket(ip, port);
			in = s.getInputStream();
			os = s.getOutputStream();

			do {
				in.read();
			} while (in.available() != 0);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeRegValue(String ip, String station, String dpNr,
			String value) throws WriteDatapointFailedException {
		String command = ip + ";" + station + ";" + "register;" + "write;"
				+ dpNr + ";" + value + System.getProperty("line.separator");
		writeToDataPoint(command, value);
	}

	public void writeCoilValue(String ip, String station, String dpNr,
			boolean value) throws WriteDatapointFailedException {
		String command = ip + ";" + station + ";" + "coil;" + "write;" + dpNr
				+ ";" + String.valueOf(value).toUpperCase()
				+ System.getProperty("line.separator");
		if (value)
			writeToDataPoint(command, "TRUE");
		else
			writeToDataPoint(command, "FALSE");
	}

	public String readRegValue(String ip, String station, String dpNr) {
		String command = ip + ";" + station + ";" + "register;" + "read;"
				+ dpNr + ";0" + System.getProperty("line.separator");
		return readFromDataPoint(command);
	}

	public String readCoilValue(String ip, String station, String dpNr) {
		String command = ip + ";" + station + ";" + "coil;" + "read;" + dpNr
				+ ";0" + System.getProperty("line.separator");
		return readFromDataPoint(command);
	}

	private void writeToDataPoint(String command, String compareValue) throws WriteDatapointFailedException {
		if (s.isConnected()) {
			try {
				os.write(command.getBytes());
				os.flush();

				String s = "";
				do {
					byte[] buffer = new byte[1000];
					in.read(buffer);
					s += new String(buffer);
				} while (in.available() != 0);

				String existingValue = s.split(":")[1].trim();
				if(!existingValue.equals(compareValue))
					throw new WriteDatapointFailedException();

				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String readFromDataPoint(String command) {
		if (s.isConnected()) {
			try {
				os.write(command.getBytes());
				os.flush();

				String s = "";
				do {
					byte[] buffer = new byte[1000];
					in.read(buffer);
					s += new String(buffer);
				} while (in.available() != 0);

				return s.split(":")[1].trim();

			} catch (IOException e) {
				e.printStackTrace();
			}
			return "Error readind dp";
		}
		return "Socket Error";
	}

	public void close() {
		try {
			this.in.close();
			this.os.close();
			this.s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
