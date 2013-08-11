package de.samson.service.database.entities.description;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "str_geraet", schema = "description")
@IdClass(GeraeteDescID.class)
public class GeraeteDesc {

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "geraet", orphanRemoval = true)
	List<CoilDesc> coilsList;

	String comment;

	@Id
	String geraeteKennung;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "geraeteDesc", orphanRemoval = true)
	List<WmzDesc> wmz;

	String geraeteTyp;
	int idxAdr;
	boolean inCSV;

	String indexName;

	int maskCSVFilterGruppe;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "geraet", orphanRemoval = true)
	List<HRegDesc> registerList;

	@Id
	int revision;

	String singleUse;

	@OneToMany(cascade=CascadeType.ALL, mappedBy="geraeteDesc", orphanRemoval=true)
	private List<WmzDesc> wmzList;

	public GeraeteDesc() {
	}

	public List<CoilDesc> getCoilsList() {
		return coilsList;
	}

	public void setCoilsList(List<CoilDesc> coilsList) {
		this.coilsList = coilsList;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	public List<HRegDesc> getRegisterList() {
		return registerList;
	}

	public void setRegisterList(List<HRegDesc> registerList) {
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

	public void setAllWMZ(List<WmzDesc> parsedJSONFile) {
		this.wmzList = parsedJSONFile;
	}

	public List<WmzDesc> getWmzList() {
		return wmzList;
	}
}
