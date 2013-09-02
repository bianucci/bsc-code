package de.samson.service.database.ientities.histdata;

import java.util.Date;
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

import de.samson.service.database.entities.histdata.HistDataSet;
import de.samson.service.database.entities.histdata.HistValue;

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

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "data_source", targetEntity = HistValue.class)
	protected List<IHistValue> historicalValues;

	@ManyToMany
	@JoinTable(name = "hist_data_set_has_data_source", schema = "hist_data", joinColumns = @JoinColumn(name = "data_source_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "hist_data_set_id", referencedColumnName = "id"))
	protected List<HistDataSet> dataSets;

	@Transient
	@Override
	public int getSize() {
		return historicalValues.size() + 1;
	}

	@Transient
	@Override
	public ISample getSample(int index) {
		if (index == historicalValues.size()) {
			HistValue v = new HistValue();
			v.setRec_time(new Date(System.currentTimeMillis()));
			v.setValue(getSample(index - 1).getYValue());
			return v;
		}
		return historicalValues.get(index);
	}

	@Transient
	@Override
	public Range getXDataMinMax() {
		IHistValue start = historicalValues.get(0);
		Range r = new Range(start.getXValue(), System.currentTimeMillis());
		return r;
	}

	@Transient
	@Override
	public Range getYDataMinMax() {
		double max = 0;
		double min = 0;
		for (int i = 0; i < historicalValues.size(); i++) {
			double d = historicalValues.get(i).getYValue();
			if (d < min)
				min = d;
			else if (d > max)
				max = d;
		}
		Range r = new Range(min, max);
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

	public List<IHistValue> getHistoricalValues() {
		return historicalValues;
	}

	public void setHistoricalValues(List<IHistValue> historicalValues) {
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

	public abstract String getyAxisName();

}
