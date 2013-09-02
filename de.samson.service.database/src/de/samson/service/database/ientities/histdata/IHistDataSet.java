package de.samson.service.database.ientities.histdata;

import java.util.List;

public interface IHistDataSet {
	public int getId();

	public void setId(int id);

	public String getName();

	public void setName(String name);

	public List<HistDataSource> getData_sources();

	public void setData_sources(List<HistDataSource> data_sources);

	public String getxAxisName();

	public void setxAxisName(String xAxisName);

	public String getyAxisName();

	public void setyAxisName(String yAxisName);
}
