package de.samson.configviewer.editor.regler;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;

import de.samson.configviewer.PartID;
import de.samson.configviewer.editor.regler.coiltv.CoilTableViewerFactory;
import de.samson.configviewer.editor.regler.registertv.RegisterTableViewerFactory;
import de.samson.configviewer.view.View;
import de.samson.service.database.DatabaseService;
import de.samson.service.database.entities.config.CoilConfig;
import de.samson.service.database.entities.config.RegisterConfig;
import de.samson.service.database.entities.config.ReglerConfig;
import de.samson.service.database.entities.description.CoilDescription;
import de.samson.service.database.entities.description.HoldingRegiterDescription;

public class ReglerConfEditor extends EditorPart {
	public ReglerConfEditor() {
	}

	private ReglerConfig rc;
	private ReglerConfigEditorInput input;
	private CheckboxTableViewer regTV;
	private CheckboxTableViewer coilTV;
	private ComboViewer comboViewerStatNr;
	private ComboViewer comboReglerTyp;
	private ComboViewer comboRevNr;
	private String[] modbusAdresses;

	private boolean dirty = false;
	private List<Object> toAdd;
	private List<Object> toDel;

	private boolean noComboChange = true;
	private View treeView;

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {

		if (!(input instanceof ReglerConfigEditorInput)) {
			throw new RuntimeException("Wrong input");
		}

		this.input = (ReglerConfigEditorInput) input;
		setSite(site);
		setInput(input);

		rc = DatabaseService.getReglerConfigByID(this.input.getId());
		
		String s = "";
		s += "Gerät " + rc.getsTyp();
		setPartName(s);

		modbusAdresses = new String[255];
		for (int i = 0; i < 255; i++) {
			modbusAdresses[i] = String.valueOf(i + 1);
		}

		toAdd = new ArrayList<Object>();
		toDel = new ArrayList<Object>();

		IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		treeView = (View) page.findView(PartID.View_Regler_Conf_ID);
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		Composite reglerSettings = createReglerDetailsComposite(parent);
		reglerSettings.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				false));

		Group regGroup = new Group(parent, SWT.NONE);
		Group coilGroup = new Group(parent, SWT.NONE);

		GridData lData = new GridData(SWT.FILL, SWT.FILL, true, true);
		lData.heightHint = 250;
		regGroup.setLayoutData(lData);

		GridData lData2 = new GridData(SWT.FILL, SWT.FILL, true, true);
		lData2.heightHint = 250;
		coilGroup.setLayoutData(lData2);

		regGroup.setLayout(new FillLayout());
		coilGroup.setLayout(new FillLayout());

		regTV = RegisterTableViewerFactory.createTV(regGroup);
		coilTV = CoilTableViewerFactory.createTV(coilGroup);

		regTV.setInput(rc.getReglerDescription());
		coilTV.setInput(rc.getReglerDescription());

		ISelectionChangedListener selectionListener = new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				dirty = true;
				firePropertyChange(IEditorPart.PROP_DIRTY);

				String newReglerTyp = comboReglerTyp.getCombo().getText();
				boolean reglerTypChanged = !newReglerTyp.equals(rc.getsTyp());

				if (event.getSource() == comboReglerTyp) {
					comboRevNr.setInput(DatabaseService
							.getAllREvisionNrForRegler(newReglerTyp));
					comboRevNr.refresh();
					comboRevNr.getCombo().select(0);
				}

				String newRevNr = comboRevNr.getCombo().getText();
				boolean revNrChanged = !newRevNr.equals(rc
						.getDescFileRevision());

				noComboChange = !(reglerTypChanged || revNrChanged);

				coilTV.getTable().setEnabled(noComboChange);
				regTV.getTable().setEnabled(noComboChange);
			}
		};

		ICheckStateListener checkstateListner = new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				dirty = true;
				firePropertyChange(IEditorPart.PROP_DIRTY);
				Object o = event.getElement();

				if (event.getChecked()) {
					if (toDel.contains(o))
						toDel.remove(o);
					else
						toAdd.add(o);
				} else {
					if (toAdd.contains(o))
						toAdd.remove(o);
					else
						toDel.add(o);
				}
			}
		};

		regTV.addCheckStateListener(checkstateListner);
		coilTV.addCheckStateListener(checkstateListner);

		comboReglerTyp.addSelectionChangedListener(selectionListener);
		comboRevNr.addSelectionChangedListener(selectionListener);
		comboViewerStatNr.addSelectionChangedListener(selectionListener);

		regGroup.setText("Register");
		coilGroup.setText("Coils");

		selectCheckBoxes();
	}

	private void selectCheckBoxes() {
		TableItem[] coils = coilTV.getTable().getItems();
		for (CoilConfig c : rc.getCoilsConfigs()) {
			for (int i = 0; i < coils.length; i++) {
				CoilDescription s = (CoilDescription) coils[i].getData();
				if (s.getClnr() == c.getnCoilnr()) {
					coilTV.setChecked(s, true);
					break;
				}
			}
		}

		TableItem[] register = regTV.getTable().getItems();
		for (RegisterConfig r : rc.getRegisterConfigs()) {

			for (int i = 0; i < register.length; i++) {
				HoldingRegiterDescription s = (HoldingRegiterDescription) register[i].getData();
				if (s.getHrnr() - 40000 == r.getnRegisternr()) {
					regTV.setChecked(s, true);
				}
			}
		}
	}

	private Composite createReglerDetailsComposite(Composite parent) {
		Composite c = new Composite(parent, SWT.NONE);
		c.setLayout(new GridLayout(4, false));

		// Comp1
		Label lblReglertyp = new Label(c, SWT.NONE);
		lblReglertyp.setText("Regler-Typ:");

		// Comp2
		comboReglerTyp = new ComboViewer(c, SWT.NONE);
		Combo combo = comboReglerTyp.getCombo();
		GridData layoutData = new GridData(SWT.LEFT, SWT.CENTER, true, false);
		layoutData.widthHint = 150;
		combo.setLayoutData(layoutData);
		comboReglerTyp.setContentProvider(new ArrayContentProvider());

		// Comp3
		Label lblRevnr = new Label(c, SWT.NONE);
		lblRevnr.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		lblRevnr.setText("Rev-Nr.:");

		// Comp4
		comboRevNr = new ComboViewer(c, SWT.NONE);
		Combo crnr = comboRevNr.getCombo();
		comboRevNr.setContentProvider(new ArrayContentProvider());
		layoutData = new GridData(SWT.LEFT, SWT.CENTER, true, false);
		layoutData.widthHint = 150;
		crnr.setLayoutData(layoutData);

		// Comp5
		Label lblKommentar = new Label(c, SWT.NONE);
		lblKommentar.setText("Kommentar:");

		// Comp6
		Label lblReglerkommentar = new Label(c, SWT.NONE);
		lblReglerkommentar.setText(rc.getReglerDescription().getComment());

		// Comp7
		Label lblStatNr = new Label(c, SWT.NONE);
		lblStatNr.setText("Stations-Nr.:");
		lblStatNr.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false));

		// Comp8
		comboViewerStatNr = new ComboViewer(c, SWT.NONE);
		Combo csnr = comboRevNr.getCombo();
		comboViewerStatNr.setContentProvider(new ArrayContentProvider());
		layoutData = new GridData(SWT.LEFT, SWT.CENTER, true, false);
		layoutData.widthHint = 150;
		csnr.setLayoutData(layoutData);

		// set combo data
		initCombosForReglerConfig();

		return c;
	}

	private void initCombosForReglerConfig() {
		comboRevNr.setInput(DatabaseService.getAllREvisionNrForRegler(rc
				.getsTyp()));
		comboReglerTyp.setInput(DatabaseService.getAllReglerTypes());
		comboViewerStatNr.setInput(modbusAdresses);

		comboRevNr.getCombo().setText(rc.getDescFileRevision());
		comboReglerTyp.getCombo().setText(rc.getsTyp());
		comboViewerStatNr.getCombo().setText(String.valueOf(rc.getnDeviceid()));
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		String statNR = comboViewerStatNr.getCombo().getText();
		String revNR = comboRevNr.getCombo().getText();
		String reglerTyp = comboReglerTyp.getCombo().getText();

		if (noComboChange) {
			DatabaseService.updateReglerConfig(rc, toAdd, toDel, statNR, revNR,
					reglerTyp);
		} else {
			List<Object> deleteAll = new ArrayList<>();
			deleteAll.addAll(rc.getCoilsConfigs());
			deleteAll.addAll(rc.getRegisterConfigs());
			DatabaseService.updateReglerConfig(rc, toAdd, deleteAll, statNR,
					revNR, reglerTyp);

			regTV.setInput(rc.getReglerDescription());
			coilTV.setInput(rc.getReglerDescription());

			regTV.getTable().setEnabled(true);
			coilTV.getTable().setEnabled(true);

			noComboChange = true;
		}

		dirty = false;
		firePropertyChange(IEditorPart.PROP_DIRTY);
		toAdd.clear();
		toDel.clear();
		treeView.getTv().refresh();
		
		String s = "";
		s += "Gerät " + rc.getsTyp();
		setPartName(s);
	}

	@Override
	public void doSaveAs() {
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
	public void setFocus() {
	}

	@Override
	public String getTitleToolTip() {
		String s = "";
		s += "Gerät " + rc.getsTyp();
		s += " v. " + rc.getDescFileRevision();
		s += " - " + rc.getGatewayConfig().getsIP();
		return s;
	}
	
	public ReglerConfig getRc() {
		return rc;
	}

}
