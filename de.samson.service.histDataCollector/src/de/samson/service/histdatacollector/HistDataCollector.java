package de.samson.service.histdatacollector;

import java.util.Timer;

import org.eclipse.jface.preference.IPreferenceStore;

public class HistDataCollector {
	private static HistDataCollector hdc = null;
	private static IPreferenceStore preferences;
	private Timer timer;
	private boolean timerIsRunning = false;
	private HistDataCollectionTask task;

	public static HistDataCollector getInstance() {
		if (hdc == null)
			hdc = new HistDataCollector();
		return hdc;
	}

	protected HistDataCollector() {
		preferences = getPreferencesFromActivator();
	}

	protected IPreferenceStore getPreferencesFromActivator() {
		return Activator.getDefault().getPreferenceStore();

	}

	public void startCollecting() {
		if (!timerIsRunning) {
			timer = new Timer("Hist Data Collector");
			task = new HistDataCollectionTask(preferences.getDouble("TOTBAND"),
					preferences.getInt("MAX_TIME_DIFF"));

			long i = preferences.getInt("INTERVALL");
			timer.schedule(task, i, i);
			timerIsRunning = true;
		}
	}
	
	public void stopCollecting() {
		if (timerIsRunning) {
			timer.cancel();
			timer = null;
			task = null;
			timerIsRunning = false;
		}
	}

	public boolean isCollecting() {
		return timerIsRunning;
	}

	public Timer getTimer() {
		return timer;
	}

	public HistDataCollectionTask getTask() {
		return task;
	}
	
	
	
}
