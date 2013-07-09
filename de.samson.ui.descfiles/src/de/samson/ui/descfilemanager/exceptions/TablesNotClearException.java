package de.samson.ui.descfilemanager.exceptions;

public class TablesNotClearException extends Exception {
	private static final long serialVersionUID = 3467356135175622837L;

	public TablesNotClearException() {
		super();
	}

	public TablesNotClearException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public TablesNotClearException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public TablesNotClearException(String arg0) {
		super(arg0);
	}

	public TablesNotClearException(Throwable arg0) {
		super(arg0);
	}

}
