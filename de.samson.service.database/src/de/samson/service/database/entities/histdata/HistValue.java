 package de.samson.service.database.entities.histdata;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.csstudio.swt.xygraph.dataprovider.ISample;

import de.samson.service.database.ientities.histdata.HistDataSource;
import de.samson.service.database.ientities.histdata.IHistValue;

@Entity
@Table(name = "hist_data.hist_value")
public class HistValue implements IHistValue {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@ManyToOne
	@JoinColumn(name = "data_source_id", referencedColumnName = "id")
	HistDataSource data_source;

	@Temporal(TemporalType.TIMESTAMP)
	Date rec_time;
	double value;

	public HistDataSource getData_source() {
		return data_source;
	}

	public void setData_source(HistDataSource data_source) {
		this.data_source = data_source;
	}

	@Override
	public Date getRecordTime() {
		return rec_time;
	}

	public void setRec_time(Date rec_time) {
		this.rec_time = rec_time;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	@Transient
	@Override
	public double getXValue() {
		return rec_time.getTime();
	}

	@Transient
	@Override
	public double getYValue() {
		return value;
	}

	@Transient
	@Override
	public double getXPlusError() {
		return 0;
	}

	@Transient
	@Override
	public double getYPlusError() {
		return 0;
	}

	@Transient
	@Override
	public double getXMinusError() {
		return 0;
	}

	@Transient
	@Override
	public double getYMinusError() {
		return 0;
	}

	@Transient
	@Override
	public String getInfo() {
		return null;
	}
}
