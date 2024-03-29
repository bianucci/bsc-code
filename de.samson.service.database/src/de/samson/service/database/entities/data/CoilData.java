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

import de.samson.service.database.entities.config.CoilConfig;
import de.samson.service.database.entities.histdata.CoilDataSource;
import de.samson.service.database.ientities.histdata.IDataProvider;

@Entity
@Table(name = "Coils", schema = "s_modbusphp_data")
@IdClass(value = CoilDataID.class)
public class CoilData implements IDataProvider {
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

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinTable(name = "coil_datasrc_has_coildata", schema = "s_modbusphp_data", joinColumns = {
			@JoinColumn(name = "coils_nRegler_id", referencedColumnName = "nRegler_id"),
			@JoinColumn(name = "coils_nCoilnr", referencedColumnName = "nCoilnr") }, 
			inverseJoinColumns = { @JoinColumn(name = "coil_data_source_id", referencedColumnName = "id") })
	CoilDataSource dataSource;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumns(value = {
			@JoinColumn(name = "nRegler_id", referencedColumnName = "nRegler_id", insertable = false, updatable = false),
			@JoinColumn(name = "nCoilnr", referencedColumnName = "nCoilnr", insertable = false, updatable = false), })
	CoilConfig config;

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

	public int getnRegler_id() {
		return nRegler_id;
	}

	public void setnRegler_id(int nRegler_id) {
		this.nRegler_id = nRegler_id;
	}

	public ReglerData getReglerData() {
		return reglerData;
	}

	public void setReglerData(ReglerData reglerData) {
		this.reglerData = reglerData;
	}

	public CoilDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(CoilDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public boolean isbWert() {
		return bWert;
	}

	public CoilConfig getConfig() {
		return config;
	}

	public void setConfig(CoilConfig config) {
		this.config = config;
	}

	@Transient
	@Override
	public double getCurrentValue() {
		if (getWert())
			return 1;
		else
			return 0;
	}
}
