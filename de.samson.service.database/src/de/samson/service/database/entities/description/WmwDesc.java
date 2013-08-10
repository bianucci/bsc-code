package de.samson.service.database.entities.description;

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
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "desc_wmw", schema = "description", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"category", "wmz_id" }) })
public class WmwDesc {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;

	String category;

	@ManyToOne
	@JoinColumn(name = "wmz_id", referencedColumnName = "id")
	WmzDesc wmz;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "wmw", orphanRemoval = true)
	List<Masseinheit> masseinheiten;

	@OneToMany(mappedBy = "wmw", orphanRemoval = false)
	List<HoldingRegiterDescription> werteRegister;

}
