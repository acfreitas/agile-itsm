/*****************************************************************************
 * Timeperiods.java - 
 *
 * Copyright (c) 2014 Projeto citsmart (Contact: adenir.gomes@centralit.com.br)
 *
 * License:
 *****************************************************************************/
package br.com.centralit.nagios.livestatus.tables;

import java.util.Map;

/**
 * Class Timeperiods is the main class for obtain all columns of table "timeperiods" from a Livestatus TCP-socket/file status.dat.
 * 
 * @author Adenir Ribeiro Gomes
 */

public class Timeperiods extends LiveStatusBase {
	/**
	 * Constructor of table Timeperiods
	 * 
	 * @param path
	 *            = "tcp://host:port" File : where path is the path to the file
	 */
	public Timeperiods(String path) {
		super(path);
		initializeMaps();
		tableName = "timeperiods";
	}

	/**
	 * create the map for all columns description of table Timeperiods. Key=column name, Value=column description
	 * 
	 */
	public final void initializeMaps() {
		mapComments.put("alias", "The alias of the timeperiod");
		mapComments.put("in", "Wether we are currently in this period (0/1)");
		mapComments.put("name", "The name of the timeperiod");
	}

	/**
	 * The alias of the timeperiod
	 * 
	 * @return returns the value of the "alias" column as string
	 */
	public String Alias() {
		return getAsString("alias");
	}

	/**
	 * Wether we are currently in this period (0/1)
	 * 
	 * @return returns the value of the "in" column as int
	 */
	public int In() throws NumberFormatException {
		return getAsInt("in");
	}

	/**
	 * The name of the timeperiod
	 * 
	 * @return returns the value of the "name" column as string
	 */
	public String Name() {
		return getAsString("name");
	}

	/**
	 * create the map for all columns of table Timeperiods. Key=column name, Value=column value
	 * 
	 * @param table
	 *            LiveStatus table
	 * @param filter
	 *            filter to applay for this table
	 * @return Map<String, String>
	 */

	@Override
	public Map<String, String> asArrayString(String table, String filter) throws NumberFormatException {
		mapKeyValue.clear();
		setMapObjects(table, filter);

		addToHashtable("alias", (Alias()));
		addToHashtable("in", getAsString(In()));
		addToHashtable("name", (Name()));
		return mapKeyValue;
	}
}
