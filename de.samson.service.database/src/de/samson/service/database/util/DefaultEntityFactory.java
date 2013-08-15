package de.samson.service.database.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.samson.service.database.DatabaseService;
import de.samson.service.database.entities.config.CoilConfig;
import de.samson.service.database.entities.config.GatewayConfig;
import de.samson.service.database.entities.config.RegisterConfig;
import de.samson.service.database.entities.config.ReglerConfig;
import de.samson.service.database.entities.config.Standort;
import de.samson.service.database.entities.data.CoilData;
import de.samson.service.database.entities.data.GatewayData;
import de.samson.service.database.entities.data.RegisterData;
import de.samson.service.database.entities.data.ReglerData;
import de.samson.service.database.entities.data.WmwData;
import de.samson.service.database.entities.data.WmzData;
import de.samson.service.database.entities.description.CoilDesc;
import de.samson.service.database.entities.description.GeraeteDesc;
import de.samson.service.database.entities.description.HRegDesc;
import de.samson.service.database.entities.description.WmwDesc;
import de.samson.service.database.entities.description.WmzDesc;
import de.samson.service.database.entities.histdata.CoilDataSource;
import de.samson.service.database.entities.histdata.HRegDataSource;
import de.samson.service.database.entities.histdata.HistDataSet;
import de.samson.service.database.entities.histdata.HistDataSource;
import de.samson.service.database.entities.histdata.HistValue;

public class DefaultEntityFactory {

	public static ReglerConfig createReglerConfig(GatewayConfig gwc) {
		GeraeteDesc str = null;
		ReglerConfig rc = new ReglerConfig();
		ReglerData rd = new ReglerData();

		if (str == null)
			str = DatabaseService.getAllSTR_Geraet().get(0);

		rc.setCoilsConfigs(new ArrayList<CoilConfig>());
		rc.setRegisterConfigs(new ArrayList<RegisterConfig>());
		rc.setGatewayConfig(gwc);

		rc.setReglerDescription(str);
		rc.setDescFileRevision(String.valueOf(str.getRevision()));
		rc.setsTyp(str.getGeraeteKennung());

		rd.setCoilDatas(new ArrayList<CoilData>());
		rd.setGatewayData(gwc.getGatewayData());
		rd.setnErrcnt(0);
		rd.setnNextrequest(0);
		rd.setRegisterData(new ArrayList<RegisterData>());

		rd.setReglerConfig(rc);
		rc.setReglerData(rd);
		gwc.getRegler().add(rc);

		List<WmzData> allWmz = createAllWmzStoredInWmzDescription(str);
		for (int i = 0; i < allWmz.size(); i++) {
			WmzData wmzData = allWmz.get(i);
			wmzData.setReglerConfig(rc);
			rc.setAllWmz(allWmz);
		}

		return rc;
	}

	public static List<WmzData> createAllWmzStoredInWmzDescription(
			GeraeteDesc rc) {
		List<WmzData> allWmz = new ArrayList<WmzData>();

		List<WmzDesc> wmzDescList = rc.getWmzList();
		for (int i = 0; i < wmzDescList.size(); i++) {

			WmzDesc wmzDesc = wmzDescList.get(i);
			WmzData wmzData = new WmzData();

			// create wmz data
			wmzData.setAllWMW(new ArrayList<WmwData>());
			wmzData.setDescription(wmzDesc);
			allWmz.add(wmzData);

			List<WmwDesc> wmwDescList = wmzDesc.getWmwList();
			for (int j = 0; j < wmwDescList.size(); j++) {
				WmwDesc wmwDesc = wmwDescList.get(j);
				WmwData wmwData = new WmwData();

				wmwData.setValue(0);
				wmwData.setDescription(wmwDesc);
				wmwData.setEinheit(0);
				wmwData.setWmz(wmzData);

				wmzData.getAllWMW().add(wmwData);
			}
		}

		return allWmz;
	}

	public static GatewayConfig createGatewayConfig(Standort s) {
		GatewayConfig gwc = new GatewayConfig();
		GatewayData gwd = new GatewayData();

		gwc.setnGruppe(1);
		gwc.setnPort(502);
		gwc.setsIP("0.0.0.0");
		gwc.setStandort(s);
		gwc.setRegler(new ArrayList<ReglerConfig>());

		gwd.setReglerData(new ArrayList<ReglerData>());
		gwd.setnErrcnt(0);
		gwd.setnNextrequest(0);

		gwd.setGatewayConfig(gwc);
		gwc.setGatewayData(gwd);

		s.getGateways().add(gwc);
		return gwc;
	}

	public static Standort createNewStandort() {
		Standort s = new Standort();
		s.setGateways(new ArrayList<GatewayConfig>());
		s.setnPlz(12345);
		s.setsHn("22");
		s.setsStrasse("Sackgasse");
		return s;
	}

	public static RegisterConfig createNewRegisterConfig(ReglerConfig rc,
			HRegDesc s) {
		RegisterConfig r = new RegisterConfig();
		r.setnRegisteranz(1);
		r.setnRegisternr(s.getHrnr() - 40000);
		r.setnIntervall(10);
		r.setReglerConfig(rc);
		return r;
	}

	public static RegisterData createNewRegisterData(RegisterConfig rc) {
		RegisterData rd = new RegisterData();
		rd.setbErr(false);
		rd.setDataSource(null);
		rd.setnGruppe(0);
		rd.setnIntervall(rc.getnIntervall());
		rd.setnRegisternr(rc.getnRegisternr());
		rd.setnRegler_id(rc.getReglerConfig().getnId());
		rd.setReglerData(rc.getReglerConfig().getReglerData());
		rd.setsWert(new byte[] { Byte.MIN_VALUE, Byte.MIN_VALUE });
		return rd;
	}

	public static CoilData createNewCoilData(CoilConfig cc) {
		CoilData cd = new CoilData();
		cd.setbErr(false);
		cd.setbWert(false);
		cd.setDataSource(null);
		cd.setnCoilnr(cc.getnCoilnr());
		cd.setnGruppe(0);
		cd.setnIntervall(cc.getnIntervall());
		cd.setnRegler_id(cc.getReglerConfig().getnId());
		cd.setRegler(cc.getReglerConfig().getReglerData());
		return cd;
	}

	public static CoilConfig createNewCoilConfig(ReglerConfig rc, CoilDesc s) {
		CoilConfig ccf = new CoilConfig();
		ccf.setnCoilanz(1);
		ccf.setnCoilnr(s.getClnr());
		ccf.setnIntervall(10);
		ccf.setReglerConfig(rc);
		return ccf;
	}

	public static HRegDataSource createNewHRegDataSource(HRegDesc desc) {
		HRegDataSource ds = new HRegDataSource();
		ds.setBezeichnung("Register " + desc.getHrnr());
		ds.setDataSets(new ArrayList<HistDataSet>());
		ds.setHistoricalValues(new ArrayList<HistValue>());
		ds.setHrd(desc);
		ds.setTotband(1);
		return ds;
	}

	public static CoilDataSource createNewCoilDataSource(CoilDesc desc) {
		CoilDataSource ds = new CoilDataSource();
		ds.setBezeichnung("Coil " + desc.getClnr());
		ds.setDataSets(new ArrayList<HistDataSet>());
		ds.setHistoricalValues(new ArrayList<HistValue>());
		ds.setCd(desc);
		ds.setTotband(1);
		return ds;
	}

	public static HistValue createNewHistValue(HistDataSource ds) {
		HistValue v = new HistValue();
		v.setData_source(ds);
		v.setRec_time(new Date(System.currentTimeMillis()));
		v.setValue(0);
		return v;
	}
}
