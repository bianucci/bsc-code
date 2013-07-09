package de.samson.service.database.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import de.samson.service.database.Activator;

public class Initializer extends AbstractPreferenceInitializer {

	public Initializer() {
	}

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault("IP", "127.0.0.1");
		store.setDefault("PORT", 3306);
		store.setDefault("USERNAME", "root");
		store.setDefault("PASSWORD", "");
	}

}
