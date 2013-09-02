package de.samson.dataviewer.editor;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.IHandlerService;

import de.samson.dataviewer.PartID;
import de.samson.service.database.entities.data.CoilData;
import de.samson.service.database.entities.data.RegisterData;
import de.samson.service.database.entities.data.WmwData;
import de.samson.service.database.ientities.histdata.HistDataSource;

public class CellClickedListener implements Listener {

	private static final String openHistDataEditorCommandParameterID = "de.samson.ui.dataVisualizer.dataSourceId";

	private ICommandService commandService;
	private IHandlerService handlerService;

	private TableViewer coilTV;
	private TableViewer regTV;
	private int histDataColumnIndex;

	private int valueColumnIndex;

	public CellClickedListener(TableViewer coilTV, TableViewer regTV,
			int histDataColumnIndex, int valueColumnIndex) {
		super();
		this.coilTV = coilTV;
		this.regTV = regTV;
		this.histDataColumnIndex = histDataColumnIndex;
		this.valueColumnIndex = valueColumnIndex;

		commandService = (ICommandService) PlatformUI.getWorkbench()
				.getService(ICommandService.class);
		handlerService = (IHandlerService) PlatformUI.getWorkbench()
				.getService(IHandlerService.class);
	}

	@Override
	public void handleEvent(Event event) {
		Point pt = new Point(event.x, event.y);

		// get table selected table row
		TableItem item = null;
		if (event.widget.equals(coilTV.getTable()))
			item = coilTV.getTable().getItem(pt);

		else if (event.widget.equals(regTV.getTable()))
			item = regTV.getTable().getItem(pt);

		if (item != null) {

			// check if the cell at histDataColumnIndex was selected
			Rectangle rect = item.getBounds(histDataColumnIndex);
			if (rect.contains(pt)) {

				// check if hist data source is available for selected data
				HistDataSource ds = null;
				if (item.getData() instanceof CoilData) {
					ds = ((CoilData) item.getData()).getDataSource();
				} else if (item.getData() instanceof RegisterData) {
					WmwData wmw = ((RegisterData) item.getData()).getWmw();
					if (wmw != null) {
						if (wmw.getDataSource() != null)
							ds = wmw.getDataSource();
					} else
						ds = ((RegisterData) item.getData()).getDataSource();
				}

				// fire command to open hist data editor
				if (ds != null) {
					fireOpenEditorCommand(ds);
				}
			}
			
			rect = item.getBounds(this.valueColumnIndex);
			if (rect.contains(pt)) {

				// check if hist data source is available for selected data
				if (item.getData() instanceof CoilData) {
					
				} else if (item.getData() instanceof RegisterData) {
					
				}

			}
		}
	}

	private void fireOpenEditorCommand(HistDataSource ds) {
		Command openHistDataEditor = commandService
				.getCommand(PartID.Command_Open_HistDataSet_Editor_ID);

		Map<String, String> params = new HashMap<String, String>();

		params.put(openHistDataEditorCommandParameterID,
				String.valueOf(ds.getId()));

		ParameterizedCommand pC = ParameterizedCommand.generateCommand(
				openHistDataEditor, params);

		try {
			handlerService.executeCommand(pC, null);
		} catch (Exception ex) {
			throw new RuntimeException(
					"de.samson.dataVisualizer.openEditor not found");
		}
	}

}
