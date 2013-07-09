package de.samson.dataviewer.view.treeviewer;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.samson.service.database.entities.config.ReglerConfig;

public class TreeViewerFactory {

	private static TreeViewer tv;

	public static TreeViewer createTV(Composite parent) {
		tv = new TreeViewer(parent, SWT.NONE);
		tv.setContentProvider(new DataTreeContentProvider());
		tv.setLabelProvider(new DataTreeLabelProvider());
		return tv;
	}

	public ReglerConfig getSelectedRegler() {
		IStructuredSelection s = (IStructuredSelection) tv.getSelection();
		if (s.getFirstElement() instanceof ReglerConfig) {
			return (ReglerConfig) s.getFirstElement();
		} else
			return null;
	}

}
