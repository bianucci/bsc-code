package de.samson.service.database.entities.data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import de.samson.service.database.entities.config.GatewayConfig;

@Entity
@Table(name = "s_modbusphp_data.Gateways")
public class GatewayData {

	@Id
	@Column(insertable = false, updatable = false)
	int nGateway_id;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "nGateway_id", referencedColumnName = "nId")
	GatewayConfig gatewayConfig;

	private int nNextrequest;
	private int nErrcnt;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "gatewayData")
	List<ReglerData> reglerData;

	public GatewayData() {
	}

	public GatewayData(GatewayConfig gatewayConfig, int nNextrequest,
			int nErrcnt, List<ReglerData> reglerData) {
		super();
		this.gatewayConfig = gatewayConfig;
		this.nNextrequest = nNextrequest;
		this.nErrcnt = nErrcnt;
		this.reglerData = reglerData;
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

	public List<ReglerData> getReglerData() {
		return reglerData;
	}

	public void setReglerData(List<ReglerData> reglerData) {
		this.reglerData = reglerData;
	}

	public GatewayConfig getGatewayConfig() {
		return gatewayConfig;
	}

	public void setGatewayConfig(GatewayConfig gatewayConfig) {
		this.gatewayConfig = gatewayConfig;
	}

}
