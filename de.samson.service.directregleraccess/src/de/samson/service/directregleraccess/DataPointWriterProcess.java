package de.samson.service.directregleraccess;

import java.io.File;
import java.io.IOException;
import java.util.Observable;

public class DataPointWriterProcess extends Observable implements Runnable {

	private Process p;
	private String pathPHPFile;
	private String pathPHPWIN;
	private ProcessBuilder pb;

	public DataPointWriterProcess(String pathPHPFile, String pathPHPWIN) {
		super();
		this.pathPHPFile = pathPHPFile;
		this.pathPHPWIN = pathPHPWIN;
	}

	@Override
	public void run() {
		try {
			File file = new File(pathPHPFile).getParentFile();
			pb = new ProcessBuilder(pathPHPWIN, pathPHPFile);
			pb.directory(file);

			p = pb.start();

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
