package de.samson.service.database;

import java.util.TimerTask;

public class DatabaseTimer extends TimerTask {

	@Override
	public void run() {
		DatabaseService.refreshData();
	}

}
