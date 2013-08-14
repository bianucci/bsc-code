package de.samson.service.histdatacollector.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import de.samson.service.histdatacollector.HistDataObserver;

public class CollectionHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		HistDataObserver instance = HistDataObserver.getInstance();
		
		if (instance.isObserving())
			instance.stopObserving();
		else
			instance.startObserving(null);

		return null;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
