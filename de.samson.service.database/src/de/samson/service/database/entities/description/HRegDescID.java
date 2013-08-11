package de.samson.service.database.entities.description;
import java.io.Serializable;

public class HRegDescID implements Serializable {
	private static final long serialVersionUID = -5058733829906896211L;

	String geraeteKennung;
	int hrnr;
	int revision;

	public HRegDescID() {
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
		result = prime * result
				+ ((geraeteKennung == null) ? 0 : geraeteKennung.hashCode());
		result = prime * result + hrnr;
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
		HRegDescID other = (HRegDescID) obj;
		if (geraeteKennung == null) {
			if (other.geraeteKennung != null)
				return false;
		} else if (!geraeteKennung.equals(other.geraeteKennung))
			return false;
		if (hrnr != other.hrnr)
			return false;
		if (revision != other.revision)
			return false;
		return true;
	}

}
