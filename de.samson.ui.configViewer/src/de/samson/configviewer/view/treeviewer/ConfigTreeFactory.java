package de.samson.configviewer.view.treeviewer;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TreeItem;

public class ConfigTreeFactory {

	private static TreeViewer tv;

	public static TreeViewer createTreeViewer(Composite parent) {
		tv = new TreeViewer(parent);

		tv.setContentProvider(new ConfigTreeContentProvider());
		tv.setLabelProvider(new ConfigTreeLabelProvider());

		Control control = tv.getControl();
		ConfigTreeMenuManager mm = new ConfigTreeMenuManager(tv);
		control.setMenu(mm.createContextMenu(control));

		tv.getTree().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				Point p = new Point(e.x, e.y);
				TreeItem item = tv.getTree().getItem(p);
				if (item == null)
					tv.getTree().deselectAll();
				super.mouseDown(e);
			}
		});

		return tv;
	}
}
