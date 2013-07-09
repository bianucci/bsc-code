package de.samson.ui.descfilemanager.reglerTreeFactory;

import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;

public class ReglerTreeCheckStateListener implements ICheckStateListener {

	CheckboxTreeViewer tv;

	public ReglerTreeCheckStateListener(CheckboxTreeViewer tv) {
		super();
		this.tv = tv;
		tv.addCheckStateListener(this);
	}

	@Override
	public void checkStateChanged(CheckStateChangedEvent event) {
		if (event.getElement() instanceof ReglerRevisionGroup) {
			if (event.getChecked()) {
				tv.setSubtreeChecked(event.getElement(), true);
			}
			if (!event.getChecked()) {
				tv.setSubtreeChecked(event.getElement(), false);
			}
		}
	}

}
