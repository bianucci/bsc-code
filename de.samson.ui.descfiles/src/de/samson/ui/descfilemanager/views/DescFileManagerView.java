package de.samson.ui.descfilemanager.views;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import de.samson.service.database.DatabaseService;
import de.samson.service.database.entities.description.GeraeteDescription;

public class DescFileManagerView extends ViewPart {

	private DescFilesDBView v1;
	private DescFilesLocalView v2;
	private Button b;

	public DescFileManagerView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(3, false));

		v1 = new DescFilesDBView(parent, SWT.None);
		v1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		b = new Button(parent, SWT.PUSH);
		b.setText(" << ");
		b.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));

		v2 = new DescFilesLocalView(parent, SWT.None);
		v2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		b.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				List<GeraeteDescription> toPersist = v2.getCheckedElements();
				for (GeraeteDescription s : toPersist) {
					DatabaseService.addEntity(s);
				}
				v1.refreshTreeViewer();
			}
		});
	}

	@Override
	public void setFocus() {
	}

}
