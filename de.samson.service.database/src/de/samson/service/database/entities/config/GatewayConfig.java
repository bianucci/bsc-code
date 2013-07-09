package de.samson.service.database.entities.config;

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
import javax.persistence.Transient;

import de.samson.service.database.entities.data.GatewayData;

@Entity
@Table(name = "s_modbusphp_cfg.Gateways")
public class GatewayConfig {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int nId;
	String sIP;
	int nPort;
	int nGruppe;

	@ManyToOne
	@JoinColumn(name = "nStandorte_id", referencedColumnName = "nId")
	Standort standort;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "gatewayConfig")
	List<ReglerConfig> regler;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "gatewayConfig")
	GatewayData gatewayData;
	
	@Transient
	private boolean available = true;

	public GatewayConfig() {
	}

	public GatewayData getGatewayData() {
		return gatewayData;
	}

	public void setGatewayData(GatewayData gatewayData) {
		this.gatewayData = gatewayData;
	}

	public void setRegler(List<ReglerConfig> regler) {
		this.regler = regler;
	}

	public int getnId() {
		return nId;
	}

	public void setnId(int nId) {
		this.nId = nId;
	}

	public String getsIP() {
		return sIP;
	}

	public void setsIP(String sIP) {
		this.sIP = sIP;
	}

	public int getnPort() {
		return nPort;
	}

	public void setnPort(int nPort) {
		this.nPort = nPort;
	}

	public int getnGruppe() {
		return nGruppe;
	}

	public void setnGruppe(int nGruppe) {
		this.nGruppe = nGruppe;
	}

	public Standort getStandort() {
		return standort;
	}

	public void setStandort(Standort standort) {
		this.standort = standort;
	}

	public List<ReglerConfig> getRegler() {
		return regler;
	}

	public void setAvailable(boolean b) {
		this.available = b;
	}

	public boolean isAvailable() {
		return available;
	}

}
