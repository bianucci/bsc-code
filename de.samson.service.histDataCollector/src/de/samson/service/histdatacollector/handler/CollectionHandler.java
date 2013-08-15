package de.samson.service.histdatacollector.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import de.samson.service.database.DatabaseService;
import de.samson.service.histdatacollector.HistDataCollector;

public class CollectionHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		HistDataCollector instance = HistDataCollector.getInstance();
		
		if (instance.isCollecting())
			instance.stopCollecting();
		else
			instance.startCollecting(DatabaseService.getAllDataSources());

		return null;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
