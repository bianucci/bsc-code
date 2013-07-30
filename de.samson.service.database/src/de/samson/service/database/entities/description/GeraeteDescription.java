package de.samson.service.database.entities.description;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import de.samson.service.database.entities.config.ReglerConfig;

@Entity
@Table(name = "description.str_geraet")
@IdClass(GeraeteDescriptionID.class)
public class GeraeteDescription {

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "geraet", orphanRemoval = true)
	List<CoilDescription> coilsList;

	String comment;

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumns(value = {
			@JoinColumn(name = "revision", referencedColumnName = "descFileRevision", insertable = false, updatable = false),
			@JoinColumn(name = "geraeteKennung", referencedColumnName = "sTyp", insertable = false, updatable = false) })
	ReglerConfig reglerConfig;

	@Id
	String geraeteKennung;

	String geraeteTyp;
	int idxAdr;
	boolean inCSV;

	String indexName;

	int maskCSVFilterGruppe;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "geraet", orphanRemoval = true)
	List<HoldingRegiterDescription> registerList;

	@Id
	int revision;

	String singleUse;

	@Transient
	private List<WMZDescription> wmzList;

	public GeraeteDescription() {
	}

	public List<CoilDescription> getCoilsList() {
		return coilsList;
	}

	public void setCoilsList(List<CoilDescription> coilsList) {
		this.coilsList = coilsList;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public ReglerConfig getReglerConfig() {
		return reglerConfig;
	}

	public void setReglerConfig(ReglerConfig reglerConfig) {
		this.reglerConfig = reglerConfig;
	}

	public String getGeraeteKennung() {
		return geraeteKennung;
	}

	public void setGeraeteKennung(String geraeteKennung) {
		this.geraeteKennung = geraeteKennung;
	}

	public String getGeraeteTyp() {
		return geraeteTyp;
	}

	public void setGeraeteTyp(String geraeteTyp) {
		this.geraeteTyp = geraeteTyp;
	}

	public int getIdxAdr() {
		return idxAdr;
	}

	public void setIdxAdr(int idxAdr) {
		this.idxAdr = idxAdr;
	}

	public boolean isInCSV() {
		return inCSV;
	}

	public void setInCSV(boolean inCSV) {
		this.inCSV = inCSV;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public int getMaskCSVFilterGruppe() {
		return maskCSVFilterGruppe;
	}

	public void setMaskCSVFilterGruppe(int maskCSVFilterGruppe) {
		this.maskCSVFilterGruppe = maskCSVFilterGruppe;
	}

	public List<HoldingRegiterDescription> getRegisterList() {
		return registerList;
	}

	public void setRegisterList(List<HoldingRegiterDescription> registerList) {
		this.registerList = registerList;
	}

	public int getRevision() {
		return revision;
	}

	public void setRevision(int revision) {
		this.revision = revision;
	}

	public String getSingleUse() {
		return singleUse;
	}

	public void setSingleUse(String singleUse) {
		this.singleUse = singleUse;
	}

	public void setAllWMZ(List<WMZDescription> parsedJSONFile) {
		this.wmzList = parsedJSONFile;
	}

	public List<WMZDescription> getWmzList() {
		return wmzList;
	}
}
