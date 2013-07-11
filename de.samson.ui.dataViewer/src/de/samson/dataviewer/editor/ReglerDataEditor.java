package de.samson.dataviewer.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import de.samson.dataviewer.editor.coiltv.CoilTableViewerFactory;
import de.samson.dataviewer.editor.registertv.RegisterTableViewerFactory;
import de.samson.service.database.DatabaseService;
import de.samson.service.database.entities.config.ReglerConfig;
import de.samson.service.database.entities.data.ReglerData;

public class ReglerDataEditor extends EditorPart {
	public ReglerDataEditor() {
	}

	private ReglerDataEditorInput input;
	private TableViewer regTV;
	private TableViewer coilTV;
	private ReglerData rd;
	private Composite c;
	private Button btnAutomaitischeUpdates;

	private boolean autoRefresh;

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {

		if (!(input instanceof ReglerDataEditorInput)) {
			throw new RuntimeException("Wrong input");
		}

		this.input = (ReglerDataEditorInput) input;
		setSite(site);
		setInput(input);

		rd = DatabaseService.getReglerDataByID(this.input.getId());
		setPartName("Regler ID: " + rd.getReglerConfig().getnId());

	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		Composite reglerSettings = createReglerDetailsComposite(parent);
		c.setLayout(new GridLayout(2, false));
		reglerSettings.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				false));

		btnAutomaitischeUpdates = new Button(c, SWT.CHECK);
		btnAutomaitischeUpdates.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println(btnAutomaitischeUpdates.getSelection());
				Thread t = null;
				autoRefresh = btnAutomaitischeUpdates.getSelection();
				
				if (autoRefresh) {
					t = new Thread() {
						@Override
						public void run() {
							while (autoRefresh) {
								System.out.println("Thread " + this.getId()
										+ "called");
								Display.getDefault().asyncExec(new Runnable() {

									@Override
									public void run() {
										DatabaseService.refreshEntity(rd);
										coilTV.refresh();
										regTV.refresh();
									}
								});
								try {
									Thread.sleep(5000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
					};
					t.start();
				}
			}
		});
		btnAutomaitischeUpdates.setText("Auto Refresh alle 5 Sekunden");

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

		regTV.setInput(rd);
		coilTV.setInput(rd);

		regGroup.setText("Register");
		coilGroup.setText("Coils");

	}

	private Composite createReglerDetailsComposite(Composite parent) {
		c = new Composite(parent, SWT.NONE);

		Button btnRefresh = new Button(c, SWT.NONE);
		btnRefresh.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DatabaseService.refreshEntity(rd);
				regTV.refresh();
				coilTV.refresh();
			}
		});
		btnRefresh.setText("Refresh");
		return c;
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void setFocus() {
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
	}

	@Override
	public void doSaveAs() {
	}
	
	@Override
	public void dispose() {
		autoRefresh = false;
		super.dispose();
	}

	public ReglerConfig getRc() {
		return rd.getReglerConfig();
	}
}
