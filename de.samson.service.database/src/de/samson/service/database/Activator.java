package de.samson.service.database;

import java.util.Timer;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class Activator extends AbstractUIPlugin {

	private static final String PLUGIN_ID = "de.samson.service.database";
	public static final String ConfigViewerDbChanged = "de.samson.ui.configViewer.dbChanged";
	public static final String DataViewerDbChanged = "de.samson.ui.configViewer.dbChanged";

	private Timer t;
	private static Activator plugin;

	public Activator() {
	}

	public void start(BundleContext context) {
		try {
			super.start(context);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		plugin = this;

		try {
			DatabaseService.start();
		} catch (Exception e1) {
			Status status = new Status(IStatus.ERROR, PLUGIN_ID, 0,
					"Status Error Message", null);
			ErrorDialog.openError(Display.getCurrent().getActiveShell(),
					"Datenbank Fehler",
					"Datenbank konnte nicht geladen werden", status);
			e1.printStackTrace();
		}
		t = new Timer();
		t.schedule(new DatabaseTimer(), 60000, 60000);

		getPreferenceStore().addPropertyChangeListener(
				new IPropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent event) {
						DatabaseService.shutDown();
						try {
							DatabaseService.start();
							IHandlerService handlerService = (IHandlerService) PlatformUI
									.getWorkbench().getActiveWorkbenchWindow()
									.getService(IHandlerService.class);
							handlerService.executeCommand(
									ConfigViewerDbChanged, null);
						} catch (Exception e) {
							Status status = new Status(IStatus.ERROR,
									PLUGIN_ID, 0, "Status Error Message", null);
							ErrorDialog.openError(Display.getCurrent()
									.getActiveShell(), "Datenbank Fehler",
									"Datenbank konnte nicht geladen werden",
									status);
							e.printStackTrace();
						}
					}
				});
	}

	public void stop(BundleContext context) throws Exception {
		t.cancel();

		plugin = null;
		super.stop(context);
	}

	public static Activator getDefault() {
		return plugin;
	}

	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

}
