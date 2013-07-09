package de.samson.application.sourceprovider;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.ui.AbstractSourceProvider;

public class HandlerEnabler extends AbstractSourceProvider {

	public final static String MY_STATE = "de.samson.application.alwaysTrue";
	
	public HandlerEnabler() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map getCurrentState() {
		Map map = new HashMap(1);
	    map.put(MY_STATE, "ENABLED");
	    return map;
	}

	@Override
	public String[] getProvidedSourceNames() {
		return new String[] { MY_STATE };
	}

}
