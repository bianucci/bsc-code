package de.samson.service.histdatacollector;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.wb.swt.DoubleFieldEditor;

public class CollectorPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	public CollectorPreferencePage() {
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

		addField(new IntegerFieldEditor("INTERVALL",
				"Intervalldauer in Millisek.", parent));

		addField(new DoubleFieldEditor("TOTBAND", "Totband (Absolutwert)",
				parent));

		addField(new IntegerFieldEditor("MAX_TIME_DIFF",
				"Max. Alter des letzten hist. Wertes in Millisek.", parent));
	}

}
