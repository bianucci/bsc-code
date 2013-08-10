package de.samson.service.database.entities.histdata;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import de.samson.service.database.entities.description.WmwDesc;

@Entity
@Table(name = "hist_data.wmw_data_src")
public class WmwDataSource extends HistDataSource {
	double value;
}
