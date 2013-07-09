package de.samson.modbusphp.connenction;
import java.util.Properties;

/**
 * <ul>
 * <li>winPHP: path to win-php.exe
 * <li>startServer: path to start_server.php
 * <li>startThread: path to start_thread.php
 * <li>ip: IP address
 * <li>port: port
 * </ul>
 * 
 * @author cbianucci
 * 
 */
public class ModbusPHPConfig extends Properties {

	private static final long serialVersionUID = 4765710774744898858L;

	public ModbusPHPConfig() {
		super();
	}

	public ModbusPHPConfig(Properties defaults) {
		super(defaults);
	}

}
