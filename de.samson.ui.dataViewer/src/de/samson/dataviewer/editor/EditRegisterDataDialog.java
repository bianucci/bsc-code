package de.samson.dataviewer.editor;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import de.samson.dataviewer.PartID;
import de.samson.modbusphp.datapointwriter.DataPointWriterService;
import de.samson.modbusphp.datapointwriter.exception.WriteDatapointFailedException;
import de.samson.service.database.entities.data.RegisterData;
import de.samson.service.database.entities.description.HRegDesc;

public class EditRegisterDataDialog extends Dialog {

	private RegisterData rd;
	private HRegDesc sr;
	private Text tValue;

	public EditRegisterDataDialog(Shell parentShell, RegisterData rd, HRegDesc sr) {
		super(parentShell);
		this.rd = rd;
		this.sr = sr;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = (GridLayout) container.getLayout();
		gridLayout.numColumns = 2;

		Label lblRegisternr = new Label(container, SWT.NONE);
		lblRegisternr.setText("Register Nummer " + sr.getHrnr() + " "
				+ sr.getBezeichnung());
		lblRegisternr.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 2, 1));

		Label lblWerteBereich = new Label(container, SWT.NONE);
		lblWerteBereich.setText("Wertebereich:");
		lblWerteBereich.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1));

		Label lblWerteBereichValue = new Label(container, SWT.NONE);
		lblWerteBereichValue.setText(sr.getaBerAnfang() + " - "
				+ sr.getaBerEnde());
		lblWerteBereichValue.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER,
				false, false, 1, 1));

		Label label_1 = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gd_label_1 = new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 2, 1);
		gd_label_1.widthHint = 150;
		label_1.setLayoutData(gd_label_1);

		Label lblAktuellerWert = new Label(container, SWT.NONE);
		lblAktuellerWert.setText("Aktueller Wert:");

		Label lblCurrentval = new Label(container, SWT.NONE);
		lblCurrentval.setText(String.valueOf(rd.getsWert()
				/ sr.getSkalierungsfaktor()));

		Label lblNeuerWert = new Label(container, SWT.NONE);
		lblNeuerWert.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblNeuerWert.setText("Neuer Wert:");

		tValue = new Text(container, SWT.BORDER);
		tValue.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1,
				1));

		return container;
	}

	public void write(String value) {
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
			Status status = new Status(IStatus.OK
					, PartID.PLUGIN_ID, 0,
					"Wert " + s + " konnte in Register " + dpNr
							+ "geschrieben werden.", null);
			ErrorDialog.openError(Display.getCurrent().getActiveShell(),
					"Datenpunkt Schreiber Erfolgreich",
					"Datenpunkt konnte beschrieben werden", status);
		} catch (WriteDatapointFailedException e) {
			Status status = new Status(IStatus.ERROR, PartID.PLUGIN_ID, 0,
					"Wert " + s + " konnte nicht in Register " + dpNr
							+ "geschrieben werden.", null);
			ErrorDialog.openError(Display.getCurrent().getActiveShell(),
					"Datenpunkt Schreiber Fehler",
					"Datenpunkt konnte nicht beschrieben werden", status);
			e.printStackTrace();
		}
	}

	public boolean sizeIsValid(String sValue, HRegDesc s) {
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

	@Override
	protected Point getInitialSize() {
		return new Point(250, 200);
	}

	@Override
	protected void okPressed() {
		write(tValue.getText());
		super.okPressed();
	}

}
