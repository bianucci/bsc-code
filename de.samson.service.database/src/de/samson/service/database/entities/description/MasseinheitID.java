package de.samson.service.database.entities.description;

import java.io.Serializable;

public class MasseinheitID implements Serializable {
	private static final long serialVersionUID = 2407002330520798123L;

	String keyString;
	int wmw;

	public MasseinheitID() {

	}

	public String getKeyEinheit() {
		return keyString;
	}

	public void setKeyEinheit(String key) {
		this.keyString = key;
	}

	public int getWmwID() {
		return wmw;
	}

	public void setWmwID(int wmwID) {
		this.wmw = wmwID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((keyString == null) ? 0 : keyString.hashCode());
		result = prime * result + (int) (wmw ^ (wmw >>> 32));
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
		if (keyString == null) {
			if (other.keyString != null)
				return false;
		} else if (!keyString.equals(other.keyString))
			return false;
		if (wmw != other.wmw)
			return false;
		return true;
	}

}
