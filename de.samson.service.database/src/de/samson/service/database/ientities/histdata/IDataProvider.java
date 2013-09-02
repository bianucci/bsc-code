package de.samson.service.database.ientities.histdata;


public interface IDataProvider {

	public HistDataSource getDataSource();
	public double getCurrentValue();
}
