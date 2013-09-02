package de.samson.histdata.ui.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import de.samson.service.database.ientities.histdata.HistDataSource;
import de.samson.service.database.ientities.histdata.IHistDataSet;

public class HistDataEditorInput implements IEditorInput {

	IHistDataSet mockedDataSet;
	boolean dataSetTemporallyGenerated;

	public HistDataEditorInput(IHistDataSet toVisualize) {
		super();
		this.mockedDataSet = toVisualize;
		dataSetTemporallyGenerated = false;
	}

	public HistDataEditorInput(HistDataSource toVisualize, final String dataSetName,
			final String xAxisName, final String yAxisName) {
		super();
		final ArrayList<HistDataSource> data_sources = new ArrayList<HistDataSource>();
		data_sources.add(toVisualize);

		mockedDataSet = new IHistDataSet(){
			@Override
			public int getId() {return 0;}
			@Override
			public void setId(int id) {}
			@Override
			public String getName() {return dataSetName;}
			@Override
			public void setName(String name) {}
			@Override
			public List<HistDataSource> getData_sources() {return data_sources;}
			@Override
			public void setData_sources(List<HistDataSource> data_sources) {}
			@Override
			public String getxAxisName() {return xAxisName;}
			@Override
			public void setxAxisName(String xAxisName) {}
			@Override
			public String getyAxisName() {return yAxisName;}
			@Override
			public void setyAxisName(String yAxisName) {}
			
		};
		dataSetTemporallyGenerated = true;
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
		return mockedDataSet.getName();
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

	public IHistDataSet getDataSet() {
		return mockedDataSet;
	}

}
