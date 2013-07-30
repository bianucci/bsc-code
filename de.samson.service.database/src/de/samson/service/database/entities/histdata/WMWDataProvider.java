package de.samson.service.database.entities.histdata;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "hist_data.wmw_data_src")
public class WMWDataProvider extends HistDataProvider {

	int key_masseinheit;
}
