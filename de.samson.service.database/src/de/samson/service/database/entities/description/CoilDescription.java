package de.samson.service.database.entities.description;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "str_coil", schema="description")
@IdClass(CoilDescriptionID.class)
public class CoilDescription {

	String anzKategorie;

	String bezeichnung;

	@Id
	int clnr;

	@ManyToOne
	@JoinColumns(value = {
			@JoinColumn(name = "geraeteKennung", referencedColumnName = "geraeteKennung", insertable = false, updatable = false),
			@JoinColumn(name = "revision", referencedColumnName = "revision", insertable = false, updatable = false) })
	GeraeteDescription geraet;

	@Id
	String geraeteKennung;

	String kommentar;
	int maskCSV;

	@Id
	int revision;

	boolean ro;

	String text0;
	String text1;

	public CoilDescription() {
	}

	public String getAnzKategorie() {
		return anzKategorie;
	}

	public void setAnzKategorie(String anzKategorie) {
		this.anzKategorie = anzKategorie;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public int getClnr() {
		return clnr;
	}

	public void setClnr(int clnr) {
		this.clnr = clnr;
	}

	public GeraeteDescription getGeraet() {
		return geraet;
	}

	public void setGeraet(GeraeteDescription geraet) {
		this.geraet = geraet;
	}

	public String getGeraeteKennung() {
		return geraeteKennung;
	}

	public void setGeraeteKennung(String geraeteKennung) {
		this.geraeteKennung = geraeteKennung;
	}

	public String getKommentar() {
		return kommentar;
	}

	public void setKommentar(String kommentar) {
		this.kommentar = kommentar;
	}

	public int getMaskCSV() {
		return maskCSV;
	}

	public void setMaskCSV(int maskCSV) {
		this.maskCSV = maskCSV;
	}

	public int getRevision() {
		return revision;
	}

	public void setRevision(int revision) {
		this.revision = revision;
	}

	public boolean isRo() {
		return ro;
	}

	public void setRo(boolean ro) {
		this.ro = ro;
	}

	public String getText0() {
		return text0;
	}

	public void setText0(String text0) {
		this.text0 = text0;
	}

	public String getText1() {
		return text1;
	}

	public void setText1(String text1) {
		this.text1 = text1;
	}

}
