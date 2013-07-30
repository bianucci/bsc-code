package de.samson.service.database.entities.description;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "desc_wmw", schema = "description")
public class WMWDescription {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long wmwId;

	String category;

	@ManyToOne
	@JoinColumn(name = "wmz_id", referencedColumnName = "id")
	WMZDescription wmz;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "wmw", orphanRemoval = true)
	List<WMWMasseinheit> masseinheiten;

	@OneToMany(mappedBy = "wmw", orphanRemoval = false)
	List<HoldingRegiterDescription> werteRegister;

	@OneToOne
	HoldingRegiterDescription einheitRegister;

	public long getWmwId() {
		return wmwId;
	}

	public void setWmwId(long wmwId) {
		this.wmwId = wmwId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public WMZDescription getWmz() {
		return wmz;
	}

	public void setWmz(WMZDescription wmz) {
		this.wmz = wmz;
	}

	public List<WMWMasseinheit> getMasseinheiten() {
		return masseinheiten;
	}

	public void setMasseinheiten(List<WMWMasseinheit> masseinheiten) {
		this.masseinheiten = masseinheiten;
	}

	public List<HoldingRegiterDescription> getWerteRegister() {
		return werteRegister;
	}

	public void setWerteRegister(List<HoldingRegiterDescription> werteRegister) {
		this.werteRegister = werteRegister;
	}

	public HoldingRegiterDescription getEinheitRegister() {
		return einheitRegister;
	}

	public void setEinheitRegister(HoldingRegiterDescription einheitRegister) {
		this.einheitRegister = einheitRegister;
	}

}
