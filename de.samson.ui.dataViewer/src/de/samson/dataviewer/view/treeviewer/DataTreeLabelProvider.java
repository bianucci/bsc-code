package de.samson.dataviewer.view.treeviewer;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import de.samson.service.database.entities.config.GatewayConfig;
import de.samson.service.database.entities.config.ReglerConfig;
import de.samson.service.database.entities.config.Standort;

public class DataTreeLabelProvider extends LabelProvider {

	private static final Image STANDORT = getImage("home.gif");
	private static final Image GATEWAY = getImage("gateway.gif");
	private static final Image REGLER = getImage("regler.gif");

	@Override
	public String getText(Object element) {

		if (element instanceof Standort) {
			return ((Standort) element).getsStrasse();
		}
		if (element instanceof GatewayConfig) {
			return ((GatewayConfig) element).getsIP();
		}
		if (element instanceof ReglerConfig) {
			int deviceid = ((ReglerConfig) element).getnDeviceid();
			return String.valueOf(deviceid);
		}
		return null;
	}

	@Override
	public Image getImage(Object element) {

		if (element instanceof Standort) {
			return STANDORT;
		}
		if (element instanceof GatewayConfig) {
			return GATEWAY;
		}
		if (element instanceof ReglerConfig) {
			return REGLER;
		}
		return null;
	}

	private static Image getImage(String file) {
		Bundle bundle = FrameworkUtil.getBundle(DataTreeLabelProvider.class);
		URL url = FileLocator.find(bundle, new Path("icons/" + file), null);
		ImageDescriptor image = ImageDescriptor.createFromURL(url);
		return image.createImage();

	}

}
