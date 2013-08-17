package de.samson.service.database.entities.histdata;

import java.util.List;

import javax.persistence.Column;
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

	@Column(name = "x_axis_name")
	String xAxisName;

	@Column(name = "y_axis_name")
	String yAxisName;

	@ManyToMany
	@JoinTable(name = "hist_data.hist_data_set_has_data_src", joinColumns = @JoinColumn(name = "hist_data_set_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "data_src_id", referencedColumnName = "id"))
	List<HistDataSource> data_sources;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<HistDataSource> getData_sources() {
		return data_sources;
	}

	public void setData_sources(List<HistDataSource> data_sources) {
		this.data_sources = data_sources;
	}

	public String getxAxisName() {
		return xAxisName;
	}

	public void setxAxisName(String xAxisName) {
		this.xAxisName = xAxisName;
	}

	public String getyAxisName() {
		return yAxisName;
	}

	public void setyAxisName(String yAxisName) {
		this.yAxisName = yAxisName;
	}

}
