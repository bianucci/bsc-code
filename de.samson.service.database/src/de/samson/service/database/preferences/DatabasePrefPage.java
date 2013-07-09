package de.samson.service.database.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import de.samson.service.database.Activator;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.preference.IntegerFieldEditor;

public class DatabasePrefPage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	public DatabasePrefPage() {
		super(GRID);
	}

	@Override
	public void init(IWorkbench workbench) {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		setPreferenceStore(store);
	}

	@Override
	protected void createFieldEditors() {
		Composite parent = getFieldEditorParent();
		addField(new StringFieldEditor("IP", "&IP:", parent));
		addField(new IntegerFieldEditor("PORT", "&Port:", parent));
		addField(new StringFieldEditor("USERNAME", "&Benutzername:", parent));
		addField(new StringFieldEditor("PASSWORD", "Passwort:", parent));
	}
}
