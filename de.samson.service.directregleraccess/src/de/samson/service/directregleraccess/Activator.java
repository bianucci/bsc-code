package de.samson.service.directregleraccess;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import de.samson.modbusphp.datapointwriter.DataPointWriterService;

public class Activator extends AbstractUIPlugin {

	public static final String PLUGIN_ID = "de.samson.service.directregleraccess"; //$NON-NLS-1$

	private static Activator plugin;

	public Activator() {
	}

	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		new DataPointWriterService();

		getPreferenceStore().addPropertyChangeListener(
				new IPropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent event) {
						new DataPointWriterService();
					}
				});
	}

	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	public static Activator getDefault() {
		return plugin;
	}

}
