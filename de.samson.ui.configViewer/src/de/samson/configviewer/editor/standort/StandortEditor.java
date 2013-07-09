package de.samson.configviewer.editor.standort;

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
import de.samson.service.database.entities.config.Standort;

public class StandortEditor extends EditorPart {
	private Text textPLZ;
	private Text textStrasse;
	private Text textHausnr;
	private StandortEditorInput input;
	private Standort s;

	private boolean dirty;

	public StandortEditor() {
	}

	@Override
	public void doSave(IProgressMonitor monitor) {

		if (s.getnPlz() != Integer.valueOf(textPLZ.getText())) {
			s.setnPlz(Integer.valueOf(textPLZ.getText()));
		}
		if (!s.getsStrasse().equals(textStrasse.getText())) {
			s.setsStrasse(textStrasse.getText());
		}
		if (!s.getsHn().equals(textHausnr.getText())) {
			s.setsHn(textHausnr.getText());
		}

		DatabaseService.persistEntity(s);

		IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		View view = (View) page.findView(PartID.View_Regler_Conf_ID);
		view.getTv().refresh();
		dirty = false;
		firePropertyChange(IEditorPart.PROP_DIRTY);
		
		setPartName(s.getsStrasse()+" "+s.getsHn());
	}

	@Override
	public void doSaveAs() {
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		if (!(input instanceof StandortEditorInput)) {
			throw new RuntimeException("Wrong input");
		}

		this.input = (StandortEditorInput) input;
		setSite(site);
		setInput(input);

		s = DatabaseService.getStandortConfigByID(this.input.getId());
		setPartName(s.getsStrasse()+" "+s.getsHn());

	}

	@Override
	public boolean isDirty() {
		return dirty;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(null);
		Label lblStrasse = new Label(parent, SWT.NONE);
		lblStrasse.setBounds(507, 0, 87, 471);
		lblStrasse.setBounds(10, 40, 55, 15);
		lblStrasse.setText("Stra\u00DFe:");

		Label lblHausnr = new Label(parent, SWT.NONE);
		lblHausnr.setBounds(423, 0, 84, 471);
		lblHausnr.setBounds(10, 67, 55, 15);
		lblHausnr.setText("Hausnr.:");

		Label lblPlz = new Label(parent, SWT.NONE);
		lblPlz.setBounds(339, 0, 84, 471);
		lblPlz.setBounds(10, 13, 55, 15);
		lblPlz.setText("PLZ:");

		textPLZ = new Text(parent, SWT.BORDER);
		textPLZ.setBounds(255, 0, 84, 471);
		textPLZ.setBounds(71, 10, 169, 21);
		textPLZ.setText(String.valueOf(s.getnPlz()));

		textStrasse = new Text(parent, SWT.BORDER);
		textStrasse.setBounds(171, 0, 84, 471);
		textStrasse.setBounds(71, 37, 169, 21);
		textStrasse.setText(s.getsStrasse());

		textHausnr = new Text(parent, SWT.BORDER);
		textHausnr.setBounds(87, 0, 84, 471);
		textHausnr.setBounds(71, 64, 169, 21);
		textHausnr.setText(s.getsHn());

		ModifyListener listener = new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				dirty = true;
				firePropertyChange(IEditorPart.PROP_DIRTY);
			}
		};
		textPLZ.addModifyListener(listener);
		textStrasse.addModifyListener(listener);
		textHausnr.addModifyListener(listener);
	}

	@Override
	public String getTitleToolTip() {
		return s.getsStrasse() + " " + s.getsHn() + " in " + s.getnPlz();
	}

	@Override
	public void setFocus() {
	}
}
