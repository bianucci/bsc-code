package de.samson.dataviewer.view.treeviewer;

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import de.samson.service.database.entities.config.GatewayConfig;
import de.samson.service.database.entities.config.ReglerConfig;
import de.samson.service.database.entities.config.Standort;

public class DataTreeContentProvider implements ITreeContentProvider {

	List<Standort> source;

	@Override
	public void dispose() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		source = (List<Standort>) newInput;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return source.toArray();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof Standort) {
			return ((Standort) parentElement).getGateways().toArray();
		}
		if (parentElement instanceof GatewayConfig) {
			return ((GatewayConfig) parentElement).getRegler().toArray();
		}

		return null;
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof GatewayConfig) {
			return ((GatewayConfig) element).getStandort();
		}
		if (element instanceof ReglerConfig) {
			return ((ReglerConfig) element).getGatewayConfig();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof Standort) {
			return ((Standort) element).getGateways().size() > 0;
		}
		if (element instanceof GatewayConfig) {
			return ((GatewayConfig) element).getRegler().size() > 0;
		}
		return false;
	}
	
}
