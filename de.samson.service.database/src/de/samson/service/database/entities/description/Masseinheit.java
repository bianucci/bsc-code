package de.samson.service.database.entities.description;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@IdClass(value = MasseinheitID.class)
@Table(name = "masseinheit", schema = "description")
public class Masseinheit {

	@Id
	@Column(insertable = false, updatable = false)
	long wmw_id;

	@Id
	long key;

	String value;

	@ManyToOne
	@JoinColumn(name = "wmw_id", referencedColumnName = "wmw_id")
	WmwDesc wmw;

	@ManyToOne
	@JoinColumns(value = {
			@JoinColumn(name = "hrnr", referencedColumnName = "hrnr"),
			@JoinColumn(name = "geraeteKennung", referencedColumnName = "geraeteKennung"),
			@JoinColumn(name = "revision", referencedColumnName = "revision") })
	HoldingRegiterDescription registerStoredIn;

	public long getWmwID() {
		return wmw_id;
	}

	public void setWmwID(long wmwID) {
		this.wmw_id = wmwID;
	}

	public long getKeyEinheit() {
		return key;
	}

	public void setKeyEinheit(long key) {
		this.key = key;
	}

	public String getValueEinheit() {
		return value;
	}

	public void setValueEinheit(String value) {
		this.value = value;
	}

	public WmwDesc getWmw() {
		return wmw;
	}

	public void setWmw(WmwDesc wmw) {
		this.wmw = wmw;
	}

}
