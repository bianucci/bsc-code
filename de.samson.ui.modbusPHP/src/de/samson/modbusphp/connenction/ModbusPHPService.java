package de.samson.modbusphp.connenction;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

import org.apache.commons.lang3.StringEscapeUtils;
import org.eclipse.swt.widgets.Display;

public class ModbusPHPService extends Observable implements Observer {

	Socket socket;

	int retry = 0;

	private OutputStream os;

	private ModbusPHPConfig config;
	private ModbusPHPServerProcess modbusPHPServerProcess;
	private ModbusPHPThreadProcess modbusPHPThreadProcess;
	private ModbusPHPReader modbusPHPReader;

	private String lastInfo = "Noch keine Informationen vorhanden!";

	public ModbusPHPService(ModbusPHPConfig config) {
		super();
		this.config = config;
	}

	public void connect() throws IOException {

		try {
			socket = new Socket(config.getProperty("ip"),
					Integer.valueOf(config.getProperty("port")));

			socket.setReuseAddress(true);
			os = socket.getOutputStream();

			modbusPHPReader = new ModbusPHPReader(this, socket);
			modbusPHPReader.addObserver(this);
			new Thread(modbusPHPReader).start();

			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			retry++;
			if (retry != 10)
				connect();
		}

	}

	public void disconnect() {
		if (socket != null)
			try {
				socket.getOutputStream().close();
				if
				(!socket.isConnected()) {
					socket.getInputStream().close();
					socket.close();
				}
				socket = null;
			} catch (IOException logOrIgnore) {

			}
	}

	public boolean modbusPHPIsRunning() {
		try {
			connect();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public void startUp() {
		modbusPHPServerProcess = new ModbusPHPServerProcess(new File(
				config.getProperty("startServer")));

		modbusPHPThreadProcess = new ModbusPHPThreadProcess(new File(
				config.getProperty("startThread")), "1");

		Thread threadServer = new Thread(modbusPHPServerProcess);
		threadServer.setDaemon(true);
		threadServer.start();

		Thread threadThread = new Thread(modbusPHPThreadProcess);
		threadThread.setDaemon(true);
		threadThread.start();
	}

	public void shutdown() throws IOException {
		os.write("shutdown\r\n".getBytes());
		os.flush();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void sendInfoCommand() {
		try {
			os.write("info\r\n".getBytes());
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		lastInfo = (String) arg;
		final String s = (String) arg;
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				setChanged();
				notifyObservers(cleanInformation(s));
			}
		});
	}

	private String cleanInformation(String lastInfo) {
		lastInfo = StringEscapeUtils.escapeJava(lastInfo);
		lastInfo = lastInfo.replace("\\u0000", "");
		lastInfo = lastInfo.replace("\\n\\r", "\n");
		lastInfo = lastInfo.replace("\\r\\n", "\n");
		return lastInfo;
	}

	public boolean isConnected() {
		if (socket != null)
			return socket.isConnected();
		else
			return false;
	}

	public String getLastInfo() {
		return lastInfo;

	}
}
