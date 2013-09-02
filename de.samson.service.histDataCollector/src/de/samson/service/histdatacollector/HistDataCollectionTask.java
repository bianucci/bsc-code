package de.samson.service.histdatacollector;

import java.util.List;
import java.util.TimerTask;

import de.samson.service.database.DatabaseService;
import de.samson.service.database.ientities.histdata.HistDataSource;
import de.samson.service.database.ientities.histdata.IHistValue;

public class HistDataCollectionTask extends TimerTask {
	private double totband;
	private long maxTimeDiff;

	public HistDataCollectionTask(double totband, long maxTimeDiff) {
		this.totband = totband;
		this.maxTimeDiff = maxTimeDiff;
	}

	@Override
	public void run() {
		System.out.println("Iteration started");
		List<HistDataSource> allSourcesToCheck = DatabaseService
				.getAllDataSources();

		for (int i = 0; i < allSourcesToCheck.size(); i++) {
			HistDataSource source = allSourcesToCheck.get(i);

			DatabaseService.refreshEntity(source);

			if (currentValueDiffersLastHistoricalValue(source)) {
				DatabaseService.addHistValue(source);
			} else if (histValueIsTooOld(source)) {
				DatabaseService.addHistValue(source);
			}

			DatabaseService.persistEntity(source);
		}
		System.out.println("Iteration ended");
	}

	public boolean currentValueDiffersLastHistoricalValue(HistDataSource source) {

		double last = source.getLastHistoricalValue();
		double lo = source.getCurrentValue() - totband;
		double hi = source.getCurrentValue() + totband;

		if ((last <= lo) || (last >= hi))
			return true;
		else
			return false;
	}

	public boolean histValueIsTooOld(HistDataSource source) {
		int i = source.getHistoricalValues().size() - 1;

		IHistValue lastHistVal = source.getHistoricalValues().get(i);

		long histValTime = lastHistVal.getRecordTime().getTime();
		long currentTime = System.currentTimeMillis();
		if (histValTime + maxTimeDiff <= currentTime)
			return true;
		else
			return false;
	}

	public long getMaxTimeDiff() {
		return maxTimeDiff;
	}

	public double getTotband() {
		return totband;
	}
}
