/*****************************************************************************
 * Columns.java - 
 *
 * Copyright (c) 2014 Projeto citsmart (Contact: adenir.gomes@centralit.com.br)
 *
 * License:
 *****************************************************************************/
package br.com.centralit.nagios.livestatus.tables;

import java.util.Map;


/**
 * Class Columns is the main class for obtain all columns of table "columns" from a Livestatus TCP-socket/file status.dat.
 * 
 * @author Adenir Ribeiro Gomes
 */

public class Columns extends LiveStatusBase {
	/**
	 * Constructor of table Columns
	 * 
	 * @param path
	 *            = "tcp://host:port" File : where path is the path to the file
	 */
	public Columns(String path) {
		super(path);
		initializeMaps();
		tableName = "columns";
	}

	/**
	 * create the map for all columns description of table Columns. Key=column name, Value=column description
	 * 
	 */
	public final void initializeMaps() {
		mapComments.put("description", "A description of the column");
		mapComments.put("name", "The name of the column within the table");
		mapComments.put("table", "The name of the table");
		mapComments.put("type", "The data type of the column (int, float, string, list)");
	}

	/**
	 * A description of the column
	 * 
	 * @return returns the value of the "description" column as string
	 */
	public String Description() {
		return getAsString("description");
	}

	/**
	 * The name of the column within the table
	 * 
	 * @return returns the value of the "name" column as string
	 */
	public String Name() {
		return getAsString("name");
	}

	/**
	 * The name of the table
	 * 
	 * @return returns the value of the "table" column as string
	 */
	public String Table() {
		return getAsString("table");
	}

	/**
	 * The data type of the column (int, float, string, list)
	 * 
	 * @return returns the value of the "type" column as string
	 */
	public String Type() {
		return getAsString("type");
	}

	/**
	 * create the map for all columns of table Columns. Key=column name, Value=column value
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

		addToHashtable("description", (Description()));
		addToHashtable("name", (Name()));
		addToHashtable("table", (Table()));
		addToHashtable("type", (Type()));
		return mapKeyValue;
	}
}
