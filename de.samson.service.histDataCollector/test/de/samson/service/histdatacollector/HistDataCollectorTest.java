/**
 * 
 */
package de.samson.service.histdatacollector;

import static org.junit.Assert.*;
import org.eclipse.jface.preference.IPreferenceStore;
import org.junit.Test;

/**
 * @author cbianucci
 * 
 */
public class HistDataCollectorTest {

	private class MockedHistDataCollector extends HistDataCollector {
		@Override
		protected IPreferenceStore getPreferencesFromActivator() {
			return new MockedPreferenceStore();
		}
	}

	private class MockedPreferenceStore extends AbstractPreferenceStore {
		@Override
		public double getDouble(String name) {
			if (name.equals("TOTBAND"))
				return 1;
			return 1;
		}

		@Override
		public int getInt(String name) {
			if (name.equals("INTERVALL"))
				return 100;
			if (name.equals("MAX_TIME_DIFF"))
				return 100;
			return 1;
		}
	}

	@Test
	public void testStartCollecting() {
		MockedHistDataCollector hdc = new MockedHistDataCollector();
		assertNull(hdc.getTimer());
		assertNull(hdc.getTask());

		hdc.startCollecting();

		assertNotNull(hdc.getTimer());
		assertNotNull(hdc.getTask());
	}

	@Test
	public void testStopCollecting() {
		MockedHistDataCollector hdc = new MockedHistDataCollector();

		hdc.startCollecting();

		hdc.stopCollecting();
		assertNull(hdc.getTimer());
		assertNull(hdc.getTask());
	}

	@Test
	public void testIsCollecting() {
		MockedHistDataCollector hdc = new MockedHistDataCollector();
		assertFalse(hdc.isCollecting());

		hdc.startCollecting();
		assertTrue(hdc.isCollecting());
	}

}
