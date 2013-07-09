package de.samson.configviewer.editor.gateway;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class GatewayConfigEditorInput implements IEditorInput {

	private int gatewayID;

	public GatewayConfigEditorInput(int gatewayID) {
		this.gatewayID = gatewayID;
	}

	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
		return null;
	}

	@Override
	public boolean exists() {
		return true;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	@Override
	public String getName() {
		return "anyname";
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return "not your gateway";
	}

	public int getId() {
		return gatewayID;
	}

}
