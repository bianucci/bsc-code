package de.samson.configviewer.editor.regler.registertv;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import de.samson.service.database.entities.description.STR_Geraet;
import de.samson.service.database.entities.description.STR_HoldingReg;

public class RegTableContentProvider implements IStructuredContentProvider {
	List<STR_HoldingReg> source;

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (newInput instanceof STR_Geraet) {
			source = ((STR_Geraet) newInput).getRegisterList();
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
