package de.samson.histdata.ui.editor;

import java.util.ArrayList;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import de.samson.service.database.entities.histdata.HistDataSet;
import de.samson.service.database.entities.histdata.HistDataSource;

public class HistDataEditorInput implements IEditorInput {

	HistDataSet dataSet;
	boolean dataSetTemporallyGenerated;

	public HistDataEditorInput(HistDataSet toVisualize) {
		super();
		this.dataSet = toVisualize;
		dataSetTemporallyGenerated = false;
	}

	public HistDataEditorInput(HistDataSource toVisualize, String dataSetName,
			String xAxisName, String yAxisName) {
		super();
		ArrayList<HistDataSource> data_sources = new ArrayList<HistDataSource>();
		data_sources.add(toVisualize);

		dataSet = new HistDataSet();
		dataSetTemporallyGenerated = true;

		dataSet.setData_sources(data_sources);
		dataSet.setName(dataSetName);
		dataSet.setxAxisName(xAxisName);
		dataSet.setyAxisName(yAxisName);
	}

	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
		return null;
	}

	@Override
	public boolean exists() {
		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	@Override
	public String getName() {
		return dataSet.getName();
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return "";
	}

	public boolean isDataSetTemporallyGenerated() {
		return dataSetTemporallyGenerated;
	}

	public HistDataSet getDataSet() {
		return dataSet;
	}

}
