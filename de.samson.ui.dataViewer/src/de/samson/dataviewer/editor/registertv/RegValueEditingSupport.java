package de.samson.dataviewer.editor.registertv;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

import de.samson.modbusphp.datapointwriter.DataPointWriterService;
import de.samson.service.database.entities.data.RegisterData;
import de.samson.service.database.entities.description.STR_HoldingReg;
import de.samson.service.database.util.DataConverterUtil;

public class RegValueEditingSupport extends EditingSupport {

	private TableViewer viewer;

	public RegValueEditingSupport(TableViewer viewer) {
		super(viewer);
		this.viewer = viewer;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return new TextCellEditor(viewer.getTable());
	}

	@Override
	protected boolean canEdit(Object element) {
		RegisterData rd = (RegisterData) element;
		STR_HoldingReg sr = DataConverterUtil.getRegisterDescForData(rd);
		return !sr.isRo();
	}

	@Override
	protected Object getValue(Object element) {
		RegisterData rd = (RegisterData) element;
		STR_HoldingReg sr = DataConverterUtil.getRegisterDescForData(rd);

		double f = sr.getSkalierungsfaktor();
		if (f == 1)
			return String.valueOf(rd.getsWert());
		else
			return (String.valueOf(rd.getsWert() / f));
	}

	@Override
	protected void setValue(Object element, Object value) {
		RegisterData rd = (RegisterData) element;
		STR_HoldingReg sr = DataConverterUtil.getRegisterDescForData(rd);

		String s = (String) value;
		if (s.length() == 0)
			return;
		
		double d = Double.valueOf(s);
		d *= sr.getSkalierungsfaktor();
		s = String.valueOf((int) d);


		String rawData = Integer.toHexString(Integer.valueOf(s));
		while (rawData.length() < 4) {
			rawData = "0" + rawData;
		}

		int len = rawData.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(rawData.charAt(i), 16) << 4) + Character
					.digit(rawData.charAt(i + 1), 16));
		}

		String ip = rd.getReglerData().getGatewayData().getGatewayConfig()
				.getsIP();

		String station = String.valueOf(rd.getReglerData().getReglerConfig()
				.getnDeviceid());

		String dpNr = String.valueOf(rd.getnRegisternr());

		DataPointWriterService.writeRegValue(ip, station, dpNr, s);
		this.viewer.refresh();
	}
}
