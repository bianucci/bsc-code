package de.samson.ui.modbusphp;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import de.samson.modbusphp.Activator;

public class ModbusPHPInitializer extends AbstractPreferenceInitializer {

	public ModbusPHPInitializer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault("IP", "localhost");

		store.setDefault("PORT", "5566");

		store.setDefault("STARTSERVERPHP",
				"C:\\xampp\\htdocs\\ModbusPHP\\start_server.php");

		store.setDefault("STARTTHREADPHP",
				"C:\\xampp\\htdocs\\ModbusPHP\\start_thread.php");

		store.setDefault("PHPWIN", "C:\\xampp\\php\\php-win.exe");

	}

}
