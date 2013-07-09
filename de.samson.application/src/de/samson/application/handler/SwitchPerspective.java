package de.samson.application.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;

import de.samson.application.shared.PartID;

public class SwitchPerspective extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			IWorkbenchWindow w = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow();

			w.getActivePage().closeAllEditors(true);

			if (w.getActivePage().getPerspective().getId()
					.equals(PartID.Perspective_Data_ID)) {

				PlatformUI.getWorkbench().showPerspective(
						PartID.Perspective_Config_ID, w);
			}

			else if (w.getActivePage().getPerspective().getId()
					.equals(PartID.Perspective_Config_ID)) {

				PlatformUI.getWorkbench().showPerspective(
						PartID.Perspective_Data_ID, w);
			}

		} catch (WorkbenchException e) {
			e.printStackTrace();
		}
		return null;
	}

}
