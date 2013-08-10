package de.samson.service.database.entities.data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.samson.service.database.entities.config.ReglerConfig;

@Entity
@Table(name = "wmz", schema = "s_modbusphp_data")
public class WmzData {

	@Id
	long id;

	int number;

	@ManyToOne
	@JoinColumn(name = "regler", referencedColumnName = "nId")
	ReglerConfig reglerConfig;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "wmz", orphanRemoval = true)
	List<WmwData> allWMW;

	// @ManyToOne
	// @JoinColumns(value = {
	// @JoinColumn(name = "sTyp", referencedColumnName = "geraeteKennung"),
	// @JoinColumn(name = "", referencedColumnName = "") })
	// WmzDesc description;

}
