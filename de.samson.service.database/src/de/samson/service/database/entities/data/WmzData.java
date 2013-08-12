package de.samson.service.database.entities.data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.samson.service.database.entities.config.ReglerConfig;
import de.samson.service.database.entities.description.WmzDesc;

@Entity
@Table(name = "wmz", schema = "s_modbusphp_data")
public class WmzData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;

	@ManyToOne
	@JoinColumn(name = "regler_id", referencedColumnName = "nId")
	ReglerConfig reglerConfig;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "wmz", orphanRemoval = true)
	List<WmwData> allWMW;

	@ManyToOne
	@JoinColumn(name = "desc_wmz_id", referencedColumnName = "id")
	WmzDesc description;

	public ReglerConfig getReglerConfig() {
		return reglerConfig;
	}

	public void setReglerConfig(ReglerConfig reglerConfig) {
		this.reglerConfig = reglerConfig;
	}

	public List<WmwData> getAllWMW() {
		return allWMW;
	}

	public void setAllWMW(List<WmwData> allWMW) {
		this.allWMW = allWMW;
	}

	public WmzDesc getDescription() {
		return description;
	}

	public void setDescription(WmzDesc description) {
		this.description = description;
	}

}
