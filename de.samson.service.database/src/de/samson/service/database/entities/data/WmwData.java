package de.samson.service.database.entities.data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import de.samson.service.database.entities.description.HRegDesc;
import de.samson.service.database.entities.description.WmwDesc;
import de.samson.service.database.entities.histdata.WmwDataSource;
import de.samson.service.database.ientities.histdata.IDataProvider;

@Entity
@Table(name = "wmw", schema = "s_modbusphp_data")
public class WmwData implements IDataProvider{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@ManyToOne
	@JoinColumn(name = "wmw_desc_id", referencedColumnName = "id")
	WmwDesc description;

	@ManyToOne
	@JoinColumn(name = "wmz_id", referencedColumnName = "id")
	WmzData wmz;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "data_source_id", referencedColumnName = "id")
	WmwDataSource dataSource;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "wmw_consists_of_hregdata", schema = "s_modbusphp_data" , joinColumns = { 
			@JoinColumn(name = "wmw_id", referencedColumnName = "id") }, 
			inverseJoinColumns = {
			@JoinColumn(name = "register_nRegler_id", referencedColumnName = "nRegler_id"),
			@JoinColumn(name = "register_nRegisternr", referencedColumnName = "nRegisternr") })
	List<RegisterData> rd;

	double value;

	int einheit;

	public WmwDesc getDescription() {
		return description;
	}

	public void setDescription(WmwDesc description) {
		this.description = description;
	}

	public WmzData getWmz() {
		return wmz;
	}

	public void setWmz(WmzData wmz) {
		this.wmz = wmz;
	}


	public double getValue() {
		double v = 0;
		List<RegisterData> rd2 = getRd();
		List<HRegDesc> werteRegister = description.getWerteRegister();

		for (int i = 0; i < rd2.size(); i++) {

			for (int j = 0; j < werteRegister.size(); j++) {

				int getnRegisternr = rd.get(i).getnRegisternr();
				int k = description.getWerteRegister().get(j).getHrnr() - 40000;

				if (getnRegisternr == k) {
					RegisterData registerData = rd.get(i);
					int getsWert = registerData.getsWert();
					double faktor = description.getWerteRegister().get(j)
							.getFaktor();
					v += getsWert * faktor;
				}
			}
		}
		setValue(v);
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public int getEinheit() {
		return einheit;
	}

	public void setEinheit(int einheit) {
		this.einheit = einheit;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public WmwDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(WmwDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public List<RegisterData> getRd() {
		return rd;
	}

	public void setRd(List<RegisterData> rd) {
		this.rd = rd;
	}

	@Transient
	@Override
	public double getCurrentValue() {
		return getValue();
	}
}
