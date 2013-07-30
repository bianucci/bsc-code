package de.samson.service.database.entities.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Register", schema="s_modbusphp_data")
@IdClass(value = RegisterDataID.class)
public class RegisterData {

	@Id
	private int nRegisternr;

	@Id
	@Column(insertable = false, updatable = false)
	private int nRegler_id;

	@ManyToOne
	@JoinColumn(name = "nRegler_id", referencedColumnName = "nRegler_id")
	ReglerData reglerData;

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

}
