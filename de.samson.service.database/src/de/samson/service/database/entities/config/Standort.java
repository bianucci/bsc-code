package de.samson.service.database.entities.config;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "s_modbusphp_cfg.standorte")
public class Standort {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int nid;
	String sStrasse;
	String sHn;
	int nPlz;

	@OneToMany(cascade=CascadeType.ALL, mappedBy = "standort")
	List<GatewayConfig> gateways;

	public Standort() {
	}

	public Standort(int nid, String sStrasse, String sHn, int nPlz,
			List<GatewayConfig> gateways) {
		super();
		this.nid = nid;
		this.sStrasse = sStrasse;
		this.sHn = sHn;
		this.nPlz = nPlz;
		this.gateways = gateways;
	}

	public int getNid() {
		return nid;
	}

	public void setNid(int nid) {
		this.nid = nid;
	}

	public String getsStrasse() {
		return sStrasse;
	}

	public void setsStrasse(String sStrasse) {
		this.sStrasse = sStrasse;
	}

	public String getsHn() {
		return sHn;
	}

	public void setsHn(String sHn) {
		this.sHn = sHn;
	}

	public int getnPlz() {
		return nPlz;
	}

	public void setnPlz(int nPlz) {
		this.nPlz = nPlz;
	}

	public List<GatewayConfig> getGateways() {
		return gateways;
	}

	public void setGateways(List<GatewayConfig> gateways) {
		this.gateways = gateways;
	}

}
