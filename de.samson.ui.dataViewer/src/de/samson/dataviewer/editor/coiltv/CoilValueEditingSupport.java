package de.samson.dataviewer.editor.coiltv;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;

import de.samson.dataviewer.editor.EditCoilDataDialog;
import de.samson.service.database.entities.data.CoilData;
import de.samson.service.database.entities.description.CoilDesc;
import de.samson.service.database.util.DataConverterUtil;

public class CoilValueEditingSupport extends EditingSupport {

	private TableViewer viewer;

	public CoilValueEditingSupport(TableViewer viewer) {
		super(viewer);
		this.viewer = viewer;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return new CheckboxCellEditor(viewer.getTable(), SWT.CHECK
				| SWT.READ_ONLY);
	}

	@Override
	protected boolean canEdit(Object element) {
		CoilData cd = (CoilData) element;
		CoilDesc coilDesc = DataConverterUtil.getCoilDescForData(cd);
		return !coilDesc.isRo();
	}

	@Override
	protected Object getValue(Object element) {
		return false;
	}

	@Override
	protected void setValue(Object element, Object value) {
		CoilData cd = (CoilData) element;
		CoilDesc coilDesc = DataConverterUtil.getCoilDescForData(cd);
		new EditCoilDataDialog(viewer.getControl().getShell(), cd, coilDesc).open();
		getViewer().refresh();
	}
}
