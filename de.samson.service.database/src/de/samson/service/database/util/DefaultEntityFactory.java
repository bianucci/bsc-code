package de.samson.service.database.util;

import java.util.ArrayList;

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
import de.samson.service.database.entities.description.CoilDesc;
import de.samson.service.database.entities.description.GeraeteDesc;
import de.samson.service.database.entities.description.HRegDesc;

public class DefaultEntityFactory {

	private static GeraeteDesc str = null;

	public static ReglerConfig createReglerConfig(GatewayConfig gwc) {
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
		return rc;
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

	public static CoilConfig createNewCoilConfig(ReglerConfig rc, CoilDesc s) {
		CoilConfig ccf = new CoilConfig();
		ccf.setnCoilanz(1);
		ccf.setnCoilnr(s.getClnr());
		ccf.setnIntervall(10);
		ccf.setReglerConfig(rc);
		return ccf;
	}
}
