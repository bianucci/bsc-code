package de.samson.service.database.entities.config;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import de.samson.service.database.entities.data.CoilData;

@Entity
@Table(name = "Coils", schema = "s_modbusphp_cfg")
@IdClass(value = CoilConfigID.class)
public class CoilConfig {

	@Id
	int nCoilnr;
	int nCoilanz;
	int nIntervall;

	@Id
	@Column(insertable = false, updatable = false)
	int nRegler_id;

	@ManyToOne
	@JoinColumn(name = "nRegler_id", referencedColumnName = "nId")
	ReglerConfig reglerConfig;

	@OneToOne(mappedBy = "config", cascade = CascadeType.ALL, orphanRemoval = true)
	CoilData data;

	public CoilConfig() {
	}

	public CoilConfig(int nCoilnr, int nCoilanz, int nIntervall,
			ReglerConfig reglerConfig) {
		super();
		this.nCoilnr = nCoilnr;
		this.nCoilanz = nCoilanz;
		this.nIntervall = nIntervall;
		this.reglerConfig = reglerConfig;
	}

	public int getnCoilnr() {
		return nCoilnr;
	}

	public void setnCoilnr(int nCoilnr) {
		this.nCoilnr = nCoilnr;
	}

	public int getnCoilanz() {
		return nCoilanz;
	}

	public void setnCoilanz(int nCoilanz) {
		this.nCoilanz = nCoilanz;
	}

	public int getnIntervall() {
		return nIntervall;
	}

	public void setnIntervall(int nIntervall) {
		this.nIntervall = nIntervall;
	}

	public ReglerConfig getReglerConfig() {
		return reglerConfig;
	}

	public void setReglerConfig(ReglerConfig reglerConfig) {
		this.reglerConfig = reglerConfig;
	}

	public int getnRegler_id() {
		return nRegler_id;
	}

	public void setnRegler_id(int nRegler_id) {
		this.nRegler_id = nRegler_id;
	}

	public CoilData getData() {
		return data;
	}

	public void setData(CoilData data) {
		this.data = data;
	}

}