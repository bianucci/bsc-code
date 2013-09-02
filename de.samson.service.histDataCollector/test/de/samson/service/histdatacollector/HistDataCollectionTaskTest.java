package de.samson.service.histdatacollector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import de.samson.service.database.ientities.histdata.HistDataSource;
import de.samson.service.database.ientities.histdata.IHistValue;

public class HistDataCollectionTaskTest {

	private class MockedDataSource extends HistDataSource {

		double current;
		MockedHistValue last;

		public MockedDataSource(double current) {
			this.current = current;
			this.last = new MockedHistValue(1, new Date(
					System.currentTimeMillis()));
		}
		
		public MockedDataSource(MockedHistValue last){
			this.last = last;
			current = 1;
		}

		@Override
		public double getLastHistoricalValue() {
			return last.getValue();
		}

		@Override
		public double getCurrentValue() {
			return current;
		}

		@Override
		public List<IHistValue> getHistoricalValues() {
			List<IHistValue> a = new ArrayList<>();
			a.add(last);
			return a;
		}

		@Override
		public String getyAxisName() {
			return "";
		}

	}

	private double totband = 2.22;
	long maxTimeDiff = 99;

	Date now = new Date(System.currentTimeMillis());
	Date before1h = new Date(System.currentTimeMillis() - 360000);

	@Test
	public void testHistDataCollectionTask() {
		HistDataCollectionTask task = null;
		assertNull(task);

		task = new HistDataCollectionTask(totband, maxTimeDiff);
		assertNotNull(task);
		assertEquals(maxTimeDiff, task.getMaxTimeDiff());
	}

	@Test
	public void testCurrentValueDiffersLastHistoricalValue() {
		HistDataCollectionTask task;
		MockedHistValue hv;
		MockedDataSource ds;

		double anyRecordedValue = 12;
		hv = new MockedHistValue(anyRecordedValue, now);
		ds = new MockedDataSource(hv);

		totband = 11.99;
		task = new HistDataCollectionTask(totband, maxTimeDiff);

		assertFalse(task.currentValueDiffersLastHistoricalValue(ds));

		anyRecordedValue += totband;
		hv = new MockedHistValue(anyRecordedValue, now);
		ds = new MockedDataSource(hv);
		assertTrue(task.currentValueDiffersLastHistoricalValue(ds));
	}

	@Test
	public void testHistValueIsTooOld() {
		HistDataCollectionTask task;
		MockedHistValue hv;
		MockedDataSource ds;

		double anyRecordedValue = 12;
		hv = new MockedHistValue(anyRecordedValue, now);
		ds = new MockedDataSource(hv);

		task = new HistDataCollectionTask(totband, maxTimeDiff);

		assertFalse(task.histValueIsTooOld(ds));

		hv = new MockedHistValue(anyRecordedValue, before1h);
		ds = new MockedDataSource(hv);
		assertTrue(task.histValueIsTooOld(ds));
	}

}
