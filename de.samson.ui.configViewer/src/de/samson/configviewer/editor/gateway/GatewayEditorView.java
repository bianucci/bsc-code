package de.samson.configviewer.editor.gateway;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;

import de.samson.configviewer.PartID;
import de.samson.configviewer.view.View;
import de.samson.service.database.DatabaseService;
import de.samson.service.database.entities.config.GatewayConfig;

public class GatewayEditorView extends EditorPart {
	public GatewayEditorView() {
	}

	private Text textIP;
	private Text textPort;
	private Label textErrorCount;
	private Label textNextrequest;
	private Text textGruppe;
	private GatewayConfigEditorInput input;
	private GatewayConfig gwc;

	private boolean dirty = false;

	@Override
	public void doSave(IProgressMonitor monitor) {
		if (!gwc.getsIP().equals(textIP.getText())) {
			gwc.setsIP(textIP.getText());
		}
		if (gwc.getnPort() != Integer.valueOf(textPort.getText())) {
			gwc.setnPort(Integer.valueOf(textPort.getText()));
		}
		if (gwc.getnGruppe() != Integer.valueOf(textGruppe.getText())) {
			gwc.setnGruppe(Integer.valueOf(textGruppe.getText()));
		}

		DatabaseService.persistEntity(gwc);

		IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		View view = (View) page.findView(PartID.View_Regler_Conf_ID);
		view.getTv().refresh();
		dirty = false;
		firePropertyChange(IEditorPart.PROP_DIRTY);
		
		setPartName(gwc.getsIP());
	}

	@Override
	public void doSaveAs() {
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		if (!(input instanceof GatewayConfigEditorInput)) {
			throw new RuntimeException("Wrong input");
		}

		this.input = (GatewayConfigEditorInput) input;
		setSite(site);
		setInput(input);

		gwc = DatabaseService.getGConfigByID(this.input.getId());
		setPartName(gwc.getsIP());
	}

	@Override
	public boolean isDirty() {
		return dirty;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(null);
		Label lblIp = new Label(parent, SWT.NONE);
		lblIp.setBounds(533, 0, 61, 471);
		lblIp.setBounds(10, 10, 30, 15);
		lblIp.setText("IP:");

		Label lblPort = new Label(parent, SWT.NONE);
		lblPort.setBounds(474, 0, 59, 471);
		lblPort.setBounds(166, 10, 30, 15);
		lblPort.setText("Port:");

		textIP = new Text(parent, SWT.BORDER);
		textIP.setBounds(415, 0, 59, 471);
		textIP.setText(gwc.getsIP());
		textIP.setBounds(46, 7, 100, 21);

		textPort = new Text(parent, SWT.BORDER);
		textPort.setBounds(356, 0, 59, 471);
		textPort.setText(String.valueOf(gwc.getnPort()));
		textPort.setBounds(202, 7, 46, 21);

		textErrorCount = new Label(parent, SWT.BORDER);
		textErrorCount.setBounds(297, 0, 59, 471);
		textErrorCount.setBounds(166, 72, 82, 21);
		textErrorCount.setText(String
				.valueOf(gwc.getGatewayData().getnErrcnt()));

		Label lblErrorTimeout = new Label(parent, SWT.NONE);
		lblErrorTimeout.setBounds(238, 0, 59, 471);
		lblErrorTimeout.setBounds(10, 48, 150, 15);
		lblErrorTimeout.setText("Timeout nach Fehler (s) :");

		Label lblErrorCount = new Label(parent, SWT.NONE);
		lblErrorCount.setBounds(179, 0, 59, 471);
		lblErrorCount.setBounds(10, 75, 150, 15);
		lblErrorCount.setText("Max. Anzahl Fehlversuche:");

		textNextrequest = new Label(parent, SWT.BORDER);
		textNextrequest.setBounds(120, 0, 59, 471);
		textNextrequest.setBounds(166, 45, 82, 21);
		textNextrequest.setText(String.valueOf(gwc.getGatewayData()
				.getnNextrequest()));

		Label lblThread = new Label(parent, SWT.NONE);
		lblThread.setBounds(61, 0, 59, 471);
		lblThread.setBounds(10, 102, 150, 15);
		lblThread.setText("Thread:");

		textGruppe = new Text(parent, SWT.BORDER);
		textGruppe.setBounds(0, 0, 61, 471);
		textGruppe.setBounds(166, 99, 82, 21);
		textGruppe.setText("1");
		textGruppe.setEnabled(false);

		ModifyListener listener = new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				dirty = true;
				firePropertyChange(IEditorPart.PROP_DIRTY);
			}
		};

		textGruppe.addModifyListener(listener);
		textIP.addModifyListener(listener);
		textPort.addModifyListener(listener);
	}

	@Override
	public void setFocus() {
	}
	
	@Override
	public String getTitleToolTip() {
		return gwc.getsIP()+" Gruppe "+gwc.getnGruppe();
	}
}
