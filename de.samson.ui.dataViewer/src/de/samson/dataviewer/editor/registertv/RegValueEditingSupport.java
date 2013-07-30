package de.samson.dataviewer.editor.registertv;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Display;

import de.samson.dataviewer.PartID;
import de.samson.modbusphp.datapointwriter.DataPointWriterService;
import de.samson.modbusphp.datapointwriter.exception.WriteDatapointFailedException;
import de.samson.service.database.entities.data.RegisterData;
import de.samson.service.database.entities.description.HoldingRegiterDescription;
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
		HoldingRegiterDescription sr = DataConverterUtil.getRegisterDescForData(rd);
		return !sr.isRo();
	}

	@Override
	protected Object getValue(Object element) {
		RegisterData rd = (RegisterData) element;
		int w = rd.getsWert();
		String rawdata = String.valueOf(w);

			HoldingRegiterDescription sr = DataConverterUtil.getRegisterDescForData(rd);
		double f = sr.getSkalierungsfaktor();

		if (f == 1) {
			return rawdata;
		} else {
			String scaledValue = String.valueOf(w / f);

			String[] split = scaledValue.split("\\.");
			if ((f == 100) && (split[1].length() < 2))
				scaledValue += "0";

			else if ((f == 1000) && (split[1].length() < 3))
				for(int i=0;i<3-split[1].length();i++)
					scaledValue+="=";
			
			System.out.println("getScaledValue " + scaledValue);
			return scaledValue;
		}
	}

	@Override
	protected void setValue(Object element, Object value) {
		RegisterData rd = (RegisterData) element;
		HoldingRegiterDescription sr = DataConverterUtil.getRegisterDescForData(rd);

		String s = (String) value;
		if (s.length() == 0)
			return;

		double d = Double.valueOf(s);
		d *= sr.getSkalierungsfaktor();
		s = String.valueOf((int) d);

		if (!sizeIsValid(s, sr))
			return;

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

		try {
			DataPointWriterService.writeRegValue(ip, station, dpNr, s);
		} catch (WriteDatapointFailedException e) {
			Status status = new Status(IStatus.ERROR, PartID.PLUGIN_ID, 0,
					"Wert " + s + "konnte nicht in Register " + dpNr
							+ "geschrieben werden.", null);
			ErrorDialog.openError(Display.getCurrent().getActiveShell(),
					"Datenpunkt Schreiber Fehler",
					"Datenpunkt konnte nicht beschrieben werden", status);
			e.printStackTrace();
		}
		this.viewer.refresh();
	}

	public boolean sizeIsValid(String sValue, HoldingRegiterDescription s) {
		double value = Double.valueOf(sValue.replace(",", "."));

		String sMin = s.getUeBerAnfang();
		String sMax = s.getUeBerEnde();
		double iMin = Double.valueOf(sMin.replace(",", "."));
		double iMax = Double.valueOf(sMax.replace(",", "."));

		if ((value < iMin) || (value > iMax))
			return false;
		else
			return true;
	}
}
