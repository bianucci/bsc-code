package de.samson.service.database.entities.data;

import java.io.Serializable;

public class RegisterDataID implements Serializable {
	private static final long serialVersionUID = -5651864625881895132L;

	int nRegler_id;
	int nRegisternr;

	public RegisterDataID() {
	}

	public RegisterDataID(int nRegler_id, int nRegisternr) {
		super();
		this.nRegler_id = nRegler_id;
		this.nRegisternr = nRegisternr;
	}

	public int getnRegler_id() {
		return nRegler_id;
	}

	public void setnRegler_id(int nRegler_id) {
		this.nRegler_id = nRegler_id;
	}

	public int getnRegisternr() {
		return nRegisternr;
	}

	public void setnRegisternr(int nRegisternr) {
		this.nRegisternr = nRegisternr;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + nRegisternr;
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
		RegisterDataID other = (RegisterDataID) obj;
		if (nRegisternr != other.nRegisternr)
			return false;
		if (nRegler_id != other.nRegler_id)
			return false;
		return true;
	}

}
