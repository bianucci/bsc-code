package de.samson.ui.descfilemanager.reglerTreeFactory;


import org.eclipse.jface.viewers.LabelProvider;

import de.samson.service.database.entities.description.GeraeteDescription;

public class ReglerTreeLabelProvider extends LabelProvider {

	public ReglerTreeLabelProvider() {
		super();
	}

	@Override
	public String getText(Object element) {

		if (element instanceof GeraeteDescription) {
			GeraeteDescription str_Geraet = (GeraeteDescription) element;

			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("Rev. ");
			stringBuilder.append(str_Geraet.getRevision());

			String line = stringBuilder.toString();

			return line;

		} else if (element instanceof ReglerRevisionGroup) {
			ReglerRevisionGroup reglerGroup = (ReglerRevisionGroup) element;

			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("Ger�t ");
			stringBuilder.append(reglerGroup.getName());

			if (reglerGroup.size() == 1) {
				stringBuilder.append(" Rev. "
						+ reglerGroup.get(0).getRevision());
			}

			String line = stringBuilder.toString();

			return line;

		} else {
			return null;
		}
	}
}
