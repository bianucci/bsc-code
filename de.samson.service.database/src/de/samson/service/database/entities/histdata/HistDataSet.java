package de.samson.service.database.entities.histdata;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "hist_data.hist_data_set")
public class HistDataSet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	String name;

	@ManyToMany
	@JoinTable(name = "hist_data.hist_data_set_has_data_src", 
			joinColumns = @JoinColumn(name = "hist_data_set_id", referencedColumnName = "id"), 
			inverseJoinColumns = @JoinColumn(name = "data_src_id", referencedColumnName = "id"))
	List<HistDataSource> data_sources;

}
