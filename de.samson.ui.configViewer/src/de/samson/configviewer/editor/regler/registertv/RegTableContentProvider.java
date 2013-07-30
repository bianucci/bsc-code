package de.samson.configviewer.editor.regler.registertv;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import de.samson.service.database.entities.description.GeraeteDescription;
import de.samson.service.database.entities.description.HoldingRegiterDescription;

public class RegTableContentProvider implements IStructuredContentProvider {
	List<HoldingRegiterDescription> source;

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (newInput instanceof GeraeteDescription) {
			source = ((GeraeteDescription) newInput).getRegisterList();
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
