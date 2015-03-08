/* Released under the GPL2. See license.txt for details. */
package br.com.centralit.nagios;

import java.util.ArrayList;
import java.util.List;

public class PerformanceDataPerElement
{
	String lastCheckTime = "";
	final protected List<DataSource> dataSources = new ArrayList<DataSource>();

	public DataSource add(String source)
	{
		for(DataSource current : dataSources)
		{
			if (current.getDataSourceName().equals(source))
				return current;
		}

		DataSource newDataSource = new DataSource(source);
		dataSources.add(newDataSource);

		return newDataSource;
	}

	public void setDataSourceUnit(String name, String unit)
	{
		getDataSource(name).setUnit(unit);
	}

	public void setCheckTime(String checkTime)
	{
		lastCheckTime = checkTime;
	}

	public DataSource add(String source, double value)
	{
		DataSource newDataSource = add(source);

		newDataSource.add(value);

		return newDataSource;
	}

	public String getCheckTime()
	{
		return lastCheckTime;
	}

	public DataSource getDataSource(String name)
	{
		for(DataSource current : dataSources)
		{
			if (current.getDataSourceName().equals(name))
				return current;
		}

		return null;
	}

	public List<DataSource> getAllDataSources()
	{
		return dataSources;
	}
}
