package de.samson.service.database.entities.data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import de.samson.service.database.entities.config.RegisterConfig;
import de.samson.service.database.entities.histdata.HRegDataSource;
import de.samson.service.database.ientities.histdata.IDataProvider;
import de.samson.service.database.util.DataConverterUtil;

@Entity
@Table(name = "Register", schema = "s_modbusphp_data")
@IdClass(value = RegisterDataID.class)
public class RegisterData implements IDataProvider {

	@Id
	private int nRegisternr;

	@Id
	@Column(insertable = false, updatable = false)
	private int nRegler_id;

	@ManyToOne
	@JoinColumn(name = "nRegler_id", referencedColumnName = "nRegler_id")
	ReglerData reglerData;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinTable(name = "hreg_datasrc_has_hregdata",schema="s_modbusphp_data", joinColumns = {
			@JoinColumn(name = "register_nRegler_id", referencedColumnName = "nRegler_id"),
			@JoinColumn(name = "register_nRegisternr", referencedColumnName = "nRegisternr") }, 
			inverseJoinColumns = { @JoinColumn(name = "hreg_data_source_id", referencedColumnName = "id") })
	HRegDataSource dataSource;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumns(value = {
			@JoinColumn(name = "nRegler_id", referencedColumnName = "nRegler_id", insertable = false, updatable = false),
			@JoinColumn(name = "nRegisternr", referencedColumnName = "nRegisternr", insertable = false, updatable = false), })
	RegisterConfig config;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinTable(name = "wmw_consists_of_hregdata",schema="s_modbusphp_data", joinColumns = {
			@JoinColumn(name = "register_nRegler_id", referencedColumnName = "nRegler_id"),
			@JoinColumn(name = "register_nRegisternr", referencedColumnName = "nRegisternr") }, 
			inverseJoinColumns = { @JoinColumn(name = "wmw_id", referencedColumnName = "id") })
	WmwData wmw;

	private byte[] sWert;
	private int nIntervall;
	private int nGruppe;
	private boolean bErr;

	public RegisterData() {
	}

	public RegisterData(int nRegisternr, byte[] sWert, int nIntervall,
			int nGruppe, boolean bErr, ReglerData reglerData) {
		super();
		this.nRegisternr = nRegisternr;
		this.sWert = sWert;
		this.nIntervall = nIntervall;
		this.nGruppe = nGruppe;
		this.bErr = bErr;
		this.reglerData = reglerData;
	}

	public int getnRegisternr() {
		return nRegisternr;
	}

	public void setnRegisternr(int nRegisternr) {
		this.nRegisternr = nRegisternr;
	}

	public int getsWert() {

		return (sWert[1] & 0xff) + sWert[0] * 256;
	}

	public void setsWert(byte[] sWert) {
		this.sWert = sWert;
	}

	public int getnIntervall() {
		return nIntervall;
	}

	public void setnIntervall(int nIntervall) {
		this.nIntervall = nIntervall;
	}

	public int getnGruppe() {
		return nGruppe;
	}

	public void setnGruppe(int nGruppe) {
		this.nGruppe = nGruppe;
	}

	public boolean isbErr() {
		return bErr;
	}

	public void setbErr(boolean bErr) {
		this.bErr = bErr;
	}

	public ReglerData getReglerData() {
		return reglerData;
	}

	public void setReglerData(ReglerData reglerData) {
		this.reglerData = reglerData;
	}

	@Override
	public String toString() {
		return "RegisterData [getnRegisternr()=" + getnRegisternr()
				+ ", getsWert()=" + getsWert() + ", getnIntervall()="
				+ getnIntervall() + ", getnGruppe()=" + getnGruppe()
				+ ", isbErr()=" + isbErr() + ", getReglerData()="
				+ getReglerData() + "]";
	}

	public int getnRegler_id() {
		return nRegler_id;
	}

	public void setnRegler_id(int nRegler_id) {
		this.nRegler_id = nRegler_id;
	}

	public HRegDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(HRegDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public RegisterConfig getConfig() {
		return config;
	}

	public void setConfig(RegisterConfig config) {
		this.config = config;
	}

	public WmwData getWmw() {
		return wmw;
	}

	public void setWmw(WmwData wmw) {
		this.wmw = wmw;
	}

	@Transient
	@Override
	public double getCurrentValue() {
		int raw = getsWert();
		double d = raw
				/ DataConverterUtil.getRegisterDescForData(this)
						.getSkalierungsfaktor();
		return d;
	}

}
