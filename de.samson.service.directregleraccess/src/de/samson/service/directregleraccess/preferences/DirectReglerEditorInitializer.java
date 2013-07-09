package de.samson.service.directregleraccess.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import de.samson.service.directregleraccess.Activator;

public class DirectReglerEditorInitializer extends
		AbstractPreferenceInitializer {

	public DirectReglerEditorInitializer() {
	}

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault("IP", "localhost");
		store.setDefault("PORT", "5567");
		store.setDefault("DIRECTEDITORPHP", "C:\\xampp\\htdocs\\ModbusPHP\\socketss.php");
		store.setDefault("PHPWIN", "C:\\xampp\\php\\php-win.exe");
	}

}
