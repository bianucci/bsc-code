package de.samson.configviewer.view.treeviewer;

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

public class ConfigTreeLabelProvider extends LabelProvider {

	private static final Image STANDORT = getImage("home.gif");

	private static final Image GATEWAY_AVAILABLE = getImage("gateway.gif");
	private static final Image GATEWAY_UNAVAILABLE = getImage("gateway_fail.gif");

	private static final Image REGLER_AVAILABLE = getImage("regler.gif");
	private static final Image REGLER_UNAVAILABLE = getImage("regler_fail.gif");

	@Override
	public String getText(Object element) {

		if (element instanceof Standort) {
			Standort s = (Standort) element;
			return s.getsStrasse() + " " + s.getsHn()
					+ (" (" + s.getnPlz() + ")");
		}
		if (element instanceof GatewayConfig) {
			GatewayConfig gwc = (GatewayConfig) element;
			return gwc.getsIP();
		}
		if (element instanceof ReglerConfig) {
			ReglerConfig rc = (ReglerConfig) element;
			String s = rc.getsTyp();
			int deviceid = ((ReglerConfig) element).getnDeviceid();

			s = "Gerät " + s + " Station " + deviceid;
			return s;
		}
		return null;
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof Standort) {
			return STANDORT;
		}
		if (element instanceof GatewayConfig) {
			if (((GatewayConfig) element).isAvailable())
				return GATEWAY_AVAILABLE;
			else
				return GATEWAY_UNAVAILABLE;
		}
		if (element instanceof ReglerConfig) {
			if (((ReglerConfig) element).isAvailable())
				return REGLER_AVAILABLE;
			else
				return REGLER_UNAVAILABLE;
		}
		return null;
	}

	private static Image getImage(String file) {
		Bundle bundle = FrameworkUtil.getBundle(ConfigTreeLabelProvider.class);
		URL url = FileLocator.find(bundle, new Path("icons/" + file), null);
		ImageDescriptor image = ImageDescriptor.createFromURL(url);
		return image.createImage();

	}

}
