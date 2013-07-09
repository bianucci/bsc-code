package de.samson.service.network.icmp;

import java.net.InetAddress;

public class PingService {

	public static boolean pingHost(String hostname) {
		try {
			InetAddress byName = InetAddress.getByName(hostname);
			boolean reachable = byName.isReachable(2000);
			System.out.println(reachable);
			return reachable;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
