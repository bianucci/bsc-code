package de.samson.service.database.entities.data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import de.samson.service.database.entities.config.ReglerConfig;

@Entity
@Table(name = "Regler", schema="s_modbusphp_data")
public class ReglerData {

	@Id
	@Column(insertable = false, updatable = false)
	int nRegler_id;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "nRegler_id", referencedColumnName = "nId")
	ReglerConfig reglerConfig;

	private int nNextrequest;
	private int nErrcnt;

	@ManyToOne
	@JoinColumn(name = "nGateway_id", referencedColumnName = "nGateway_id")
	GatewayData gatewayData;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "reglerData", orphanRemoval = true)
	List<RegisterData> registerData;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "reglerData", orphanRemoval = true)
	List<CoilData> coilDatas;

	public ReglerData() {
	}

	public ReglerData(ReglerConfig reglerConfig, int nNextrequest, int nErrcnt,
			GatewayData gatewayData, List<RegisterData> registerData,
			List<CoilData> coilDatas) {
		super();
		this.reglerConfig = reglerConfig;
		this.nNextrequest = nNextrequest;
		this.nErrcnt = nErrcnt;
		this.gatewayData = gatewayData;
		this.registerData = registerData;
		this.coilDatas = coilDatas;
	}

	public int getnNextrequest() {
		return nNextrequest;
	}

	public void setnNextrequest(int nNextrequest) {
		this.nNextrequest = nNextrequest;
	}

	public int getnErrcnt() {
		return nErrcnt;
	}

	public void setnErrcnt(int nErrcnt) {
		this.nErrcnt = nErrcnt;
	}

	public GatewayData getGatewayData() {
		return gatewayData;
	}

	public void setGatewayData(GatewayData gatewayData) {
		this.gatewayData = gatewayData;
	}

	public List<RegisterData> getRegisterData() {
		return registerData;
	}

	public void setRegisterData(List<RegisterData> registerData) {
		this.registerData = registerData;
	}

	public List<CoilData> getCoilsData() {
		return coilDatas;
	}

	public void setCoilsData(List<CoilData> coilDatas) {
		this.coilDatas = coilDatas;
	}

	public ReglerConfig getReglerConfig() {
		return reglerConfig;
	}

	public void setReglerConfig(ReglerConfig reglerConfig) {
		this.reglerConfig = reglerConfig;
	}

	public List<CoilData> getCoilDatas() {
		return coilDatas;
	}

	public void setCoilDatas(List<CoilData> coilDatas) {
		this.coilDatas = coilDatas;
	}

}
