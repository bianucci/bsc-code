package de.samson.service.histdatacollector;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.samson.service.database.DatabaseService;
import de.samson.service.database.entities.histdata.HistDataSource;
import de.samson.service.database.entities.histdata.HistValue;

public class HistDataCollector {
	private class HistDataCollectionTask extends TimerTask {
		List<HistDataSource> allSourcesToCheck;

		@Override
		public void run() {
			System.out.println("Iteration started");
			allSourcesToCheck = DatabaseService.getAllDataSources();

			for (int i = 0; i < allSourcesToCheck.size(); i++) {
				HistDataSource source = allSourcesToCheck.get(i);

				// TODO with lots of histvalues refresh lot. get reference to
				// real data provider e.g. coilconfig and refresh dat mofo
				// directly
				DatabaseService.refreshEntity(source);

				double last = source.getLastHistoricalValue();
				double currLo;
				currLo = source.getCurrentValue() - source.getTotband();

				double currHi = source.getCurrentValue() + source.getTotband();

				HistValue newHistVal = new HistValue();

				newHistVal.setValue(0);
				if ((last <= currLo) || (last >= currHi)) {
					newHistVal.setData_source(source);
					newHistVal
							.setRec_time(new Date(System.currentTimeMillis()));

					newHistVal.setValue(source.getCurrentValue());
					source.getHistoricalValues().add(newHistVal);

					DatabaseService.persistEntity(source);
					System.out.println("New hist value "
							+ newHistVal.getValue()
							+ " added for HistDataSource " + source.getId());
				}
			}
			System.out.println("Iteration ended");
		}
	}

	private static HistDataCollector hdc = null;
	private Timer t;
	private boolean timerIsRunning = false;
	private HistDataCollectionTask task;

	public static HistDataCollector getInstance() {
		if (hdc == null)
			hdc = new HistDataCollector();
		return hdc;
	}

	private HistDataCollector() {
	}

	public void startCollecting() {
		if (!timerIsRunning) {
			t = new Timer("Hist Data Collector");
			task = new HistDataCollectionTask();

			t.schedule(task, 2000, 6000);
			timerIsRunning = true;
		}
	}

	public void stopCollecting() {
		t.cancel();
		timerIsRunning = false;
	}

	public boolean isCollecting() {
		return timerIsRunning;
	}
}
