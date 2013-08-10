package de.xygraph.test.example;

import java.util.ArrayList;
import java.util.List;

import org.csstudio.swt.xygraph.figures.ToolbarArmedXYGraph;
import org.csstudio.swt.xygraph.figures.XYGraph;
import org.csstudio.swt.xygraph.linearscale.Range;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import de.samson.service.database.DatabaseService;
import de.samson.service.database.entities.config.GatewayConfig;
import de.samson.service.database.entities.config.RegisterConfig;
import de.samson.service.database.entities.config.ReglerConfig;
import de.samson.service.database.entities.config.Standort;
import de.samson.service.database.util.DefaultEntityFactory;

public class TestView extends ViewPart {

	public TestView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		Canvas c = new Canvas(parent, SWT.None);

		// use LightweightSystem to create the bridge between SWT and draw2D
		final LightweightSystem lws = new LightweightSystem(c);

		// create a new XY Graph.
		XYGraph xyGraph = new XYGraph();
		xyGraph.setTitle("XY Graph Test");
		xyGraph.primaryXAxis.setTitle("Time");
		xyGraph.primaryXAxis.setRange(new Range(0, 200));
		xyGraph.primaryXAxis.setDateEnabled(true);
		xyGraph.primaryXAxis.setAutoScaleThreshold(0);
		xyGraph.primaryXAxis.setShowMajorGrid(true);
		xyGraph.primaryXAxis.setAutoScale(true);

		xyGraph.primaryYAxis.setTitle("Amplitude");
		xyGraph.primaryYAxis.setAutoScale(true);
		xyGraph.primaryYAxis.setShowMajorGrid(true);

		ToolbarArmedXYGraph toolbarArmedXYGraph = new ToolbarArmedXYGraph(
				xyGraph);

		xyGraph.setTitle("Simple Toolbar Armed XYGraph Example");
		// set it as the content of LightwightSystem
		lws.setContents(toolbarArmedXYGraph);

		xyGraph.primaryXAxis.setShowMajorGrid(true);
		xyGraph.primaryYAxis.setShowMajorGrid(true);

		// List<Standort> standortList = DatabaseService.getStandortList();
		// for (int i = 0; i < standortList.size(); i++) {
		// Standort s = standortList.get(i);
		//
		// for (int j = 0; j < s.getGateways().size(); j++) {
		// GatewayConfig g = s.getGateways().get(j);
		//
		// for (int k = 0; k < g.getRegler().size(); k++) {
		// ReglerConfig r = g.getRegler().get(k);
		// DatabaseService.addRegisterConfigsInRange(r, 0, 100);
		// }
		// }
		// DatabaseService.persistEntity(s);
		// }

		// for (int i = 0; i < 10; i++) {
		// Standort s = DatabaseService.addDefaultStandort();
		//
		// for (int j = 0; j < 10; j++) {
		// GatewayConfig g = DatabaseService
		// .addDefaultGatewayToStandort(s);
		//
		// for (int k = 0; k < 10; k++) {
		// ReglerConfig r = DatabaseService
		// .addDefaultReglerConfigToGateway(g);
		// }
		// }
		// }

		// create a trace data provider, which will provide the data to the
		// trace.
		// HistDataSource traceDataProvider = new HistDataSource();
		// traceDataProvider.addHistVal(new HistValue(new Date(System
		// .currentTimeMillis() + 86400000), 75.0));
		// traceDataProvider.addHistVal(new HistValue(new Date(System
		// .currentTimeMillis() + 86400000 * 2), 73.0));
		// traceDataProvider.addHistVal(new HistValue(new Date(System
		// .currentTimeMillis() + 86400000 * 3), 90.0));
		// traceDataProvider.addHistVal(new HistValue(new Date(System
		// .currentTimeMillis() + 86400000 * 4), 86.0));

		// create the trace
		// Trace trace = new Trace("Trace1-XY Plot", xyGraph.primaryXAxis,
		// xyGraph.primaryYAxis, traceDataProvider);
		//
		// // set trace property
		// trace.setPointStyle(PointStyle.XCROSS);
		//
		// // add the trace to xyGraph
		// xyGraph.addTrace(trace);
	}

	@Override
	public void setFocus() {
	}

}
