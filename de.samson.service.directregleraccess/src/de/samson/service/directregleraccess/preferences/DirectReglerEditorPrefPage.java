package de.samson.service.directregleraccess.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import de.samson.service.directregleraccess.Activator;

public class DirectReglerEditorPrefPage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	public DirectReglerEditorPrefPage() {
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

		addField(new FileFieldEditor("DIRECTEDITORPHP", "&direct_editor.php:",
				parent));

		addField(new FileFieldEditor("PHPWIN", "&win-php.exe:", parent));

		addField(new StringFieldEditor("IP", "&IP-Adresse: ", parent));
		addField(new IntegerFieldEditor("PORT", "&Port: ", parent));
	}

}
