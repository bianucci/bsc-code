package de.samson.histdata.ui.editor;

import org.csstudio.swt.xygraph.figures.ToolbarArmedXYGraph;
import org.csstudio.swt.xygraph.figures.Trace;
import org.csstudio.swt.xygraph.figures.Trace.PointStyle;
import org.csstudio.swt.xygraph.figures.Trace.TraceType;
import org.csstudio.swt.xygraph.figures.XYGraph;
import org.csstudio.swt.xygraph.linearscale.Range;
import org.csstudio.swt.xygraph.util.XYGraphMediaFactory;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import de.samson.service.database.ientities.histdata.HistDataSource;
import de.samson.service.database.ientities.histdata.IHistDataSet;

public class HistDataSetEditor extends EditorPart {

	private HistDataEditorInput input;
	private IHistDataSet dataSet;
	private LightweightSystem lws;
	private XYGraph xyGraph;
	private static Color RED = XYGraphMediaFactory.getInstance().getColor(
			XYGraphMediaFactory.COLOR_RED);;

	@Override
	public void doSave(IProgressMonitor monitor) {
	}

	@Override
	public void doSaveAs() {
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		if (!(input instanceof HistDataEditorInput)) {
			throw new RuntimeException("Wrong input");
		}

		this.input = (HistDataEditorInput) input;
		setSite(site);
		setInput(input);

		dataSet = this.input.getDataSet();
		setPartName(dataSet.getName());
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
	public void createPartControl(Composite parent) {
		Canvas c = new Canvas(parent, SWT.None);

		lws = new LightweightSystem(c);

		xyGraph = new XYGraph();
		xyGraph.primaryXAxis.setTitle(dataSet.getxAxisName());
		xyGraph.primaryXAxis.setRange(new Range(-200, 200));
		xyGraph.primaryXAxis.setDateEnabled(true);
		xyGraph.primaryXAxis.setAutoScaleThreshold(0);
		xyGraph.primaryXAxis.setShowMajorGrid(true);
		xyGraph.primaryXAxis.setAutoScale(true);

		xyGraph.primaryYAxis.setTitle(dataSet.getyAxisName());
		xyGraph.primaryYAxis.setAutoScale(true);
		xyGraph.primaryYAxis.setShowMajorGrid(true);
		xyGraph.primaryYAxis.setAutoScaleThreshold(0.1);

		ToolbarArmedXYGraph toolbarArmedXYGraph = new ToolbarArmedXYGraph(
				xyGraph);

		xyGraph.setTitle(dataSet.getName());

		// set it as the content of LightwightSystem
		lws.setContents(toolbarArmedXYGraph);

		xyGraph.primaryXAxis.setShowMajorGrid(true);
		xyGraph.primaryYAxis.setShowMajorGrid(true);

		// add the trace to xyGraph
		for (int i = 0; i < dataSet.getData_sources().size(); i++) {
			HistDataSource ds = dataSet.getData_sources().get(i);
			Trace t = createTraceForDataSource(ds, HistDataSetEditor.RED,
					TraceType.AREA, PointStyle.FILLED_DIAMOND);
			xyGraph.addTrace(t);
		}
	}

	public Trace createTraceForDataSource(HistDataSource ds, Color bg,
			TraceType tt, PointStyle ps) {
		Trace t = new Trace(ds.getBezeichnung(), xyGraph.primaryXAxis,
				xyGraph.primaryYAxis, ds);
		t.setLineWidth(2);
		t.setAntiAliasing(true);
		t.setTraceType(tt);
		t.setPointStyle(ps);
		t.setAntiAliasing(true);
		t.setTraceColor(bg);

		if ((ds.getYDataMinMax().getUpper() == 1)
				&& (ds.getYDataMinMax().getLower() == 0)) {
			t.setTraceType(TraceType.STEP_HORIZONTALLY);
			t.setPointStyle(PointStyle.NONE);
		}

		return t;
	}

	@Override
	public void setFocus() {
	}

}
