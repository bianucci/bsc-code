package de.samson.service.database.entities.description;
import java.io.Serializable;

public class CoilDescID implements Serializable {
	private static final long serialVersionUID = 123456L;

	int clnr;
	String geraeteKennung;
	int revision;

	public CoilDescID() {
	}

	public int getClnr() {
		return clnr;
	}

	public void setClnr(int clnr) {
		this.clnr = clnr;
	}

	public String getGeraeteKennung() {
		return geraeteKennung;
	}

	public void setGeraeteKennung(String geraeteKennung) {
		this.geraeteKennung = geraeteKennung;
	}

	public int getRevision() {
		return revision;
	}

	public void setRevision(int revision) {
		this.revision = revision;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + clnr;
		result = prime * result
				+ ((geraeteKennung == null) ? 0 : geraeteKennung.hashCode());
		result = prime * result + revision;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CoilDescID other = (CoilDescID) obj;
		if (clnr != other.clnr)
			return false;
		if (geraeteKennung == null) {
			if (other.geraeteKennung != null)
				return false;
		} else if (!geraeteKennung.equals(other.geraeteKennung))
			return false;
		if (revision != other.revision)
			return false;
		return true;
	}

}
