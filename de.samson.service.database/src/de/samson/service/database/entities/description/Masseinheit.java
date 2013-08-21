package de.samson.service.database.entities.description;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@IdClass(value = MasseinheitID.class)
@Table(name = "masseinheit", schema = "description")
public class Masseinheit {

	@Id
	String keyString;

	String value;

	@Id
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "wmw_id", referencedColumnName = "id")
	WmwDesc wmw;

	public String getKeyEinheit() {
		return keyString;
	}

	public void setKeyEinheit(String key) {
		this.keyString = key;
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
