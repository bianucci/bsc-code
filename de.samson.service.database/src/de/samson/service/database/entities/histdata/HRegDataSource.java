package de.samson.service.database.entities.histdata;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import de.samson.service.database.entities.data.RegisterData;
import de.samson.service.database.entities.description.HRegDesc;
import de.samson.service.database.util.DataConverterUtil;

@Entity
@DiscriminatorValue("hreg")
@Table(name = "hist_data.hreg_data_source")
public class HRegDataSource extends HistDataSource {

	@ManyToOne
	@JoinColumns(value = {
			@JoinColumn(name = "hreg_nr", referencedColumnName = "hrnr"),
			@JoinColumn(name = "geraete_kennung", referencedColumnName = "geraeteKennung"),
			@JoinColumn(name = "desc_revision", referencedColumnName = "revision") })
	HRegDesc hrd;

	@OneToOne(mappedBy = "dataSource", cascade = CascadeType.REFRESH )
	RegisterData data;

	public HRegDesc getHrd() {
		return hrd;
	}

	public void setHrd(HRegDesc hrd) {
		this.hrd = hrd;
	}

	@Override
	public String getBezeichnung() {
		return super.getBezeichnung();
	}

	@Override
	public void setBezeichnung(String bezeichnung) {
		super.setBezeichnung(bezeichnung);
	}

	@Override
	public int getId() {
		return super.getId();
	}

	@Override
	public void setId(int id) {
		super.setId(id);
	}

	@Transient
	@Override
	public double getLastHistoricalValue() {
		return historicalValues.get(historicalValues.size() - 1).getYValue();
	}

	@Transient
	@Override
	public double getCurrentValue() {
		int raw = data.getsWert();
		double d = raw
				/ DataConverterUtil.getRegisterDescForData(data)
						.getSkalierungsfaktor();
		return d;
	}

	public RegisterData getData() {
		return data;
	}

	public void setData(RegisterData data) {
		this.data = data;
	}

	@Transient
	@Override
	public String getyAxisName() {
		return this.hrd.getEinheit();
	}
}
