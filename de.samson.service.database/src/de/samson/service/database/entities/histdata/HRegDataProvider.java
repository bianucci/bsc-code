package de.samson.service.database.entities.histdata;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import de.samson.service.database.entities.description.HoldingRegiterDescription;

@Entity
@DiscriminatorValue("hreg")
@Table(name = "hist_data.hreg_data_src")
public class HRegDataProvider extends HistDataProvider {

	@OneToOne
	@JoinColumns(value = {
			@JoinColumn(name = "hreg_nr", referencedColumnName = "hrnr"),
			@JoinColumn(name = "geraete_kennung", referencedColumnName = "geraeteKennung"),
			@JoinColumn(name = "desc_revision", referencedColumnName = "revision") })
	HoldingRegiterDescription hrd;

	public HoldingRegiterDescription getHrd() {
		return hrd;
	}

	public void setHrd(HoldingRegiterDescription hrd) {
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
	public long getId() {
		return super.getId();
	}

	@Override
	public void setId(long id) {
		super.setId(id);
	}

}
