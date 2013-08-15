package de.samson.service.database.entities.data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import de.samson.service.database.entities.description.WmwDesc;
import de.samson.service.database.entities.histdata.WmwDataSource;

@Entity
@Table(name = "wmw", schema = "s_modbusphp_data")
public class WmwData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@ManyToOne
	@JoinColumn(name = "wmw_desc_id", referencedColumnName = "id")
	WmwDesc description;

	@ManyToOne
	@JoinColumn(name = "wmz_id", referencedColumnName = "id")
	WmzData wmz;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "data_source_id", referencedColumnName = "id")
	WmwDataSource dataSource;

	double value;

	int einheit;

	public WmwDesc getDescription() {
		return description;
	}

	public void setDescription(WmwDesc description) {
		this.description = description;
	}

	public WmzData getWmz() {
		return wmz;
	}

	public void setWmz(WmzData wmz) {
		this.wmz = wmz;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public int getEinheit() {
		return einheit;
	}

	public void setEinheit(int einheit) {
		this.einheit = einheit;
	}

}
