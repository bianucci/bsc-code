package de.samson.service.database.entities.description;

import java.io.Serializable;

public class WmwMasseinheitID implements Serializable {
	private static final long serialVersionUID = 2407002330520798123L;

	long keyEinheit;
	long wmwID;

	public WmwMasseinheitID() {

	}

	public long getKeyEinheit() {
		return keyEinheit;
	}

	public void setKeyEinheit(long key) {
		this.keyEinheit = key;
	}

	public long getWmwID() {
		return wmwID;
	}

	public void setWmwID(long wmwID) {
		this.wmwID = wmwID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (keyEinheit ^ (keyEinheit >>> 32));
		result = prime * result + (int) (wmwID ^ (wmwID >>> 32));
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
		WmwMasseinheitID other = (WmwMasseinheitID) obj;
		if (keyEinheit != other.keyEinheit)
			return false;
		if (wmwID != other.wmwID)
			return false;
		return true;
	}

}
