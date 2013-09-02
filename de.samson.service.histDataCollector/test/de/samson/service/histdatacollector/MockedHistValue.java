package de.samson.service.histdatacollector;

import java.util.Date;

import de.samson.service.database.ientities.histdata.IHistValue;

public class MockedHistValue implements IHistValue {

	double value;
	Date recTime;

	public MockedHistValue(double value, Date recTime) {
		this.value = value;
		this.recTime = recTime;
	}

	@Override
	public String getInfo() {
		return null;
	}

	@Override
	public double getXMinusError() {
		return 0;
	}

	@Override
	public double getXPlusError() {
		return 0;
	}

	@Override
	public double getXValue() {
		return 0;
	}

	@Override
	public double getYMinusError() {
		return 0;
	}

	@Override
	public double getYPlusError() {
		return 0;
	}

	@Override
	public double getYValue() {
		return 0;
	}

	@Override
	public double getValue() {
		return value;
	}

	@Override
	public Date getRecordTime() {
		return recTime;
	}

}
