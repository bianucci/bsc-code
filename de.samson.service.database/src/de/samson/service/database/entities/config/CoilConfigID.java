package de.samson.service.database.entities.config;

import java.io.Serializable;

public class CoilConfigID implements Serializable {
	private static final long serialVersionUID = -4475436574344962560L;

	int nRegler_id;
	int nCoilnr;

	public CoilConfigID() {
	}

	public CoilConfigID(int nRegler_id, int nCoilnr) {
		super();
		this.nRegler_id = nRegler_id;
		this.nCoilnr = nCoilnr;
	}

	public int getnRegler_id() {
		return nRegler_id;
	}

	public void setnRegler_id(int nRegler_id) {
		this.nRegler_id = nRegler_id;
	}

	public int getnCoilnr() {
		return nCoilnr;
	}

	public void setnCoilnr(int nCoilnr) {
		this.nCoilnr = nCoilnr;
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
		CoilConfigID other = (CoilConfigID) obj;
		if (nCoilnr != other.nCoilnr)
			return false;
		if (nRegler_id != other.nRegler_id)
			return false;
		return true;
	}

}
