package de.samson.configviewer.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import de.samson.configviewer.PartID;
import de.samson.configviewer.editor.gateway.GatewayConfigEditorInput;
import de.samson.configviewer.editor.regler.ReglerConfigEditorInput;
import de.samson.configviewer.editor.standort.StandortEditorInput;
import de.samson.configviewer.view.View;
import de.samson.service.database.entities.config.GatewayConfig;
import de.samson.service.database.entities.config.ReglerConfig;
import de.samson.service.database.entities.config.Standort;

public class CallEditor extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		System.out.println("called");

		// Get the view
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		IWorkbenchPage page = window.getActivePage();
		View view = (View) page.findView(PartID.View_Regler_Conf_ID);

		// Get the selection
		ISelection selection = view.getSite().getSelectionProvider()
				.getSelection();

		if (selection != null && selection instanceof IStructuredSelection) {
			Object obj = ((IStructuredSelection) selection).getFirstElement();

			// If we had a selection lets open the editor
			if (obj != null) {
				try {
					IEditorInput input;

					if (obj instanceof ReglerConfig) {
						ReglerConfig rc = (ReglerConfig) obj;
						input = new ReglerConfigEditorInput(rc.getnId());
						page.openEditor(input, PartID.Editor_Regler_Conf_ID);

					} else if (obj instanceof Standort) {
						Standort s = (Standort) obj;
						input = new StandortEditorInput(s.getNid());
						page.openEditor(input, PartID.Editor_Standort_ID);

					} else if (obj instanceof GatewayConfig) {
						GatewayConfig g = (GatewayConfig) obj;
						input = new GatewayConfigEditorInput(g.getnId());
						page.openEditor(input, PartID.Editor_Gateway_Conf_ID);
					}

				} catch (PartInitException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return null;
	}
}