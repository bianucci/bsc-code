package de.samson.service.database.entities.histdata;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import de.samson.service.database.entities.data.CoilData;
import de.samson.service.database.entities.description.CoilDesc;

@Entity
@DiscriminatorValue("coil")
@Table(name = "hist_data.coil_data_source")
public class CoilDataSource extends HistDataSource {

	@OneToOne
	@JoinColumns(value = {
			@JoinColumn(name = "coil_nr", referencedColumnName = "clnr"),
			@JoinColumn(name = "geraete_kennung", referencedColumnName = "geraeteKennung"),
			@JoinColumn(name = "desc_revision", referencedColumnName = "revision") })
	CoilDesc cd;

	@OneToOne(mappedBy = "dataSource")
	CoilData data;

	public CoilDesc getCd() {
		return cd;
	}

	public void setCd(CoilDesc cd) {
		this.cd = cd;
	}

	@Override
	public void setBezeichnung(String bezeichnung) {
		super.setBezeichnung(bezeichnung);
	}

	@Override
	public String getBezeichnung() {
		return super.getBezeichnung();
	}

	@Override
	public void setId(int id) {
		super.setId(id);
	}

	@Override
	public int getId() {
		return super.getId();
	}

	@Transient
	@Override
	public double getLastHistoricalValue() {
		return historicalValues.get(historicalValues.size() - 1).getYValue();
	}

	@Transient
	@Override
	public double getCurrentValue() {
		if (data.getWert())
			return 1;
		else
			return 0;
	}

	public CoilData getData() {
		return data;
	}

	public void setData(CoilData data) {
		this.data = data;
	}

}
