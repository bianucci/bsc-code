package de.samson.service.database.entities.histdata;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.samson.service.database.entities.description.HRegDesc;

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

}
