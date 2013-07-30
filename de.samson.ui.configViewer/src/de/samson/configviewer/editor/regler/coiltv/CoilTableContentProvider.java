package de.samson.configviewer.editor.regler.coiltv;


import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import de.samson.service.database.entities.description.CoilDescription;
import de.samson.service.database.entities.description.GeraeteDescription;

public class CoilTableContentProvider implements IStructuredContentProvider {
	List<CoilDescription> source;

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (newInput instanceof GeraeteDescription) {
			source = ((GeraeteDescription) newInput).getCoilsList();
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
