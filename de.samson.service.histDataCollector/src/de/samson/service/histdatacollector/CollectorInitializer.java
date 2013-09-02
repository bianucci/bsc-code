package de.samson.service.histdatacollector;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

public class CollectorInitializer extends AbstractPreferenceInitializer {

	public CollectorInitializer() {
	}

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault("INTERVALL", 10000);
		store.setDefault("TOTBAND", 1);
		store.setDefault("MAX_TIME_DIFF", 3600);
	}

}
