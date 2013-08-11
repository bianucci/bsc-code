package de.samson.service.database.util;

import de.samson.service.database.entities.data.CoilData;
import de.samson.service.database.entities.data.RegisterData;
import de.samson.service.database.entities.data.ReglerData;
import de.samson.service.database.entities.description.CoilDesc;
import de.samson.service.database.entities.description.GeraeteDesc;
import de.samson.service.database.entities.description.HRegDesc;

public class DataConverterUtil {

	private static CoilDesc lastCoilDesc = new CoilDesc();
	private static GeraeteDesc lastReglerDesc = new GeraeteDesc();
	private static ReglerData lastReglerData = new ReglerData();

	private static HRegDesc lastRegisterDesc = new HRegDesc();

	public static CoilDesc getCoilDescForData(CoilData cd) {
		if (lastCoilDesc.getClnr() == cd.getnCoilnr()) {
			return lastCoilDesc;
		} else {
			lastReglerData = cd.getRegler();
			for (CoilDesc sc : cd.getRegler().getReglerConfig()
					.getReglerDescription().getCoilsList()) {
				if (sc.getClnr() == cd.getnCoilnr()) {
					return sc;
				}
			}
		}
		return null;
	}

	public static HRegDesc getRegisterDescForData(RegisterData rd) {
		if ((lastReglerData == rd.getReglerData())
				&& ((rd.getnRegisternr() + 40000) == lastRegisterDesc.getHrnr())) {
			return lastRegisterDesc;
		} else {
			lastReglerData = rd.getReglerData();

			for (HRegDesc sc : rd.getReglerData().getReglerConfig()
					.getReglerDescription().getRegisterList()) {

				if (sc.getHrnr() == rd.getnRegisternr() + 40000) {
					return sc;
				}
			}
		}
		return null;
	}

	public static GeraeteDesc getGeraeteDescForData(ReglerData rd) {
		return lastReglerDesc;
	}

	public static ReglerData getGeraeteDataForDesc(GeraeteDesc sg) {
		return lastReglerData;
	}

}
