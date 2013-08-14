package de.samson.service.database.entities.histdata;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import de.samson.service.database.entities.data.WmwData;
import de.samson.service.database.entities.description.WmwDesc;

@Entity
@DiscriminatorValue("wmw")
@Table(name = "hist_data.wmw_data_source")
public class WmwDataSource extends HistDataSource {

	@ManyToOne
	@JoinColumn(name = "wmw_desc_id", referencedColumnName = "id")
	WmwDesc description;

	@OneToOne(mappedBy = "dataSource")
	WmwData data;

	@Transient
	@Override
	public double getLastHistoricalValue() {
		return historicalValues.get(historicalValues.size() - 1).getYValue();
	}

	@Transient
	@Override
	public double getCurrentValue() {
		return data.getValue();
	}
}
