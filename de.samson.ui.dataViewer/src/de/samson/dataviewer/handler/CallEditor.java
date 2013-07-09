package de.samson.dataviewer.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import de.samson.dataviewer.PartID;
import de.samson.dataviewer.editor.ReglerDataEditorInput;
import de.samson.dataviewer.view.ReglerDataExplorer;
import de.samson.service.database.entities.config.ReglerConfig;

public class CallEditor extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		System.out.println("called");

		// Get the view
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		IWorkbenchPage page = window.getActivePage();
		ReglerDataExplorer view = (ReglerDataExplorer) page
				.findView(PartID.View_Regler_Data_ID);

		// Get the selection
		ISelection selection = view.getSite().getSelectionProvider()
				.getSelection();

		if (selection != null && selection instanceof IStructuredSelection) {
			Object obj = ((IStructuredSelection) selection).getFirstElement();

			// If we had a selection lets open the editor
			if (obj != null) {
				ReglerConfig rc = (ReglerConfig) obj;
				ReglerDataEditorInput input = new ReglerDataEditorInput(
						rc.getnId());
				try {
					page.openEditor(input, PartID.Editor_Regler_Data_ID);

				} catch (PartInitException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return null;
	}

}
