package de.samson.ui.descfilemanager.reglerTreeFactory;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import de.samson.service.database.entities.description.GeraeteDescription;

public class ReglerTreeContentProvider implements ITreeContentProvider {

	List<ReglerRevisionGroup> dataResource;

	@Override
	public void dispose() {

	}

	@SuppressWarnings("unchecked")
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		dataResource = new ArrayList<ReglerRevisionGroup>();

		List<GeraeteDescription> allRegler = (List<GeraeteDescription>) newInput;

		if (allRegler == null || allRegler.size() <= 0) {
			return;
		}

		for (GeraeteDescription s : allRegler) {

			String name = s.getGeraeteKennung();

			ReglerRevisionGroup rrg = null;

			for (ReglerRevisionGroup temp : dataResource) {
				if (temp.getName().equals(name)) {
					rrg = temp;
				}
			}
			if (rrg == null) {
				rrg = new ReglerRevisionGroup(name);
				dataResource.add(rrg);
			}
			rrg.add(s);
		}
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return dataResource.toArray();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof ReglerRevisionGroup) {
			ReglerRevisionGroup reglerGroup = (ReglerRevisionGroup) parentElement;
			if (reglerGroup.size() > 1) {
				Object[] array = reglerGroup.toArray();
				return array;
			}
		}
		return null;
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof ReglerRevisionGroup) {
			if (((ReglerRevisionGroup) element).size() > 1) {
				return true;
			}
		}
		return false;
	}

}
