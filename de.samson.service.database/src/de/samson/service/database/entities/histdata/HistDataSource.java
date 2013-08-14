package de.samson.service.database.entities.histdata;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.csstudio.swt.xygraph.dataprovider.IDataProvider;
import org.csstudio.swt.xygraph.dataprovider.IDataProviderListener;
import org.csstudio.swt.xygraph.dataprovider.ISample;
import org.csstudio.swt.xygraph.linearscale.Range;

@Entity
@Table(name = "hist_data.data_source")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "src_type", discriminatorType = DiscriminatorType.STRING, length = 20)
public abstract class HistDataSource implements IDataProvider {

	public HistDataSource() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	String bezeichnung;

	double totband;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "data_source")
	List<HistValue> historicalValues;

	@ManyToMany
	@JoinTable(name = "hist_data_set_has_data_src", joinColumns = @JoinColumn(name = "data_src_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "data_set_id", referencedColumnName = "id"))
	List<HistDataSet> dataSets;

	@Transient
	@Override
	public int getSize() {
		return historicalValues.size();
	}

	@Transient
	@Override
	public ISample getSample(int index) {
		return historicalValues.get(index);
	}

	@Transient
	@Override
	public Range getXDataMinMax() {
		HistValue start = historicalValues.get(0);
		HistValue end = historicalValues.get(historicalValues.size() - 1);
		Range r = new Range(start.getXValue(), end.getXValue());
		return r;
	}

	@Transient
	@Override
	public Range getYDataMinMax() {
		HistValue start = historicalValues.get(0);
		HistValue end = historicalValues.get(historicalValues.size() - 1);
		Range r = new Range(start.getYValue(), end.getYValue());
		return r;
	}

	@Override
	public boolean isChronological() {
		return true;
	}

	@Override
	public void addDataProviderListener(IDataProviderListener listener) {
	}

	@Override
	public boolean removeDataProviderListener(IDataProviderListener listener) {
		return false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public List<HistValue> getHistoricalValues() {
		return historicalValues;
	}

	public void setHistoricalValues(List<HistValue> historicalValues) {
		this.historicalValues = historicalValues;
	}

	public List<HistDataSet> getDataSets() {
		return dataSets;
	}

	public void setDataSets(List<HistDataSet> dataSets) {
		this.dataSets = dataSets;
	}

	public void addHistVal(HistValue histValue) {
		this.historicalValues.add(histValue);
	}

	public double getTotband() {
		return totband;
	}

	public void setTotband(double totband) {
		this.totband = totband;
	}

	public abstract double getLastHistoricalValue();

	public abstract double getCurrentValue();

}
