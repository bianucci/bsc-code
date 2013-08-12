package de.samson.service.database.entities.description;

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
public class WmzDesc {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;

	String bezeichnung;

	@ManyToOne
	@JoinColumns(value = {
			@JoinColumn(name = "geraeteKennung", referencedColumnName = "geraeteKennung"),
			@JoinColumn(name = "revision", referencedColumnName = "revision") })
	GeraeteDesc geraeteDesc;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "wmz", orphanRemoval = true)
	List<WmwDesc> wmwList;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public GeraeteDesc getGeraeteDesc() {
		return geraeteDesc;
	}

	public void setGeraeteDesc(GeraeteDesc geraeteDesc) {
		this.geraeteDesc = geraeteDesc;
	}

	public List<WmwDesc> getWmwList() {
		return wmwList;
	}

	public void setWmwList(List<WmwDesc> wmwList) {
		this.wmwList = wmwList;
	}

}
