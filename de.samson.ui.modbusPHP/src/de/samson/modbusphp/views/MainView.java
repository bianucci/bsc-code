package de.samson.modbusphp.views;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import de.samson.modbusphp.Activator;
import de.samson.modbusphp.connenction.ModbusPHPConfig;
import de.samson.modbusphp.connenction.ModbusPHPService;

public class MainView extends ViewPart implements Observer {

	public static final String ID = "de.samson.modbusphp.views.MainView";
	private Text txtModbusphpinfo;
	public static ModbusPHPConfig config;
	private ModbusPHPService mbusPHP;
	private Button btnStart;
	private Button btnStop;
	private IPreferenceStore preferences;

	public MainView() {
		init();
	}

	private void init() {
		config = new ModbusPHPConfig();
		Activator default1 = Activator.getDefault();
		preferences = default1.getPreferenceStore();
	}

	@Override
	public void createPartControl(Composite parent) {
		config = new ModbusPHPConfig();
		config.setProperty("ip", preferences.getString("IP"));
		config.setProperty("port", preferences.getString("PORT"));
		config.setProperty("winPHP", preferences.getString("WINPHP"));
		config.setProperty("startServer",
				preferences.getString("STARTSERVERPHP"));
		config.setProperty("startThread",
				preferences.getString("STARTTHREADPHP"));
		mbusPHP = new ModbusPHPService(config);

		parent.setLayout(new GridLayout(2, false));
		btnStart = new Button(parent, SWT.NONE);
		btnStart.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				startButtonPushed();
			}
		});
		GridData gd_btnStart = new GridData(SWT.LEFT, SWT.TOP, false, false, 1,
				1);
		gd_btnStart.widthHint = 100;
		btnStart.setLayoutData(gd_btnStart);
		btnStart.setText("Start");

		txtModbusphpinfo = new Text(parent, SWT.BORDER | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.MULTI);
		txtModbusphpinfo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true, 1, 4));

		btnStop = new Button(parent, SWT.NONE);
		btnStop.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				stopButtonPushed();
			}
		});
		GridData gd_btnStop = new GridData(SWT.LEFT, SWT.TOP, false, false, 1,
				1);
		gd_btnStop.widthHint = 100;
		btnStop.setLayoutData(gd_btnStop);
		btnStop.setText("Stop");

		initButtons();
		mbusPHP.addObserver(this);
	}

	public void initButtons() {
		btnStart.setEnabled(true);
		btnStop.setEnabled(false);
		if (mbusPHP.modbusPHPIsRunning()) {
			btnStart.setEnabled(false);
			btnStop.setEnabled(true);

			if (!mbusPHP.isConnected()) {
				try {
					mbusPHP.connect();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public void startButtonPushed() {
		btnStart.setEnabled(false);
		btnStop.setEnabled(true);
		new Thread() {
			@Override
			public void run() {
				try {
					if (!mbusPHP.isConnected())
						mbusPHP.startUp();
						mbusPHP.connect();
				} catch (Exception e) {
					e.printStackTrace();
				}
				super.run();
			}
		}.start();
	}

	public void stopButtonPushed() {
		btnStart.setEnabled(true);
		btnStop.setEnabled(false);
		new Thread() {
			@Override
			public void run() {
				try {
					mbusPHP.shutdown();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				mbusPHP.disconnect();
				super.run();
			}
		}.start();
	}

	@Override
	public void setFocus() {
	}

	@Override
	public void update(Observable o, Object arg) {
		txtModbusphpinfo.setTextChars(((String) arg).toCharArray());
	}
	
	@Override
	public void dispose() {
		mbusPHP.disconnect();
		super.dispose();
	}
}