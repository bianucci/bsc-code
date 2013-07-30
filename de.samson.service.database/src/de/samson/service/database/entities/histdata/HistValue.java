package de.samson.service.database.entities.histdata;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "hist_data.hist_val")
public class HistValue {

	@Id
	int id;

	@ManyToOne
	@JoinColumn(referencedColumnName = "wmwID")
	CoilDataProvider data_src;

	@Temporal(TemporalType.TIMESTAMP)
	Date rec_time;
	double value;

}
