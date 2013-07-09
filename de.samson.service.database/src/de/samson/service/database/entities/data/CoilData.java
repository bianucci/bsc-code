package de.samson.service.database.entities.data;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "s_modbusphp_data.Coils")
@IdClass(value = CoilDataID.class)
public class CoilData {
	@Id
	private int nCoilnr;
	private boolean bWert;
	private int nIntervall;
	private int nGruppe;
	private boolean bErr;

	@Id
	@Column(insertable = false, updatable = false)
	private int nRegler_id;

	@ManyToOne
	@JoinColumn(name = "nRegler_id", referencedColumnName = "nRegler_id")
	ReglerData reglerData;

	public CoilData() {
	}

	public CoilData(int nCoilnr, boolean bWert, int nIntervall, int nGruppe,
			boolean bErr, ReglerData reglerData) {
		super();
		this.nCoilnr = nCoilnr;
		this.bWert = bWert;
		this.nIntervall = nIntervall;
		this.nGruppe = nGruppe;
		this.bErr = bErr;
		this.reglerData = reglerData;
	}

	public int getnCoilnr() {
		return nCoilnr;
	}

	public void setnCoilnr(int nCoilnr) {
		this.nCoilnr = nCoilnr;
	}

	public boolean getWert() {
		return bWert;
	}

	public void setbWert(boolean bWert) {
		this.bWert = bWert;
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

	public ReglerData getRegler() {
		return reglerData;
	}

	public void setRegler(ReglerData reglerData) {
		this.reglerData = reglerData;
	}

}
