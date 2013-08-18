package de.samson.service.database.entities.description;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "desc_wmw", schema = "description", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"category", "wmz_id" }) })
public class WmwDesc {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	String category;

	@ManyToOne(cascade= CascadeType.ALL)
	@JoinColumn(name = "wmz_id", referencedColumnName = "id")
	WmzDesc wmz;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "wmw", orphanRemoval = true)
	List<Masseinheit> masseinheiten;

	@OneToMany(mappedBy = "wmwThisRegisterStoresValueFor", orphanRemoval = false)
	List<HRegDesc> werteRegister;

	@OneToOne
	@JoinColumns(value = {
			@JoinColumn(name = "hrnr_einheit", referencedColumnName = "hrnr"),
			@JoinColumn(name = "geraeteKennung_einheit", referencedColumnName = "geraeteKennung"),
			@JoinColumn(name = "revision_einheit", referencedColumnName = "revision") })
	HRegDesc registerEinheitIsStoredIn;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public WmzDesc getWmz() {
		return wmz;
	}

	public void setWmz(WmzDesc wmz) {
		this.wmz = wmz;
	}

	public List<Masseinheit> getMasseinheiten() {
		return masseinheiten;
	}

	public void setMasseinheiten(List<Masseinheit> masseinheiten) {
		this.masseinheiten = masseinheiten;
	}

	public List<HRegDesc> getWerteRegister() {
		return werteRegister;
	}

	public void setWerteRegister(List<HRegDesc> werteRegister) {
		this.werteRegister = werteRegister;
	}

	public HRegDesc getRegisterEinheitIsStoredIn() {
		return registerEinheitIsStoredIn;
	}

	public void setRegisterEinheitIsStoredIn(HRegDesc registerEinheitIsStoredIn) {
		this.registerEinheitIsStoredIn = registerEinheitIsStoredIn;
	}

}
