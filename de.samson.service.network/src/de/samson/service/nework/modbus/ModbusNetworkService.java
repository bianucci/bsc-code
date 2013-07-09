package de.samson.service.nework.modbus;

import java.net.InetAddress;

import net.wimpi.modbus.Modbus;
import net.wimpi.modbus.io.ModbusTCPTransaction;
import net.wimpi.modbus.msg.ReadCoilsRequest;
import net.wimpi.modbus.net.TCPMasterConnection;

public class ModbusNetworkService {

	public static void pingModbusDevice(String ip, int[] stationNr) {
		for (int i : stationNr) {
			try {
				pingModbusDevice(ip, i);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void pingModbusDevice(String ip, int stationNr)
			throws Exception {

		InetAddress addr = null;
		try {
			addr = InetAddress.getByName(ip);

			TCPMasterConnection con = new TCPMasterConnection(addr);
			con.setPort(Modbus.DEFAULT_PORT);
			con.connect();

			ReadCoilsRequest r = new ReadCoilsRequest(0, 1);
			r.setUnitID(stationNr);

			ModbusTCPTransaction trans = new ModbusTCPTransaction(con);
			trans.setRequest(r);
			trans.setRetries(1);

			trans.execute();
			con.close();
			System.out.println("Regler " + stationNr + "available");

		} catch (Exception e) {
			System.out.println("Regler " + stationNr + "unavailable");
			throw new Exception();
		}
	}
}
