package de.samson.configviewer.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.PlatformUI;

import de.samson.configviewer.PartID;
import de.samson.configviewer.view.View;

public class DBChangedHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		String id = PartID.View_Regler_Conf_ID;
		System.out.println("hallo");
		View v = (View) PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().findView(id);
		v.getTv().refresh();
		return null;
	}
}
