package de.samson.modbusphp.connenction;
import java.io.File;
import java.io.IOException;
import java.util.Observable;

public class ModbusPHPServerProcess extends Observable implements Runnable {

	private File phpFile;
	private Process p;

	public ModbusPHPServerProcess(File phpFile) {
		super();
		this.phpFile = phpFile;
	}

	@Override
	public void run() {
		try {
			p = new ProcessBuilder("C:\\xampp\\php\\php-win.exe",
					phpFile.getAbsolutePath()).directory(
					new File("c:\\xampp\\htdocs\\ModbusPHP")).start();

			p.getErrorStream().close();
			p.getInputStream().close();
			p.getOutputStream().close();
			p.waitFor();

			setChanged();
			notifyObservers();

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void forceExit() {
		p.destroy();
	}
}
