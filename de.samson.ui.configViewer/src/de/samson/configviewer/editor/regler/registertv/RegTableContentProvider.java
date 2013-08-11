package de.samson.configviewer.editor.regler.registertv;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import de.samson.service.database.entities.description.GeraeteDesc;
import de.samson.service.database.entities.description.HRegDesc;

public class RegTableContentProvider implements IStructuredContentProvider {
	List<HRegDesc> source;

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (newInput instanceof GeraeteDesc) {
			source = ((GeraeteDesc) newInput).getRegisterList();
		}
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return source.toArray();
	}

	@Override
	public void dispose() {
	}
}
