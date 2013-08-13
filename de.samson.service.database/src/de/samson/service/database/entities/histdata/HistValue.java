package de.samson.service.database.entities.histdata;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.csstudio.swt.xygraph.dataprovider.ISample;

@Entity
@Table(name = "hist_data.hist_value")
public class HistValue implements ISample {

	@Id
	int id;

	@ManyToOne
	@JoinColumn(name="data_source_id",referencedColumnName = "id")
	HistDataSource data_source;

	@Temporal(TemporalType.TIMESTAMP)
	Date rec_time;
	double value;

	@Override
	public double getXValue() {
		return rec_time.getTime();
	}

	@Override
	public double getYValue() {
		return value;
	}

	@Override
	public double getXPlusError() {
		return 0;
	}

	@Override
	public double getYPlusError() {
		return 0;
	}

	@Override
	public double getXMinusError() {
		return 0;
	}

	@Override
	public double getYMinusError() {
		return 0;
	}

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return null;
	}
}
