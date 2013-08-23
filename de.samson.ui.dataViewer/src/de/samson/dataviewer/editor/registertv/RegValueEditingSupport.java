package de.samson.dataviewer.editor.registertv;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;

import de.samson.dataviewer.editor.DPDataDialog;
import de.samson.service.database.entities.data.RegisterData;
import de.samson.service.database.entities.description.HRegDesc;
import de.samson.service.database.util.DataConverterUtil;

public class RegValueEditingSupport extends EditingSupport {

	private TableViewer viewer;

	public RegValueEditingSupport(TableViewer viewer) {
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
		RegisterData rd = (RegisterData) element;
		if (rd.getWmw() != null)
			return false;
		HRegDesc sr = DataConverterUtil.getRegisterDescForData(rd);
		return !sr.isRo();
	}

	@Override
	protected Object getValue(Object element) {
		return false;
	}

	@Override
	protected void setValue(Object element, Object value) {
		RegisterData rd = (RegisterData) element;
		HRegDesc sr = DataConverterUtil.getRegisterDescForData(rd);
		
		new DPDataDialog(viewer.getControl().getShell(), rd, sr).open();
		
		this.viewer.refresh();
	}
}
