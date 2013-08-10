package de.xygraph.test.example;

import org.csstudio.swt.xygraph.dataprovider.IDataProvider;
import org.csstudio.swt.xygraph.dataprovider.IDataProviderListener;
import org.csstudio.swt.xygraph.dataprovider.ISample;
import org.csstudio.swt.xygraph.linearscale.Range;

public class AnyDataProvider implements IDataProvider {

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ISample getSample(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Range getXDataMinMax() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Range getYDataMinMax() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isChronological() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addDataProviderListener(IDataProviderListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean removeDataProviderListener(IDataProviderListener listener) {
		// TODO Auto-generated method stub
		return false;
	}

}
