package de.samson.ui.descfilemanager.views;

import java.util.List;
import java.util.Vector;

import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Tree;

import de.samson.service.database.DatabaseService;
import de.samson.service.database.entities.description.GeraeteDesc;
import de.samson.ui.descfilemanager.reglerTreeFactory.DescFileTreeViewerFactory;
import de.samson.ui.descfilemanager.reglerTreeFactory.ReglerRevisionGroup;

public class DescFilesDBView extends Composite {

	private Group g;
	private Button b;
	private CheckboxTreeViewer tv;
	private Tree tree;
	private List<GeraeteDesc> treeData;

	public DescFilesDBView(Composite parent, int style) {
		super(parent, style);

		createPartControl();
	}

	public void createPartControl() {
		this.setLayout(new GridLayout(1, false));

		g = new Group(this, SWT.NONE);
		g.setText("Datenbank");
		g.setLayout(new GridLayout(1, false));
		g.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		tv = DescFileTreeViewerFactory.createTreeViewer(g);
		tree = tv.getTree();
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		treeData = DatabaseService.getAllSTR_Geraet();
		tv.setInput(treeData);

		b = new Button(this, SWT.PUSH);
		b.setLayoutData(new GridData(SWT.FILL, SWT.END, true, false));
		b.setText("Ausgewählte Elemente löschen");
		b.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				List<GeraeteDesc> toDelete = getCheckedElements();
				for (GeraeteDesc s : toDelete)
					DatabaseService.removeEntity(s);
				refreshTreeViewer();
			}
		});
	}

	public List<GeraeteDesc> getCheckedElements() {
		List<GeraeteDesc> checked = new Vector<GeraeteDesc>();
		for (Object o : tv.getCheckedElements()) {
			if (o instanceof GeraeteDesc) {
				checked.add((GeraeteDesc) o);
			}
			if (o instanceof ReglerRevisionGroup) {
				ReglerRevisionGroup g = (ReglerRevisionGroup) o;
				if (g.size() == 1)
					checked.add(g.get(0));
			}
		}
		return checked;
	}

	public void refreshTreeViewer() {
		tv.setInput(DatabaseService.getAllSTR_Geraet());
		tv.refresh();
	}
}
