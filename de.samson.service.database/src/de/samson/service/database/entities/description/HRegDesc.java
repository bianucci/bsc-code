package de.samson.service.database.entities.description;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "str_holdingreg", schema = "description")
@IdClass(HRegDescID.class)
public class HRegDesc {

	String aBerAnfang;

	String aBerEnde;

	String anzKategorie;

	String bezeichnung;

	String einheit;

	@ManyToOne
	@JoinColumns(value = {
			@JoinColumn(name = "geraeteKennung", referencedColumnName = "geraeteKennung", insertable = false, updatable = false),
			@JoinColumn(name = "revision", referencedColumnName = "revision", insertable = false, updatable = false) })
	GeraeteDesc geraet;

	@ManyToOne
	@JoinColumn(name = "wmw_id", referencedColumnName = "id", nullable = true)
	WmwDesc wmwThisRegisterStoresValueFor;

	@OneToOne(mappedBy = "registerEinheitIsStoredIn")
	WmwDesc wmwThisRegisterStoresMasseinheitKeyFor;

	@Id
	String geraeteKennung;

	@Id
	int hrnr;

	String kommentar;
	int maskCSV;
	int nkS;

	@Id
	int revision;

	boolean ro;

	String ueBerAnfang;
	String ueBerEnde;

	double faktor;

	public String getaBerAnfang() {
		return aBerAnfang;
	}

	public void setaBerAnfang(String aBerAnfang) {
		this.aBerAnfang = aBerAnfang;
	}

	public String getaBerEnde() {
		return aBerEnde;
	}

	public void setaBerEnde(String aBerEnde) {
		this.aBerEnde = aBerEnde;
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

	public String getEinheit() {
		return einheit;
	}

	public void setEinheit(String einheit) {
		this.einheit = einheit;
	}

	public GeraeteDesc getGeraet() {
		return geraet;
	}

	public void setGeraet(GeraeteDesc geraet) {
		this.geraet = geraet;
	}

	public WmwDesc getWmwThisRegisterStoresValueFor() {
		return wmwThisRegisterStoresValueFor;
	}

	public void setWmwThisRegisterStoresValueFor(
			WmwDesc wmwThisRegisterStoresValueFor) {
		this.wmwThisRegisterStoresValueFor = wmwThisRegisterStoresValueFor;
	}

	public WmwDesc getWmwThisRegisterStoresMasseinheitKeyFor() {
		return wmwThisRegisterStoresMasseinheitKeyFor;
	}

	public void setWmwThisRegisterStoresMasseinheitKeyFor(
			WmwDesc wmwThisRegisterStoresMasseinheitKeyFor) {
		this.wmwThisRegisterStoresMasseinheitKeyFor = wmwThisRegisterStoresMasseinheitKeyFor;
	}

	public String getGeraeteKennung() {
		return geraeteKennung;
	}

	public void setGeraeteKennung(String geraeteKennung) {
		this.geraeteKennung = geraeteKennung;
	}

	public int getHrnr() {
		return hrnr;
	}

	public void setHrnr(int hrnr) {
		this.hrnr = hrnr;
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

	public int getNkS() {
		return nkS;
	}

	public void setNkS(int nkS) {
		this.nkS = nkS;
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

	public String getUeBerAnfang() {
		return ueBerAnfang;
	}

	public void setUeBerAnfang(String ueBerAnfang) {
		this.ueBerAnfang = ueBerAnfang;
	}

	public String getUeBerEnde() {
		return ueBerEnde;
	}

	public void setUeBerEnde(String ueBerEnde) {
		this.ueBerEnde = ueBerEnde;
	}

	public double getFaktor() {
		return faktor;
	}

	public void setFaktor(double faktor) {
		this.faktor = faktor;
	}

	public double getSkalierungsfaktor() {
		double uB = Double.valueOf(getUeBerEnde().replace(',', '.').replace(
				".000", ".00"));

		double aB = Double.valueOf(getaBerEnde().replace(',', '.').replace(
				".000", ".00"));

		if ((aB == 0) && uB == 0) {
			aB = 1;
			uB = 1;
		}

		return uB / aB;
	}

	public boolean hasWmwDesc() {
		return this.wmwThisRegisterStoresMasseinheitKeyFor != null
				|| this.wmwThisRegisterStoresValueFor != null;
	}

	public WmwDesc getLinkedWmwDesc() {
		if (this.wmwThisRegisterStoresMasseinheitKeyFor != null)
			return this.wmwThisRegisterStoresMasseinheitKeyFor;
		if (this.wmwThisRegisterStoresValueFor != null)
			return this.wmwThisRegisterStoresValueFor;
		return null;

	}

}
