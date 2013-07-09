package de.samson.ui.modbusphp;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import de.samson.modbusphp.Activator;

public class ModbusPHPPrefPage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	public ModbusPHPPrefPage() {
		super(GRID);
	}

	@Override
	public void init(IWorkbench workbench) {
		IPreferenceStore preferenceStore = Activator.getDefault()
				.getPreferenceStore();
		setPreferenceStore(preferenceStore);
	}

	@Override
	protected void createFieldEditors() {
		Composite parent = getFieldEditorParent();

		addField(new FileFieldEditor("PHPWIN", "&php-win.exe:", parent));
		addField(new FileFieldEditor("STARTSERVERPHP", "&start_server.php:",
				parent));
		addField(new FileFieldEditor("STARTTHREADPHP", "&start_thread.php:",
				parent));
		addField(new StringFieldEditor("IP", "&IP-Adresse: ", parent));
		addField(new StringFieldEditor("PORT", "&Port: ", parent));
	}
}
