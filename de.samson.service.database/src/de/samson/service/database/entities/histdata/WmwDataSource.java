package de.samson.service.database.entities.histdata;

import javax.persistence.CascadeType;
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

	@OneToOne(mappedBy = "dataSource", cascade = CascadeType.ALL)
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

	public WmwDesc getDescription() {
		return description;
	}

	public void setDescription(WmwDesc description) {
		this.description = description;
	}

	public WmwData getData() {
		return data;
	}

	public void setData(WmwData data) {
		this.data = data;
	}

	@Transient
	@Override
	public String getyAxisName() {
		String einheitKey = String.valueOf(this.getData().getEinheit());
		for (int i = 0; i < description.getMasseinheiten().size(); i++)
			if (description.getMasseinheiten().get(i).getKeyEinheit()
					.equals(einheitKey))
				return description.getMasseinheiten().get(i).getValueEinheit();
		return this.bezeichnung;
	}

}
