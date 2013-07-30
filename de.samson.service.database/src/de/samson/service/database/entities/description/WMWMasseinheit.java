package de.samson.service.database.entities.description;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@IdClass(value = WmwMasseinheitID.class)
@Table(name = "wmw_masseinheit", schema = "description")
public class WMWMasseinheit {

	@Id
	@Column(insertable = false, updatable = false)
	long wmwID;

	@Id
	long keyEinheit;

	String valueEinheit;

	@ManyToOne
	@JoinColumn(name = "wmwID", referencedColumnName = "wmwID")
	WMWDescription wmw;

	public long getWmwID() {
		return wmwID;
	}

	public void setWmwID(long wmwID) {
		this.wmwID = wmwID;
	}

	public long getKeyEinheit() {
		return keyEinheit;
	}

	public void setKeyEinheit(long key) {
		this.keyEinheit = key;
	}

	public String getValueEinheit() {
		return valueEinheit;
	}

	public void setValueEinheit(String value) {
		this.valueEinheit = value;
	}

	public WMWDescription getWmw() {
		return wmw;
	}

	public void setWmw(WMWDescription wmw) {
		this.wmw = wmw;
	}

}
