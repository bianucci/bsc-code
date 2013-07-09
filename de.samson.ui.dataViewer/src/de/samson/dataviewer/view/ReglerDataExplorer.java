package de.samson.dataviewer.view;

import java.util.Observable;
import java.util.Observer;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.ViewPart;

import de.samson.dataviewer.view.treeviewer.TreeViewerFactory;
import de.samson.service.database.DatabaseService;

public class ReglerDataExplorer extends ViewPart implements Observer{
	private TreeViewer tv;
	private int time = 5000;

	@Override
	public void createPartControl(Composite parent) {
		tv = TreeViewerFactory.createTV(parent);
		getSite().setSelectionProvider(tv);
		tv.setInput(DatabaseService.getStandortList());
		Runnable timer = new Runnable() {

			public void run() {
				Display.getDefault().timerExec(time, this);
				tv.refresh(true);
			}
		};
		Display.getDefault().timerExec(time, timer);
		hookDoubleClickCommand();
	}

	private void hookDoubleClickCommand() {
		tv.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {

				IHandlerService handlerService = (IHandlerService) getSite()
						.getService(IHandlerService.class);
				try {
					handlerService.executeCommand(
							"de.samson.dataViewer.openEditor", null);

				} catch (Exception ex) {
					throw new RuntimeException(
							"de.samson.dataViewer.openEditor not found");
				}
			}
		});
	}

	public void setFocus() {
		tv.getControl().setFocus();
	}

	@Override
	public void update(Observable o, Object arg) {
		
	}
}