package de.samson.service.database.util;

import de.samson.service.database.entities.data.CoilData;
import de.samson.service.database.entities.data.RegisterData;
import de.samson.service.database.entities.data.ReglerData;
import de.samson.service.database.entities.description.CoilDescription;
import de.samson.service.database.entities.description.GeraeteDescription;
import de.samson.service.database.entities.description.HoldingRegiterDescription;

public class DataConverterUtil {

	private static CoilDescription lastCoilDesc = new CoilDescription();
	private static GeraeteDescription lastReglerDesc = new GeraeteDescription();
	private static ReglerData lastReglerData = new ReglerData();

	private static HoldingRegiterDescription lastRegisterDesc = new HoldingRegiterDescription();

	public static CoilDescription getCoilDescForData(CoilData cd) {
		if (lastCoilDesc.getClnr() == cd.getnCoilnr()) {
			return lastCoilDesc;
		} else {
			lastReglerData = cd.getRegler();
			for (CoilDescription sc : cd.getRegler().getReglerConfig()
					.getReglerDescription().getCoilsList()) {
				if (sc.getClnr() == cd.getnCoilnr()) {
					return sc;
				}
			}
		}
		return null;
	}

	public static HoldingRegiterDescription getRegisterDescForData(RegisterData rd) {
		if ((lastReglerData == rd.getReglerData())
				&& ((rd.getnRegisternr() + 40000) == lastRegisterDesc.getHrnr())) {
			return lastRegisterDesc;
		} else {
			lastReglerData = rd.getReglerData();

			for (HoldingRegiterDescription sc : rd.getReglerData().getReglerConfig()
					.getReglerDescription().getRegisterList()) {

				if (sc.getHrnr() == rd.getnRegisternr() + 40000) {
					return sc;
				}
			}
		}
		return null;
	}

	public static GeraeteDescription getGeraeteDescForData(ReglerData rd) {
		return lastReglerDesc;
	}

	public static ReglerData getGeraeteDataForDesc(GeraeteDescription sg) {
		return lastReglerData;
	}

}
