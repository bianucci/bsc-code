package de.samson.ui.descfilemanager.reglerTreeFactory;

import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class DescFileTreeViewerFactory {

	public static CheckboxTreeViewer createTreeViewer(Composite parent) {

		ReglerTreeContentProvider dbDataCP = new ReglerTreeContentProvider();
		ReglerTreeLabelProvider dbDataLP = new ReglerTreeLabelProvider();

		CheckboxTreeViewer tv = new CheckboxTreeViewer(parent, SWT.BORDER);

		tv.setContentProvider(dbDataCP);
		tv.setLabelProvider(dbDataLP);

		return tv;
	}

}
