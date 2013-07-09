package de.samson.configviewer.view;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.ViewPart;

import de.samson.configviewer.PartID;
import de.samson.configviewer.view.treeviewer.ConfigTreeFactory;
import de.samson.service.database.DatabaseService;

public class View extends ViewPart {
	public View() {
	}

	private TreeViewer tv;

	@Override
	public void createPartControl(Composite parent) {
		tv = ConfigTreeFactory.createTreeViewer(parent);
		getSite().setSelectionProvider(getTv());
		getTv().setInput(DatabaseService.getStandortList());
		hookDoubleClickCommand();
	}
	
	

	private void hookDoubleClickCommand() {
		getTv().addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {

				IHandlerService handlerService = (IHandlerService) getSite()
						.getService(IHandlerService.class);
				try {
					handlerService.executeCommand(
							PartID.Command_Open_Editor_ID, null);

				} catch (Exception ex) {
					throw new RuntimeException(
							"de.samson.configViewer.openEditor not found");
				}
			}
		});
	}

	public void setFocus() {
		getTv().getControl().setFocus();
	}

	public TreeViewer getTv() {
		return tv;
	}
}