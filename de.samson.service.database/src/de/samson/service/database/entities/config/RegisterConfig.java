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

import de.samson.service.database.entities.data.RegisterData;

@Entity
@Table(name = "Register", schema = "s_modbusphp_cfg")
@IdClass(value = RegisterConfigID.class)
public class RegisterConfig {

	@Id
	int nRegisternr;
	int nRegisteranz;
	int nIntervall;

	@Id
	@Column(insertable = false, updatable = false)
	int nRegler_id;

	@ManyToOne
	@JoinColumn(name = "nRegler_id", referencedColumnName = "nId")
	ReglerConfig reglerConfig;

	@OneToOne(mappedBy = "config", cascade = CascadeType.ALL, orphanRemoval = true)
	RegisterData data;

	public RegisterConfig() {
	}

	public RegisterConfig(int nRegisternr, int nRegisteranz, int nIntervall,
			ReglerConfig reglerConfig) {
		super();
		this.nRegisternr = nRegisternr;
		this.nRegisteranz = nRegisteranz;
		this.nIntervall = nIntervall;
		this.reglerConfig = reglerConfig;
	}

	public int getnRegisternr() {
		return nRegisternr;
	}

	public void setnRegisternr(int nRegisternr) {
		this.nRegisternr = nRegisternr;
	}

	public int getnRegisteranz() {
		return nRegisteranz;
	}

	public void setnRegisteranz(int nRegisteranz) {
		this.nRegisteranz = nRegisteranz;
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

	public RegisterData getData() {
		return data;
	}

	public void setData(RegisterData data) {
		this.data = data;
	}

}
