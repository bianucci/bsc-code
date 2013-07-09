package de.samson.configviewer.editor.regler.coiltv;


import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import de.samson.service.database.entities.description.STR_Coil;
import de.samson.service.database.entities.description.STR_Geraet;

public class CoilTableContentProvider implements IStructuredContentProvider {
	List<STR_Coil> source;

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (newInput instanceof STR_Geraet) {
			source = ((STR_Geraet) newInput).getCoilsList();
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
