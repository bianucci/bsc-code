package de.samson.service.database.ientities.histdata;

import java.util.Date;

import org.csstudio.swt.xygraph.dataprovider.ISample;

public interface IHistValue extends ISample{
	public double getValue();
	public Date getRecordTime();
}
