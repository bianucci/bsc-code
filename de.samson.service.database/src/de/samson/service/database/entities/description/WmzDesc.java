package de.samson.service.database.entities.description;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "desc_wmz", schema = "description", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"bezeichnung", "geraeteKennung", "revision" }) })
public class WmzDesc extends ArrayList<WmwDesc> {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;

	String bezeichnung;

	@ManyToOne
	@JoinColumns(value = {
			@JoinColumn(name = "geraeteKennung", referencedColumnName = "geraeteKennung"),
			@JoinColumn(name = "revision", referencedColumnName = "revision") })
	GeraeteDescription geraeteDescription;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "wmz", orphanRemoval = true)
	List<WmwDesc> wmwList;

	public void setName(String string) {
	}
}
