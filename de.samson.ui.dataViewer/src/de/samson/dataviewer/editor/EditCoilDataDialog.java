package de.samson.dataviewer.editor;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import de.samson.dataviewer.PartID;
import de.samson.modbusphp.datapointwriter.DataPointWriterService;
import de.samson.modbusphp.datapointwriter.exception.WriteDatapointFailedException;
import de.samson.service.database.entities.data.CoilData;
import de.samson.service.database.entities.description.CoilDesc;

public class EditCoilDataDialog extends Dialog {

	private CoilData cd;
	private CoilDesc desc;

	private boolean valueToWrite;

	public EditCoilDataDialog(Shell parentShell, CoilData cd, CoilDesc desc) {
		super(parentShell);
		this.cd = cd;
		this.desc = desc;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = (GridLayout) container.getLayout();
		gridLayout.numColumns = 2;

		Label lblCoilnr = new Label(container, SWT.NONE);
		lblCoilnr.setText("Coilnr: " + desc.getClnr() + " - "
				+ desc.getBezeichnung());
		new Label(container, SWT.NONE);

		Label lblSeperator = new Label(container, SWT.SEPARATOR
				| SWT.HORIZONTAL);
		lblSeperator.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true,
				false, 2, 1));
		lblSeperator.setText("seperator");

		Button btnValueFalse = new Button(container, SWT.RADIO);
		btnValueFalse.setText(desc.getText0());
		btnValueFalse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				valueToWrite = false;
			}
		});

		Button btnValueTrue = new Button(container, SWT.RADIO);
		btnValueTrue.setText(desc.getText1());
		btnValueTrue.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				valueToWrite = true;
			}
		});

		if (cd.getWert())
			btnValueTrue.setSelection(true);
		else
			btnValueFalse.setSelection(true);

		return container;
	}

	private void write(boolean value) {
		String ip = cd.getRegler().getGatewayData().getGatewayConfig().getsIP();
		String station = String.valueOf(cd.getRegler().getReglerConfig()
				.getnDeviceid());
		String dpNr = String.valueOf(cd.getnCoilnr());

		try {
			DataPointWriterService.writeCoilValue(ip, station, dpNr, value);
		} catch (WriteDatapointFailedException e) {
			Status status = new Status(IStatus.ERROR, PartID.PLUGIN_ID, 0,
					"Wert " + value + " konnte nicht in Register " + dpNr
							+ " geschrieben werden.", null);
			ErrorDialog.openError(Display.getCurrent().getActiveShell(),
					"Datenpunkt Schreiber Fehler",
					"Datenpunkt konnte nicht beschrieben werden", status);
			e.printStackTrace();
		}
	}

	@Override
	protected void okPressed() {
		write(valueToWrite);
		super.okPressed();
	}

	@Override
	protected Point getInitialSize() {
		return new Point(250, 150);
	}

}
