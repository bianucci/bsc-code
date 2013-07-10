package de.samson.service.database.entities.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "s_modbusphp_cfg.Coils")
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
}