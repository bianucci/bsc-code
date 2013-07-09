package de.samson.service.database.util;

import de.samson.service.database.entities.data.CoilData;
import de.samson.service.database.entities.data.RegisterData;
import de.samson.service.database.entities.data.ReglerData;
import de.samson.service.database.entities.description.STR_Coil;
import de.samson.service.database.entities.description.STR_Geraet;
import de.samson.service.database.entities.description.STR_HoldingReg;

public class DataConverterUtil {

	private static STR_Coil lastCoilDesc = new STR_Coil();
	private static STR_Geraet lastReglerDesc = new STR_Geraet();
	private static ReglerData lastReglerData = new ReglerData();

	private static STR_HoldingReg lastRegisterDesc = new STR_HoldingReg();

	public static STR_Coil getCoilDescForData(CoilData cd) {
		if (lastCoilDesc.getClnr() == cd.getnCoilnr()) {
			return lastCoilDesc;
		} else {
			lastReglerData = cd.getRegler();
			for (STR_Coil sc : cd.getRegler().getReglerConfig()
					.getReglerDescription().getCoilsList()) {
				if (sc.getClnr() == cd.getnCoilnr()) {
					return sc;
				}
			}
		}
		return null;
	}

	public static STR_HoldingReg getRegisterDescForData(RegisterData rd) {
		if ((lastReglerData == rd.getReglerData())
				&& ((rd.getnRegisternr() + 40000) == lastRegisterDesc.getHrnr())) {
			return lastRegisterDesc;
		} else {
			lastReglerData = rd.getReglerData();

			for (STR_HoldingReg sc : rd.getReglerData().getReglerConfig()
					.getReglerDescription().getRegisterList()) {

				if (sc.getHrnr() == rd.getnRegisternr() + 40000) {
					return sc;
				}
			}
		}
		return null;
	}

	public static STR_Geraet getGeraeteDescForData(ReglerData rd) {
		return lastReglerDesc;
	}

	public static ReglerData getGeraeteDataForDesc(STR_Geraet sg) {
		return lastReglerData;
	}

}
