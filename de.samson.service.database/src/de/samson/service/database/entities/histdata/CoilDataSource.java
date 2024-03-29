package de.samson.service.database.entities.histdata;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.csstudio.swt.xygraph.linearscale.Range;

import de.samson.service.database.entities.data.CoilData;
import de.samson.service.database.entities.description.CoilDesc;
import de.samson.service.database.ientities.histdata.HistDataSource;

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

	@OneToOne(cascade = CascadeType.ALL)
	@JoinTable(name = "coil_datasrc_has_coildata",schema="s_modbusphp_data", joinColumns = { 
			@JoinColumn(name = "coil_data_source_id", referencedColumnName = "id") }, 
			inverseJoinColumns = {
			@JoinColumn(name = "coils_nRegler_id", referencedColumnName = "nRegler_id"),
			@JoinColumn(name = "coils_nCoilnr", referencedColumnName = "nCoilnr") })
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

	@Transient
	@Override
	public String getyAxisName() {
		return "";
	}

	@Override
	@Transient
	public Range getYDataMinMax() {
		return new Range(0, 1);
	}

}
