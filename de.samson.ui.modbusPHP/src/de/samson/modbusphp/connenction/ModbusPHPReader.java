package de.samson.modbusphp.connenction;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Observable;

public class ModbusPHPReader extends Observable implements Runnable {

	private InputStream in;
	private byte[] buffer;
	private String lastInput = "";
	private ModbusPHPService m;

	public ModbusPHPReader(ModbusPHPService m, Socket s) {
		this.m = m;
		try {
			in = s.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			buffer = new byte[300];
			try {
				int i = in.read(buffer);
				lastInput += new String(buffer);
				if (i > 9) {
					setChanged();
					
					notifyObservers(lastInput);
					lastInput = "";
					
					Thread.sleep(5000);
					m.sendInfoCommand();//TODO
				} else if (i == -1) {
					break;
				}

			} catch (IOException | InterruptedException e) {
				System.err.println("socket unexpectedly closed ");
				break;
			}
		}
	}
}
