package de.samson.dataviewer.editor.coiltv;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.samson.modbusphp.datapointwriter.DataPointWriterService;
import de.samson.service.database.entities.data.CoilData;
import de.samson.service.database.entities.description.STR_Coil;
import de.samson.service.database.util.DataConverterUtil;

public class CoilValueEditingSupport extends EditingSupport {

	private ComboBoxViewerCellEditor cellEditor = null;

	public CoilValueEditingSupport(TableViewer viewer) {
		super(viewer);
		cellEditor = new ComboBoxViewerCellEditor((Composite) getViewer()
				.getControl(), SWT.READ_ONLY);

		cellEditor.setLabelProvider(new LabelProvider());
		cellEditor.setContentProvider(new ArrayContentProvider());
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		CoilData cd = (CoilData) element;
		STR_Coil coilDesc = DataConverterUtil.getCoilDescForData(cd);
		String[] input = new String[] { coilDesc.getText0(),
				coilDesc.getText1() };
		cellEditor.setInput(input);
		return cellEditor;
	}

	@Override
	protected boolean canEdit(Object element) {
		CoilData cd = (CoilData) element;
		STR_Coil coilDesc = DataConverterUtil.getCoilDescForData(cd);
		return !coilDesc.isRo();
	}

	@Override
	protected Object getValue(Object element) {
		CoilData cd = (CoilData) element;
		STR_Coil coilDesc = DataConverterUtil.getCoilDescForData(cd);

		if (cd.getWert() == true)
			return coilDesc.getText1();
		else
			return coilDesc.getText0();
	}

	@Override
	protected void setValue(Object element, Object value) {
		CoilData cd = (CoilData) element;
		String s = (String) value;
		boolean v = s.contains("(1)");

		String ip = cd.getRegler().getGatewayData().getGatewayConfig().getsIP();

		String station = String.valueOf(cd.getRegler().getReglerConfig()
				.getnDeviceid());

		String dpNr = String.valueOf(cd.getnCoilnr());

		DataPointWriterService.writeCoilValue(ip, station, dpNr, v);

		// DBManager.updateCoilValue(cd, v);
		getViewer().refresh();
	}
}