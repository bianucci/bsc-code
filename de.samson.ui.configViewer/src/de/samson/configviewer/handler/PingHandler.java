package de.samson.configviewer.handler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import de.samson.configviewer.PartID;
import de.samson.configviewer.view.View;
import de.samson.service.database.DatabaseService;
import de.samson.service.database.entities.config.GatewayConfig;
import de.samson.service.database.entities.config.ReglerConfig;
import de.samson.service.database.entities.config.Standort;
import de.samson.service.network.icmp.PingService;
import de.samson.service.nework.modbus.ModbusNetworkService;

public class PingHandler extends AbstractHandler {

	private TreeViewer tv;
	private List<Standort> standortList;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
		final Calendar cal = Calendar.getInstance();
		System.out.println( sdf.format(cal.getTime()) );
		
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		IWorkbenchPage page = window.getActivePage();
		
		View view = (View) page.findView(PartID.View_Regler_Conf_ID);
		tv = view.getTv();

		standortList = DatabaseService.getStandortList();
		Runnable r = new Runnable() {
			private boolean pingHost;

			@Override
			public void run() {
				for (int i = 0; i < standortList.size(); i++) {
					Standort s = standortList.get(i);

					for (int j = 0; j < s.getGateways().size(); j++) {
						GatewayConfig g = s.getGateways().get(j);
						pingHost = PingService.pingHost(g.getsIP());
						g.setAvailable(true);
						
						if (pingHost) {
							for (int k = 0; k < g.getRegler().size(); k++) {
								ReglerConfig r = g.getRegler().get(k);
								try {
									ModbusNetworkService.pingModbusDevice(
											g.getsIP(), r.getnDeviceid());
									r.setAvailable(true);
									
								} catch (Exception e) {
									r.setAvailable(false);
								}
							}
						} else {
							g.setAvailable(false);
							for (ReglerConfig r : g.getRegler()) {
								r.setAvailable(false);
							}
							System.out.println( sdf.format(cal.getTime()) );
						}
					}
				}

				Display.getDefault().asyncExec(new Runnable() {

					@Override
					public void run() {
						tv.refresh();
					}
				});
			}
		};

		new Thread(r).start();
		return null;
	}
}
