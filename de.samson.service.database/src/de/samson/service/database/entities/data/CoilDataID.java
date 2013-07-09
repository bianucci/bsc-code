package de.samson.service.database.entities.data;


import java.io.Serializable;

public class CoilDataID implements Serializable {
	private static final long serialVersionUID = 4746206309710959108L;

	int nCoilnr;
	int nRegler_id;

	public CoilDataID() {
	}

	public int getnCoilnr() {
		return nCoilnr;
	}

	public void setnCoilnr(int nCoilnr) {
		this.nCoilnr = nCoilnr;
	}

	public int getnRegler_id() {
		return nRegler_id;
	}

	public void setnRegler_id(int nRegler_id) {
		this.nRegler_id = nRegler_id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + nCoilnr;
		result = prime * result + nRegler_id;
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
		CoilDataID other = (CoilDataID) obj;
		if (nCoilnr != other.nCoilnr)
			return false;
		if (nRegler_id != other.nRegler_id)
			return false;
		return true;
	}

}