package de.samson.service.database.entities.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "wmw", schema = "s_modbusphp_data")
public class WmwData {

	@Id
	long id;

	// @ManyToOne
	// @JoinColumns(value = {
	// @JoinColumn(name = "sTyp", referencedColumnName = "geraeteKennung"),
	// @JoinColumn(name = "", referencedColumnName = "") })
	// WmwDesc description;

	@ManyToOne
	@JoinColumn(name = "wmz")
	WmzData wmz;
	
	double value;
	
	int masseinheit;

}
