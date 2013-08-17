package de.samson.histdata.ui.editor;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import de.samson.service.database.DatabaseService;
import de.samson.service.database.entities.histdata.HistDataSource;

public class OpenEditorHandler extends AbstractHandler {

	private HistDataEditorInput input;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		// Get the view
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		IWorkbenchPage page = window.getActivePage();

		int id = Integer.valueOf(event
				.getParameter("de.samson.ui.dataVisualizer.dataSourceId"));
		HistDataSource dataSource = null;
		if ((dataSource = DatabaseService.getDataSourceByID(id)) == null) {
			return null;
		}

		input = new HistDataEditorInput(dataSource, "Visualisierung "
				+ dataSource.getBezeichnung(), "Zeit", dataSource.getyAxisName());

		try {
			page.openEditor(input,
					"de.samson.ui.dataVisualizer.histDataSetEditor");

		} catch (PartInitException e) {
			throw new RuntimeException(e);
		}

		return null;
	}

}
