package de.samson.service.histdatacollector;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.samson.service.database.DatabaseService;
import de.samson.service.database.entities.histdata.HistDataSource;
import de.samson.service.database.entities.histdata.HistValue;

public class HistDataObserver {
	private class HistDataCollectionTask extends TimerTask {
		List<HistDataSource> allSourcesToCheck;

		public HistDataCollectionTask(List<HistDataSource> allSourcesToCheck) {
			super();
			this.allSourcesToCheck = allSourcesToCheck;
		}

		@Override
		public void run() {
			for (int i = 0; i < allSourcesToCheck.size(); i++) {
				HistDataSource source = allSourcesToCheck.get(i);
				double last = source.getLastHistoricalValue();
				double currLo = source.getCurrentValue() - source.getTotband();
				double currHi = source.getCurrentValue() + source.getTotband();

				if ((currLo > last) || (currHi < last)) {
					HistValue newHistVal = new HistValue();
					newHistVal.setData_source(source);
					newHistVal
							.setRec_time(new Date(System.currentTimeMillis()));
					newHistVal.setValue(last);
					source.getHistoricalValues().add(newHistVal);
					DatabaseService.persistEntity(source);
				}
			}

		}
	}

	private static HistDataObserver hdo = null;
	private Timer t;
	private boolean timerIsRunning = false;
	private HistDataCollectionTask task;

	public static HistDataObserver getInstance() {
		if (hdo == null)
			hdo = new HistDataObserver();
		return hdo;
	}

	private HistDataObserver() {
	}

	public void startObserving(List<HistDataSource> toCheck) {
		if (!timerIsRunning) {
			t = new Timer("Hist Data Collector");
			task = new HistDataCollectionTask(toCheck);

			t.schedule(task, 2000, 1000);
			timerIsRunning = true;
		}
	}

	public void stopObserving() {
		t.cancel();
		timerIsRunning = false;
	}

	public boolean isObserving() {
		return timerIsRunning;
	}
}
