package de.samson.configviewer.editor.regler;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class ReglerConfigEditorInput implements IEditorInput {

	int reglerId;

	public ReglerConfigEditorInput(int getnId) {
		this.reglerId = getnId;
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
		return "not your regler";
	}

	public int getId() {
		return reglerId;
	}

}
