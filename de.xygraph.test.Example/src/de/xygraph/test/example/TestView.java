package de.xygraph.test.example;

import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class TestView extends ViewPart {

	public TestView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		Canvas c = new Canvas(parent, SWT.None);
		
		LightweightSystem lws = new LightweightSystem(c);
		XYGraphTest testFigure = new XYGraphTest();
		lws.setContents(testFigure);

	}

	@Override
	public void setFocus() {
	}

}
