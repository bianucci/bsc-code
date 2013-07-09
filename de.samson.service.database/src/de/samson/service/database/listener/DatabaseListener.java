package de.samson.service.database.listener;

public class DatabaseListener<E> extends Thread {
	E toObserve;

	public DatabaseListener(E toObserve) {
		super();
		this.toObserve = toObserve;
	}

	@Override
	public void run() {
		super.run();
	}
}
