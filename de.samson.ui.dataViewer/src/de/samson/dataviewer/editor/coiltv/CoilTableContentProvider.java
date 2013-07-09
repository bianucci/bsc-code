package de.samson.dataviewer.editor.coiltv;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import de.samson.service.database.entities.data.CoilData;
import de.samson.service.database.entities.data.ReglerData;

public class CoilTableContentProvider implements IStructuredContentProvider {

	List<CoilData> source;

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (newInput != null) {
			source = ((ReglerData) newInput).getCoilDatas();
		}else{
			source = null;
		}

	}

	@Override
	public void dispose() {
		source = null;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return source.toArray();
	}
}
