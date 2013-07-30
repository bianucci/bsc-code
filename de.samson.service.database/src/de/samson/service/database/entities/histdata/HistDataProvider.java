package de.samson.service.database.entities.histdata;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "hist_data.data_src")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "src_type", discriminatorType = DiscriminatorType.STRING, length = 20)
public class HistDataProvider {

	@Id
	long id;

	String bezeichnung;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "data_src")
	List<HistValue> histData;

	@ManyToMany
	@JoinTable(name = "hist_data_set_has_data_src", joinColumns = @JoinColumn(name = "data_src_id", referencedColumnName = "wmwID"), inverseJoinColumns = @JoinColumn(name = "data_set_id", referencedColumnName = "wmwID"))
	List<HistDataSet> dataSets;

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

}
