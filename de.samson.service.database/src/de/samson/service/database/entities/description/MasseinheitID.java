package de.samson.service.database.entities.description;

import java.io.Serializable;

public class MasseinheitID implements Serializable {
	private static final long serialVersionUID = 2407002330520798123L;

	long key;
	long wmw_id;

	public MasseinheitID() {

	}

	public long getKeyEinheit() {
		return key;
	}

	public void setKeyEinheit(long key) {
		this.key = key;
	}

	public long getWmwID() {
		return wmw_id;
	}

	public void setWmwID(long wmwID) {
		this.wmw_id = wmwID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (key ^ (key >>> 32));
		result = prime * result + (int) (wmw_id ^ (wmw_id >>> 32));
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
		MasseinheitID other = (MasseinheitID) obj;
		if (key != other.key)
			return false;
		if (wmw_id != other.wmw_id)
			return false;
		return true;
	}

}