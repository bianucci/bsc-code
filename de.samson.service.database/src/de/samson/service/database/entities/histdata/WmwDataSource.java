package de.samson.service.database.entities.histdata;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.samson.service.database.entities.description.WmwDesc;

@Entity
@DiscriminatorValue("wmw")
@Table(name = "hist_data.wmw_data_source")
public class WmwDataSource extends HistDataSource {

	@ManyToOne
	@JoinColumn(name = "wmw_desc_id", referencedColumnName = "id")
	WmwDesc description;
}
