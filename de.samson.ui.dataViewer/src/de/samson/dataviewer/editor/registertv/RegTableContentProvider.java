package de.samson.dataviewer.editor.registertv;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import de.samson.service.database.entities.data.RegisterData;
import de.samson.service.database.entities.data.ReglerData;

public class RegTableContentProvider implements IStructuredContentProvider {
	List<RegisterData> source;

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (newInput instanceof ReglerData) {
			source = ((ReglerData) newInput).getRegisterData();
		}
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return source.toArray();
	}

	@Override
	public void dispose() {
		source = null;
	}
}
