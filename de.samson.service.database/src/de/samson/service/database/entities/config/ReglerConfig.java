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

import de.samson.service.database.entities.data.ReglerData;
import de.samson.service.database.entities.description.STR_Geraet;

@Entity
@Table(name = "s_modbusphp_cfg.Regler")
public class ReglerConfig {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int nId;
	String sTyp;
	int nDeviceid;
	String descFileRevision;

	@ManyToOne
	@JoinColumn(name = "nGateways_id", referencedColumnName = "nId")
	GatewayConfig gatewayConfig;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "reglerConfig", orphanRemoval = true)
	List<RegisterConfig> registerConfigs;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "reglerConfig", orphanRemoval = true)
	List<CoilConfig> coilsConfigs;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "reglerConfig", orphanRemoval = true)
	ReglerData reglerData;

	@OneToOne(mappedBy = "reglerConfig")
	STR_Geraet reglerDescription;

	@Transient
	private boolean available = true;

	public ReglerConfig() {
	}

	public ReglerConfig(int nId, String sTyp, int nDeviceid,
			GatewayConfig gatewayConfig, List<RegisterConfig> registerConfigs,
			List<CoilConfig> coilsConfigs, ReglerData reglerData) {
		super();
		this.nId = nId;
		this.sTyp = sTyp;
		this.nDeviceid = nDeviceid;
		this.gatewayConfig = gatewayConfig;
		this.registerConfigs = registerConfigs;
		this.coilsConfigs = coilsConfigs;
		this.reglerData = reglerData;
	}

	public int getnId() {
		return nId;
	}

	public void setnId(int nId) {
		this.nId = nId;
	}

	public String getsTyp() {
		return sTyp;
	}

	public void setsTyp(String sTyp) {
		this.sTyp = sTyp;
	}

	public int getnDeviceid() {
		return nDeviceid;
	}

	public void setnDeviceid(int nDeviceid) {
		this.nDeviceid = nDeviceid;
	}

	public GatewayConfig getGatewayConfig() {
		return gatewayConfig;
	}

	public void setGatewayConfig(GatewayConfig gatewayConfig) {
		this.gatewayConfig = gatewayConfig;
	}

	public List<RegisterConfig> getRegisterConfigs() {
		return registerConfigs;
	}

	public void setRegisterConfigs(List<RegisterConfig> registerConfigs) {
		this.registerConfigs = registerConfigs;
	}

	public List<CoilConfig> getCoilsConfigs() {
		return coilsConfigs;
	}

	public void setCoilsConfigs(List<CoilConfig> coilsConfigs) {
		this.coilsConfigs = coilsConfigs;
	}

	public ReglerData getReglerData() {
		return reglerData;
	}

	public void setReglerData(ReglerData reglerData) {
		this.reglerData = reglerData;
	}

	public String getDescFileRevision() {
		return descFileRevision;
	}

	public void setDescFileRevision(String descFileRevision) {
		this.descFileRevision = descFileRevision;
	}

	public STR_Geraet getReglerDescription() {
		return reglerDescription;
	}

	public void setReglerDescription(STR_Geraet reglerDescription) {
		this.reglerDescription = reglerDescription;
	}

	public void setAvailable(boolean b) {
		this.available = b;
	}

	public boolean isAvailable() {
		return available;
	}

}
