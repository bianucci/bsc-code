package de.samson.ui.descfilemanager.views;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;

import de.samson.service.database.entities.description.GeraeteDescription;
import de.samson.ui.descfilemanager.exceptions.DescFileParsingException;
import de.samson.ui.descfilemanager.exceptions.DescDirectoryNotFoundEception;
import de.samson.ui.descfilemanager.parser.DescFileParser;
import de.samson.ui.descfilemanager.reglerTreeFactory.DescFileTreeViewerFactory;
import de.samson.ui.descfilemanager.reglerTreeFactory.ReglerRevisionGroup;

public class DescFilesLocalView extends Composite {

	private Group g;
	private Button b;
	private Text t;
	private CheckboxTreeViewer tv;
	private Tree tree;
	private List<GeraeteDescription> treeData;

	public DescFilesLocalView(Composite parent, int style) {
		super(parent, style);
		createPartControl();
	}

	public void createPartControl() {
		this.setLayout(new GridLayout(2, false));

		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		layoutData.horizontalSpan = 2;

		g = new Group(this, SWT.NONE);
		g.setText("Lokales Verzeichnis");
		g.setLayoutData(layoutData);
		g.setLayout(new GridLayout(1, false));

		t = new Text(this, SWT.BORDER);
		t.setLayoutData(new GridData(SWT.FILL, SWT.END, true, false));

		tv = DescFileTreeViewerFactory.createTreeViewer(g);
		tree = tv.getTree();
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		tv.setInput(new ArrayList<GeraeteDescription>());

		b = new Button(this, SWT.PUSH);
		b.setText(" ... ");
		b.setLayoutData(new GridData(SWT.RIGHT, SWT.END, false, false));

		b.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				String temp;
				if ((temp = showDirectoryDialog()) != null) {
					t.setText(temp);
					try {
						DescFileParser.init(temp);
						treeData = DescFileParser.getController();
						tv.setInput(treeData);
					} catch (DescDirectoryNotFoundEception | IOException
							| DescFileParsingException e1) {
						e1.printStackTrace();
						tv.setInput(new ArrayList<GeraeteDescription>());
					}
				}
			}

		});
	}

	private String showDirectoryDialog() {
		DirectoryDialog dialog = new DirectoryDialog(this.getShell());
		if (t.getText() == null) {
			dialog.setFilterPath("c:\\");
		}
		return dialog.open();
	}

	public void refreshTreeViewer() {
		tv.refresh();
	}

	public List<GeraeteDescription> getCheckedElements() {
		List<GeraeteDescription> checked = new ArrayList<GeraeteDescription>();
		for (Object o : tv.getCheckedElements()) {
			if (o instanceof GeraeteDescription) {
				checked.add((GeraeteDescription) o);
			}
			if (o instanceof ReglerRevisionGroup) {
				ReglerRevisionGroup g = (ReglerRevisionGroup) o;
				if (g.size() == 1)
					checked.add(g.get(0));
			}
		}
		return checked;
	}

}
